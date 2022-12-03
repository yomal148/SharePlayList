package entity;


public class User {
    private String name;
    private String email;
    private String uid;
    private boolean img1;
    private boolean img2;
    private boolean img3;
    private boolean img4;
    private boolean img5;
    public User(){
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public User( String name,  String email,  String uid) {
        this.name = name;
        this.email = email;
        this.uid = uid;

    }




}