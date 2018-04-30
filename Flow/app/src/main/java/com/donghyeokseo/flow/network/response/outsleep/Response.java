package com.donghyeokseo.flow.network.response.outsleep;

public final class Response {
    private Long status;
    private String message;
    private Data data;

    public Long getStatus() { return status; }
    public void setStatus(Long value) { this.status = value; }

    public String getMessage() { return message; }
    public void setMessage(String value) { this.message = value; }

    public Data getData() { return data; }
    public void setData(Data value) { this.data = value; }
}