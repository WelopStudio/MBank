package com.welop.mbank.model;

public class Account {
    private String mUid;
    private String mName;
    private String mEmail;
    private String mSex;

    public Account(String uid, String name, String email, String sex) {
        this.mUid = uid;
        this.mName = name;
        this.mEmail = email;
        this.mSex = sex;
    }

    public Account() {
        this("", "", "", "");
    }

    public String getUid() {
        return mUid;
    }

    public void setUid(String uid) {
        this.mUid = uid;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        this.mEmail = email;
    }

    public String getSex() {
        return mSex;
    }

    public void setSex(String sex) {
        this.mSex = sex;
    }
}
