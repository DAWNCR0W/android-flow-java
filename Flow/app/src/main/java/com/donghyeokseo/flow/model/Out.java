package com.donghyeokseo.flow.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author dawncrow
 */
public final class Out {
    private String startTime;
    private String endTime;
    private String reason;
    private int statusCode;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int typeCode) {
        this.statusCode = typeCode;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
