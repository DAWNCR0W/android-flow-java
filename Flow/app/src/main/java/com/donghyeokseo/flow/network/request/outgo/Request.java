package com.donghyeokseo.flow.network.request.outgo;

import com.google.gson.annotations.SerializedName;

/**
 * @author dawncrow
 */
public final class Request {
    @SerializedName("start_time")
    private String startTime;
    @SerializedName("end_time")
    private String endTime;
    private String reason;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String value) {
        this.startTime = value;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String value) {
        this.endTime = value;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String value) {
        this.reason = value;
    }
}
