package taskpage;

public class AnswerRow {
    private String txt;
    private String response;
    private String token;
    private String cssClass;
    
    public void setResponse(String response) {
        this.response = response;
        cssClass = (response.toLowerCase().equals("success") ?
                ("response-green") : ("response-black"));
    }
    
    public AnswerRow(String txt, String response, String token) {
        this.txt = txt;
        setResponse(response);
        this.token = token;
    }
    
    public AnswerRow(String txt, String response) {
        this(txt, response, "");
    }
    
    public String getResponse() {
        return response;
    }
    
    public String getToken() {
        return token;
    }
    
    public String getCssClass() {
        return cssClass;
    }
    
    public String getTxt() {
        return txt;
    }
}
