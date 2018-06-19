package com.donghyeokseo.flow.network.interfaces;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author dawncrow
 */
public interface SignService {

    @POST("/auth/signin")
    Call<com.donghyeokseo.flow.network.response.signin.Response> signIn(
            @Body com.donghyeokseo.flow.network.request.signin.Request request
    );

    @POST("/auth/signup")
    Call<com.donghyeokseo.flow.network.response.signup.Response> signUp(
            @Body com.donghyeokseo.flow.network.request.signup.Request request
    );
}
