package com.welop.mbank.model;

public class Account {
    private String uid;
    private String name;
    private String email;
    private String sex;

    public Account(String uid, String name, String email, String sex) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.sex = sex;
    }

    public Account() {
        this("", "", "", "");
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
