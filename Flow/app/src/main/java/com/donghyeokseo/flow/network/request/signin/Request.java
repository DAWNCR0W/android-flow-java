package com.donghyeokseo.flow.network.request.signin;

import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

public final class Request {
    private String email;
    private String pw;
    @SerializedName("registration_token")
    private String registrationToken;

    public Request(String email, String pw, @Nullable String registrationToken) {
        this.email = email;
        this.pw = pw;
        if (registrationToken == null) {

            this.registrationToken = "aaa";
            Log.e("파이어베이스 에러","토큰이 입력되지 않았습니다");
        } else
            this.registrationToken = registrationToken;
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

    public String getRegistrationToken() {
        return registrationToken;
    }

    public void setRegistrationToken(String registrationToken) {
        this.registrationToken = registrationToken;
    }
}
