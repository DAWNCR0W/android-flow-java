package com.donghyeokseo.flow.network.request.outgo;

public final class Request {
    private String start_time;
    private String end_time;
    private String reason;

    public String getStartTime() {
        return start_time;
    }

    public void setStartTime(String value) {
        this.start_time = value;
    }

    public String getEndTime() {
        return end_time;
    }

    public void setEndTime(String value) {
        this.end_time = value;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String value) {
        this.reason = value;
    }
}
