package taskpage;

import java.io.File;

public class FileDesc {
    static final int DAY_LENGTH = 10;
    public static final String TAIL = ".txt";
    static final int TAIL_LENGTH = TAIL.length();
    
    private String dir;
    private String day;
    private String txtFile;
    
    public FileDesc(File dir, String name) {
        this.dir = dir.getName();
        this.txtFile = (name == null) ? ("") : name;
        makeDayOutOfFilename();
    }
    
    public String getDir() {
        return dir;
    }
    
    public String getDay() {
        return day;
    }
    
    public String getTxtFile() {
        return txtFile;
    }
    
    void makeDayOutOfFilename() {
        day = "";
        String s = "";
        int length = txtFile.length();
        boolean goOn = (length > (DAY_LENGTH + TAIL_LENGTH));
        
        if (goOn) {
            s = txtFile.substring(length - DAY_LENGTH - TAIL_LENGTH);
            length = s.length();
            s = s.substring(0, length - TAIL_LENGTH);
            
            try {
                Integer.parseInt(s.substring(0, 4)); // year
                goOn = (s.charAt(4) == '_');
            } catch (NumberFormatException enf) {
                goOn = false;
            }
        }
        
        if (goOn) {
            try {
                Integer.parseInt(s.substring(5, 7)); // month
                goOn = (s.charAt(7) == '_');
            } catch (NumberFormatException enf) {
                goOn = false;
            }
        }
        
        if (goOn) {
            try {
                Integer.parseInt(s.substring(8, 10)); // day
            } catch (NumberFormatException enf) {
                goOn = false;
            }
        }
        
        if (goOn) {
            day = s;
        }
    }
}
