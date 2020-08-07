package com.example.a3.Class;

public class Cinema {
    private String cid;

    private String cname;

    private String csurburb;

    private String cpostcode;

    public Cinema() {
    }

    public Cinema(String cid, String cname, String csurburb, String cpostcode) {
        this.cid = cid;
        this.cname = cname;
        this.csurburb = csurburb;
        this.cpostcode = cpostcode;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getCsurburb() {
        return csurburb;
    }

    public void setCsurburb(String csurburb) {
        this.csurburb = csurburb;
    }

    public String getCpostcode() {
        return cpostcode;
    }

    public void setCpostcode(String cpostcode) {
        this.cpostcode = cpostcode;
    }
}
