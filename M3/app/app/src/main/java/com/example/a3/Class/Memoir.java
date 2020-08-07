package com.example.a3.Class;

public class Memoir {
    private String mid;
    private String mrelease;
    private String madddate;
    private String mcomment;
    private String mscore;
    private String mwatchdt;
    private String mname;
    private Cinema cid;
    private Person pid;

    public Memoir(String mid, String mrelease, String madddate, String mcomment, String mscore, String mwatchdt, String mname, Cinema cid, Person pid) {
        this.mid = mid;
        this.mrelease = mrelease;
        this.madddate = madddate;
        this.mcomment = mcomment;
        this.mscore = mscore;
        this.mwatchdt = mwatchdt;
        this.mname = mname;
        this.cid = cid;
        this.pid = pid;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getMrelease() {
        return mrelease;
    }

    public void setMrelease(String mrelease) {
        this.mrelease = mrelease;
    }

    public String getMadddate() {
        return madddate;
    }

    public void setMadddate(String madddate) {
        this.madddate = madddate;
    }

    public String getMcomment() {
        return mcomment;
    }

    public void setMcomment(String mcomment) {
        this.mcomment = mcomment;
    }

    public String getMscore() {
        return mscore;
    }

    public void setMscore(String mscore) {
        this.mscore = mscore;
    }

    public String getMwatchdt() {
        return mwatchdt;
    }

    public void setMwatchdt(String mwatchdt) {
        this.mwatchdt = mwatchdt;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public Cinema getCid() {
        return cid;
    }

    public void setCid(Cinema cid) {
        this.cid = cid;
    }

    public Person getPid() {
        return pid;
    }

    public void setPid(Person pid) {
        this.pid = pid;
    }
}
