package taskpage;

import java.util.TreeMap;
import java.util.Map;
import java.util.Set;

public class UrlParse {
    static final String PROTOCOL_SPLITTER = "://";
    static final String DEFAULT_PROTOCOL = "http";
    
    String s;
    String protocol;
    String host;
    int port;
    int hostPortSlash;
    String subPath;
    String tail;
    TreeMap<String, String> map;
    
    void makeProtocol() {
        int ix = s.indexOf(PROTOCOL_SPLITTER);
        if (ix > 0) {
            protocol = s.substring(0, ix);
        } else {
            protocol = DEFAULT_PROTOCOL;
        }
    }
    
    public String getProtocol() {
        return protocol;
    }
    
    void makeHost() {
        int initLen = (protocol + PROTOCOL_SPLITTER).length();
        String s2 = s.substring(initLen);
        int ix = s2.indexOf("/");
        host = (ix > 0) ? s2.substring(0, ix) : s2;
        hostPortSlash = initLen + host.length();
    }
    
    public String getHost() {
        return host;
    }
    
    void makePort() {
        int ix = host.indexOf(":");
        port = 80;
        boolean goOn = ((ix > 0) && (ix < (host.length() - 1)));
        
        if (goOn) {
            if (ix > 0) {
                String s0 = host.substring(0, ix);
                String s1 = host.substring(ix + 1);
                try {
                    port = Integer.parseInt(s1);
                    host = s0;
                } catch (NumberFormatException enf) {
                }
            }
        }
    }
    
    public int getPort() {
        return port;
    }
    
    void makeTail() {
        subPath = "";
        if (hostPortSlash < (s.length() - 1)) {
            tail = s.substring(hostPortSlash + 1);
        } else {
            tail = "";
        }
        
        int ix = tail.indexOf("?");
        if (ix >= 0) {
            subPath = tail.substring(0, ix);
            if (ix < (tail.length() - 1)) {
                tail = tail.substring(ix + 1);
            }
        }
    }
    
    public String getSubPath() {
        return subPath;
    }
    
    public String getTail() {
        return tail;
    }
    
    void makeTwains() {
        map = new TreeMap<String, String>();
        String[] pairs = tail.split("\\Q&\\E");
        
        for (int i = 0; i < pairs.length; i++) {
            int ix = pairs[i].indexOf("=");
            if ((ix < (pairs[i].length() - 1)) && (ix > 0)) {
                String key = pairs[i].substring(0, ix);
                String val = pairs[i].substring(ix + 1);
                map.put(key, val);
            }
        }
    }
    
    Map<String, String> getMap() {
        return map;
    }
    
    Set<String> getKeySet() {
        return map.keySet();
    }
    
    public UrlParse(String s) {
        this.s = s;
        makeProtocol();
        makeHost(); // only after makeProtocol()
        makePort(); // only after makeHost()
        makeTail(); // only after makePort()
        makeTwains(); // only after makeTail()
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder("protocol: " + protocol +
                ", host: " + host + ", port: " + port +
                ", subPath: " + subPath + ", (keys: values):");
        
        for (String key : map.keySet()) {
            sb.append(" (" + key + ", " + map.get(key) + ")");
        }
        
        return sb.toString();
    }
    
    public static void main(String[] args) {
        String s = "http://10.0.1.56:14000/action=process&source=C:\\MicroFocus\\Broadcast\\112SDS\\112SDS_3020_2019_03_20_08_00_00.ts&configName=Algorithm\\Transcribe\\112SDS\\112SDS_3020_2019_03_20_08_00_00&[Session]StartOffset=0s&[Session]MaximumDuration=300s";
        UrlParse urlParse = new UrlParse(s);
        System.out.println(urlParse.toString());
    }
}
