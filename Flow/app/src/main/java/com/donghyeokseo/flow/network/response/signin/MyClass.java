package com.donghyeokseo.flow.network.response.signin;

import com.google.gson.annotations.SerializedName;

/**
 * @author dawncrow
 */
public final class MyClass {
    private long grade;
    @SerializedName("class_room")
    private long classRoom;
    @SerializedName("class_number")
    private long classNumber;

    public long getGrade() {
        return grade;
    }

    public void setGrade(long value) {
        this.grade = value;
    }

    public long getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(long value) {
        this.classRoom = value;
    }

    public long getClassNumber() {
        return classNumber;
    }

    public void setClassNumber(long value) {
        this.classNumber = value;
    }
}