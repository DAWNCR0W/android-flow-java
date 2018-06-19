package com.donghyeokseo.flow.network.response.outgo;

import com.google.gson.annotations.SerializedName;

/**
 * @author dawncrow
 */
public final class Data {

    @SerializedName("go_out")
    private GoOut goOut;

    public GoOut getGoOut() {
        return goOut;
    }

    public void setGoOut(GoOut value) {
        this.goOut = value;
    }
}