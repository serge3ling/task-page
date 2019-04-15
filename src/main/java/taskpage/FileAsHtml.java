package taskpage;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;

import com.autonomy.aci.client.services.AciConstants;
import com.autonomy.aci.client.services.AciService;
import com.autonomy.aci.client.services.impl.AciServiceImpl;
import com.autonomy.aci.client.services.impl.DocumentProcessor;
import com.autonomy.aci.client.transport.AciServerDetails;
import com.autonomy.aci.client.transport.impl.AciHttpClientImpl;
import com.autonomy.aci.client.util.ActionParameters;
import com.autonomy.aci.client.services.AciServiceException;

import java.util.Map;
import java.util.ArrayList;

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
    private ArrayList<AnswerRow> answers;
    private boolean oneConnectionFail;
    
    public FileAsHtml(String fileName) {
        html = "";
        answers = new ArrayList<AnswerRow>();
        oneConnectionFail = false;
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
    
    void handleAci(String line, String txt) {
        if (oneConnectionFail) {
            answers.add(new AnswerRow(line, txt, "..."));
            return;
        }
        UrlParse urlParse = new UrlParse(line);
        
        final AciService aciService = new AciServiceImpl(
                new AciHttpClientImpl(HttpClientBuilder.create().build()),
                new AciServerDetails(urlParse.getHost(), urlParse.getPort())
        );
        
        final ActionParameters parameters = new ActionParameters();
        Map map = urlParse.getMap();
        for (String key : urlParse.getKeySet()) {
            if (key.toLowerCase().equals("action")) {
                parameters.add(AciConstants.PARAM_ACTION, map.get(key));
            } else {
                parameters.add(key, map.get(key));
            }
        }
        
        AciServerDetails.TransportProtocol protocol = AciServerDetails.TransportProtocol.HTTP;
        switch (urlParse.getProtocol().toLowerCase()) {
            case "http":
                protocol = AciServerDetails.TransportProtocol.HTTP;
                break; // falls through
            case "https":
                protocol = AciServerDetails.TransportProtocol.HTTPS;
                break; // falls through
        }
        
        Document answer = null;
        String responseWord = "No connection";
        String token = "";
        boolean goOn = true;
        try {
            answer = aciService.executeAction(
                    new AciServerDetails(
                            protocol, urlParse.getHost(), urlParse.getPort()
                    ),
                    parameters,
                    new DocumentProcessor()
            );
        } catch (AciServiceException ase) {
            goOn = false;
            oneConnectionFail = true;
        }
        
        final XPath xpath = XPathFactory.newInstance().newXPath();
        
        if (answer != null) {
            try {
                responseWord = xpath.evaluate("/autnresponse/response", answer);
            } catch (XPathExpressionException e) {
                goOn = false;
                e.printStackTrace();
            }
        }
        
        if (goOn) {
            try {
                token = xpath.evaluate("/autnresponse/responsedata/token", answer);
            } catch (XPathExpressionException e) {
                goOn = false;
                token = "(Token not parsed.)";
                e.printStackTrace();
            }
        }
        
        answers.add(new AnswerRow(line, txt, responseWord, token));
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
            handleAci(line, txt);
            oc = "<a href=\"" + line + "\">" + (txtDiffers ? txt : line) +
                    "</a><br/>\n";
        }
        
        return oc;
    }
    
    public String getHtml() {
        return html;
    }
    
    public ArrayList<AnswerRow> getAnswers() {
        return answers;
    }
}
