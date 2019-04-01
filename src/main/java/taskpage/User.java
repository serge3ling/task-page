package taskpage;

public class User {
    private String login;
    private String name;
    private String address;
    
    public User(String login, String name, String address) {
        this.login = login;
        this.name = name;
        this.address = address;
    }
    
    public String getLogin() {
        return login;
    }
    
    public String getName() {
        return name;
    }
    
    public String getAddress() {
        return address;
    }
}
