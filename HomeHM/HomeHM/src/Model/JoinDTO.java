package Model;

public class JoinDTO {
    private String id;
    private String pw;
    private String name;
    private String createDT;

    public JoinDTO(String id, String pw, String name, String createDT) {
        this.id = id;
        this.pw = pw;
        this.name = name;
        this.createDT = createDT;
    }

    public JoinDTO(String id, String name, String createDT) {
        this.id = id;
        this.name = name;
        this.createDT = createDT;
    }

    public JoinDTO() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPw() {
        return this.pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreateDT() {
        return this.createDT;
    }

    public void setCreateDT(String createDT) {
        this.createDT = createDT;
    }
}
