package com.donghyeokseo.flow.network.response.outgo;

import java.sql.Timestamp;

public final class GoOut {
    private Long accept;
    private Long idx;
    private Timestamp start_time;
    private Timestamp end_time;
    private String reason;
    private Long class_idx;
    private String student_email;

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
        return start_time;
    }

    public void setStartTime(Timestamp value) {
        this.start_time = value;
    }

    public Timestamp getEndTime() {
        return end_time;
    }

    public void setEndTime(Timestamp value) {
        this.end_time = value;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String value) {
        this.reason = value;
    }

    public Long getClassIdx() {
        return class_idx;
    }

    public void setClassIdx(Long value) {
        this.class_idx = value;
    }

    public String getStudentEmail() {
        return student_email;
    }

    public void setStudentEmail(String value) {
        this.student_email = value;
    }
}