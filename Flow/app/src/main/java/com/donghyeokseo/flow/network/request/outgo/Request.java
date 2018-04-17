package com.donghyeokseo.flow.network.request.outgo;

import java.sql.Timestamp;

public class Request {
    private Timestamp startTime;
    private Timestamp endTime;
    private String reason;

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp value) {
        this.startTime = value;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp value) {
        this.endTime = value;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String value) {
        this.reason = value;
    }
}
