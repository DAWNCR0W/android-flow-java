package com.donghyeokseo.flow.network.response.signin;

public class Response {
    private long status;
    private String message;
    private Data data;

    public long getStatus() {
        return status;
    }

    public void setStatus(long value) {
        this.status = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String value) {
        this.message = value;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data value) {
        this.data = value;
    }
}