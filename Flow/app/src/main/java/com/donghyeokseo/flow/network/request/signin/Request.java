package com.donghyeokseo.flow.network.request.signin;

public class Request {
    private String email;
    private String pw;

    public Request(String email, String pw) {
        this.email = email;
        this.pw = pw;
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
}
