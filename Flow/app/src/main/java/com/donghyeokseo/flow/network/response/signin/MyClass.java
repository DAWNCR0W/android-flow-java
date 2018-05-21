package com.donghyeokseo.flow.network.response.signin;

import com.google.gson.annotations.SerializedName;

public final class MyClass {
    private long grade;
    private long myClassClass;
    @SerializedName("class_number")
    private long classNumber;

    public long getGrade() {
        return grade;
    }

    public void setGrade(long value) {
        this.grade = value;
    }

    public long getMyClassClass() {
        return myClassClass;
    }

    public void setMyClassClass(long value) {
        this.myClassClass = value;
    }

    public long getClassNumber() {
        return classNumber;
    }

    public void setClassNumber(long value) {
        this.classNumber = value;
    }
}