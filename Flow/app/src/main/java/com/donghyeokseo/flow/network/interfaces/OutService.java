package com.donghyeokseo.flow.network.interfaces;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface OutService {
    @POST("/out/go")
    Call<com.donghyeokseo.flow.network.response.outgo.Response> outGo(
            @Body com.donghyeokseo.flow.network.request.outgo.Request request
    );

    @POST("/out/sleep")
    Call<com.donghyeokseo.flow.network.response.outsleep.Response> outSleep(
            @Body com.donghyeokseo.flow.network.request.outsleep.Request request
    );
}
