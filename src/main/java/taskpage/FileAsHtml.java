package taskpage;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;

import com.autonomy.aci.client.services.AciService;
import com.autonomy.aci.client.services.impl.AciServiceImpl;
import com.autonomy.aci.client.services.impl.DocumentProcessor;
import com.autonomy.aci.client.transport.AciServerDetails;
import com.autonomy.aci.client.transport.impl.AciHttpClientImpl;
import com.autonomy.aci.client.util.ActionParameters;
import com.autonomy.aci.client.services.AciServiceException;

import org.apache.commons.lang.StringUtils;
import org.apache.http.impl.client.HttpClientBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

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
    
    String handleAci(String line) {
        String oc = "";
        UrlParse urlParse = new UrlParse(line);
        final AciService aciService = new AciServiceImpl(
                new AciHttpClientImpl(HttpClientBuilder.create().build()),
                new AciServerDetails(urlParse.getHost(), urlParse.getPort())
        );
        
        
        return oc;
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
            int ix = line.toLowerCase().indexOf(PREFIX);
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
            }
            
            if (txt.length() > TIME_MARK_LENGTH) {
                txt = txt.substring(txt.length() - TIME_MARK_LENGTH).
                        replaceAll("\\Q_\\E", ":");
            } else {
                txtDiffers = false;
            }
        }
        
        if (goOn) {
            handleAci(line); // FIXIT: return type is String
            oc = "<a href=\"" + line + "\">" + (txtDiffers ? txt : line) +
                    "</a><br/>\n";
        }
        
        return oc;
    }
    
    public String getHtml() {
        return html;
    }
}
