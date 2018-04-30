package com.donghyeokseo.flow.network.request.signup;

public final class Request {
    private String email;
    private String pw;
    private String name;
    private String gender;
    private String mobile;
    private int class_idx;
    private int class_number;

    public Request(String email, String pw, String name, String gender, String mobile, int classIdx, int classNumber) {
        this.email = email;
        this.pw = pw;
        this.name = name;
        this.gender = gender;
        this.mobile = mobile;
        this.class_idx = classIdx;
        this.class_number = classNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String value) {
        this.email = value;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String value) {
        this.pw = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String value) {
        this.gender = value;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String value) {
        this.mobile = value;
    }

    public int getClassIdx() {
        return class_idx;
    }

    public void setClassIdx(int value) {
        this.class_idx = value;
    }

    public int getClassNumber() {
        return class_number;
    }

    public void setClassNumber(int value) {
        this.class_number = value;
    }
}
