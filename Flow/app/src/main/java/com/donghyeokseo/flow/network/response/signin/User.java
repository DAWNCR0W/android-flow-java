package com.donghyeokseo.flow.network.response.signin;

public class User {
    private String email;
    private String name;
    private String gender;
    private String mobile;
    private MyClass myClass;

    public String getEmail() {
        return email;
    }

    public void setEmail(String value) {
        this.email = value;
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

    public MyClass getMyClass() {
        return myClass;
    }

    public void setMyClass(MyClass value) {
        this.myClass = value;
    }
}