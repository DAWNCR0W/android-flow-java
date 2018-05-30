package com.donghyeokseo.flow.network.request.signin;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.annotations.SerializedName;

public final class Request {
    private String email;
    private String pw;
    @SerializedName("registration_token")
    private String registrationToken;

    public Request(String email, String pw) {
        this.email = email;
        this.pw = pw;
        this.registrationToken = FirebaseInstanceId.getInstance().getToken();
        Log.e("asdf", registrationToken);
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
