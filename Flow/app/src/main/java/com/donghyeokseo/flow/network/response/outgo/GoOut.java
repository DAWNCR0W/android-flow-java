package com.donghyeokseo.flow.network.response.outgo;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;

/**
 * @author dawncrow
 */
public final class GoOut {
    private Long accept;
    private Long idx;
    @SerializedName("start_time")
    private Timestamp startTime;
    @SerializedName("end_time")
    private Timestamp endTime;
    private String reason;
    @SerializedName("class_idx")
    private Long classIdx;
    @SerializedName("student_email")
    private String studentEmail;

    public Long getAccept() {
        return accept;
    }

    public void setAccept(Long value) {
        this.accept = value;
    }

    public Long getIdx() {
        return idx;
    }

    public void setIdx(Long value) {
        this.idx = value;
    }

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

    public Long getClassIdx() {
        return classIdx;
    }

    public void setClassIdx(Long value) {
        this.classIdx = value;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String value) {
        this.studentEmail = value;
    }
}