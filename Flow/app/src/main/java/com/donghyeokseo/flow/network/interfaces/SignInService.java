package com.donghyeokseo.flow.network.interfaces;

import com.donghyeokseo.flow.network.request.signin.Request;
import com.donghyeokseo.flow.network.response.signin.Response;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SignInService {
    @POST("/auth/signin")
    Call<Response> signIn(
            @Body Request request
    );
}
