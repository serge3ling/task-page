package taskpage;

import java.util.ArrayList;
import java.io.File;

public class TaskPage {
    private long id;
    private String content;
    private ArrayList<User> users;
    private String userDir;
    private Dirs dirs;
    //private String[] dirList;
    private String dirPick;
    private String filePick;
    private String fileAsHtml;
    private ArrayList<AnswerRow> answers;
    
    public TaskPage() {
        users = new ArrayList<User>();
        users.add(new User("nick", "Nicolas Cage", "Hollywood, CA"));
        users.add(new User("doe", "John Doe", "Washington, DC"));
        userDir = System.getProperty("user.dir");
        dirs = new Dirs();
    }
    
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public ArrayList<User> getUsers() {
        return users;
    }
    
    public String getUserDir() {
        return userDir;
    }
    
    /*public String[] getDirList() {
        return dirList;
    }*/
    
    public Dirs getDirs() {
        return dirs;
    }
    
    public String getDirPick() {
        return dirPick;
    }
    
    public void setDirPick(String dirPick) {
        this.dirPick = dirPick;
    }
    
    public String getFilePick() {
        return filePick;
    }
    
    public void setFilePick(String filePick) {
        this.filePick = filePick;
    }
    
    public String getFileAsHtml() {
        fileAsHtml = new FileAsHtml(Conf.getConf().getWorkDir() +
                File.separator + dirPick + File.separator + filePick).
                getHtml();
        return fileAsHtml;
    }
    
    public ArrayList<AnswerRow> getAnswers() {
        answers = new FileAsHtml(Conf.getConf().getWorkDir() +
                File.separator + dirPick + File.separator + filePick).
                getAnswers();
        return answers;
    }
}

// A git snapshot branch edit.
// This line: another edit.

/*
In a template:

<table>
    <th:block th:each="user : ${users}">
        <tr>
            <td th:text="${user.login}">...</td>
            <td th:text="${user.name}">...</td>
        </tr>
        <tr>
            <td colspan="2" th:text="${user.address}">...</td>
        </tr>
    </th:block>
</table>
*/
