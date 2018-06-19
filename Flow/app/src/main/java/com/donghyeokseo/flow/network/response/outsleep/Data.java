package com.donghyeokseo.flow.network.response.outsleep;

import com.google.gson.annotations.SerializedName;

/**
 * @author dawncrow
 */
public final class Data {

    @SerializedName("sleep_out")
    private SleepOut sleepOut;

    public SleepOut getSleepOut() {
        return sleepOut;
    }

    public void setSleepOut(SleepOut value) {
        this.sleepOut = value;
    }
}