package com.donghyeokseo.flow.network.request.signup;

import com.google.gson.annotations.SerializedName;

/**
 * @author dawncrow
 */
public final class Request {
    private String email;
    private String pw;
    private String name;
    private String gender;
    private String mobile;
    @SerializedName("class_idx")
    private int classIdx;
    @SerializedName("class_number")
    private int classNumber;

    public Request(String email, String pw, String name, String gender, String mobile, int classIdx, int classNumber) {
        this.email = email;
        this.pw = pw;
        this.name = name;
        this.gender = gender;
        this.mobile = mobile;
        this.classIdx = classIdx;
        this.classNumber = classNumber;
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
        return classIdx;
    }

    public void setClassIdx(int value) {
        this.classIdx = value;
    }

    public int getClassNumber() {
        return classNumber;
    }

    public void setClassNumber(int value) {
        this.classNumber = value;
    }
}
