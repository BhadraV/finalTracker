package com.example.tracker;

public class UserHelperClass {
    String name, username, password, busno, bustype, stime, from, to;

    public UserHelperClass() {

    }

    public UserHelperClass(String name, String username, String password, String busno, String bustype, String stime, String from, String to) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.busno = busno;
        this.bustype = bustype;
        this.stime = stime;
        this.from = from;
        this.to = to;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser() {
        return username;
    }

    public void setUser(String user) {
        this.username = user;
    }

    public String getPass() {
        return password;
    }

    public void setPass(String pass) {
        this.password = pass;
    }

    public String getBusno() {
        return busno;
    }

    public void setBusno(String busno) {
        this.busno = busno;
    }

    public String getBustype() {
        return bustype;
    }

    public void setBustype(String bustype) {
        this.bustype = bustype;
    }

    public String getStime() {
        return stime;
    }

    public void setStime(String stime) {
        this.stime = stime;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}