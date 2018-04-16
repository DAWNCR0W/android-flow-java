package com.donghyeokseo.flow.network;

import com.donghyeokseo.flow.Util;
import com.donghyeokseo.flow.network.interfaces.*;

import retrofit2.converter.gson.GsonConverterFactory;

import retrofit2.Retrofit;

public class RetrofitApi {
    private Retrofit retrofit = null;

    private Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Util.SERVER_HOST)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public SignInService getSignInService() {
        return getClient().create(SignInService.class);
    }

    public SignUpService getSignUpService() {
        return getClient().create(SignUpService.class);
    }
}