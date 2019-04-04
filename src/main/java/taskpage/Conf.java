package taskpage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;

public class Conf {
    public static final String CONF_FILE = "taskpage.conf";
    public static final String WORKDIR_NAME = "workdir";
    public static final String WORKDIR_DEFAULT = "workdir";
    
    private static Conf cnf;
    
    private boolean parseSuccess = true;
    private String msg = "";
    private String workDir;
    
    private Conf() {
        workDir = System.getProperty("user.dir") + File.separator +
                WORKDIR_DEFAULT;
        parseSuccess = parseConf();
    }
    
    boolean parseLine(String line) {
        boolean oc = true;
        String low = "";
        
        boolean goOn = (line != null);
        if (goOn) {
            line = line.trim();
            low = line.toLowerCase();
            goOn = (!line.equals(""));
        }
        
        if (goOn) {
            if (low.indexOf(WORKDIR_NAME) == 0) {
                String s2 = line.substring(WORKDIR_NAME.length()).trim();
                if (s2.indexOf("=") == 0) {
                    String s3 = s2.substring(1).trim();
                    workDir = (s3.equals("") ? workDir : s3);
                }
            }
        }
        
        return oc;
    }
    
    boolean parseConf() {
        boolean oc = true;
        
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(CONF_FILE));
        } catch (FileNotFoundException efnf) {
            oc = false;
            msg += "There is no file \"" + CONF_FILE + "\".";
        }
        
        if (oc) {
            String line = "";
            while (line != null) {
                try {
                    line = br.readLine();
                    oc = parseLine(line);
                } catch (IOException eio) {
                    oc = false;
                    line = null;
                    msg += "File \"" + CONF_FILE + "\" read error.";
                }
            }
        }
        
        return oc;
    }
    
    public static Conf getConf() {
        if (cnf == null) {
            cnf = new Conf();
        }
        return cnf;
    }
    
    public boolean isParseSuccess() {
        return parseSuccess;
    }
    
    public String getMessage() {
        return msg;
    }
    
    public String getWorkDir() {
        return workDir;
    }
}

