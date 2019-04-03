package taskpage;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;

public class FileAsHtml {
    public final String PREFIX = "configname=";
    public final int PREFIX_LENGTH = PREFIX.length();
    public final int TIME_MARK_LENGTH = 8;
    
    private String html;
    
    public FileAsHtml(String fileName) {
        html = "";
        StringBuilder sb = new StringBuilder();
        
        boolean goOn = true;
        
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException exception) {
            goOn = false;
        }
        
        if (goOn) {
            String line = "";
            while ((line != null) && goOn) {
                try {
                    line = br.readLine();
                    sb.append(parseLine(line));
                } catch (IOException exception) {
                    goOn = false;
                    line = null;
                }
            }
        }
        
        if (goOn) {
            html = sb.toString();
        }
    }
    
    String parseLine(String line) {
        String oc = "";
        
        boolean goOn = false;
        if (line != null) {
            line = line.trim();
            if (!line.equals("")) {
                goOn = true;
            }
        }
        
        String txt = "";
        boolean txtDiffers = true;
        if (goOn) {
            int ix = line.indexOf(PREFIX);
            if (ix >= 0) {
                txt = line.substring(ix + PREFIX_LENGTH);
            } else {
                txtDiffers = false;
            }
        }
        
        if (goOn && txtDiffers) {
            int ix = txt.indexOf("&");
            if (ix >= 0) {
                txt = txt.substring(0, ix);
                if (txt.length() > TIME_MARK_LENGTH) {
                    txt = txt.substring(txt.length() - TIME_MARK_LENGTH).
                        replaceAll("\\Q_\\E", ":");
                } else {
                    txtDiffers = false;
                }
            }
        }
        
        if (goOn) {
            oc = "<a href=\"" + line + "\">";
            oc += txtDiffers ? txt : line;
            oc += "</a><br/>\n";
        }
        
        return oc;
    }
    
    public String getHtml() {
        return html;
    }
}
