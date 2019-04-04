package taskpage;

import java.util.ArrayList;
import java.io.File;
import java.io.FilenameFilter;

public class Dirs {
    private String[] list;
    private ArrayList<FileDesc> files;
    
    public Dirs() {
        File dir = new File(Conf.getConf().getWorkDir());
        files = new ArrayList<FileDesc>();
        list = dir.list(new FilenameFilter() {
            @Override
            public boolean accept(File file, String name) {
                File innerFile = new File(file, name);
                boolean yes = innerFile.isDirectory();
                if (yes) {
                    maybeAddFiles(innerFile);
                }
                return yes;
            }
        });
    }
    
    void maybeAddFiles(File dir) {
        String[] dirList = dir.list(new FilenameFilter() {
            @Override
            public boolean accept(File file, String name) {
                boolean yes =
                        (new File(file, name).isFile()) &&
                        name.toLowerCase().endsWith(FileDesc.TAIL);
                if (yes) {
                    files.add(new FileDesc(file, name));
                }
                return yes;
            }
        });
    }
    
    public String[] getList() {
        return list;
    }
    
    public ArrayList<FileDesc> getFiles() {
        return files;
    }
}
