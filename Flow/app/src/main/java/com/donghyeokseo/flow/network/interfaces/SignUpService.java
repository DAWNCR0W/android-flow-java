package com.donghyeokseo.flow.network.interfaces;

import com.donghyeokseo.flow.network.request.signup.Request;
import com.donghyeokseo.flow.network.response.signup.Response;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SignUpService {
    @POST("/auth/signup")
    Call<Response> signUp(
            @Body Request request
    );
}
