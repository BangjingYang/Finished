package com.example.a3.Class;

public class Credentials {
    private String username;
    private String passwd;
    private String signdate;
    private Person pid;

    public Credentials() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getSigndate() {
        return signdate;
    }

    public void setSigndate(String signdate) {
        this.signdate = signdate;
    }

    public Person getPid() {
        return pid;
    }

    public void setPid(Person pid) {
        this.pid = pid;
    }
}
