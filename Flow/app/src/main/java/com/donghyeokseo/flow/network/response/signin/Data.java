package com.donghyeokseo.flow.network.response.signin;

public class Data {
    private String token;
    private User user;

    public String getToken() {
        return token;
    }

    public void setToken(String value) {
        this.token = value;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User value) {
        this.user = value;
    }
}