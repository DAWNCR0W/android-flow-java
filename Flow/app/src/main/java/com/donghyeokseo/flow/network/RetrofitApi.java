package com.donghyeokseo.flow.network;

import com.donghyeokseo.flow.Util;
import com.donghyeokseo.flow.network.interfaces.SignInService;

import retrofit2.converter.gson.GsonConverterFactory;

import retrofit2.Retrofit;

public class RetrofitApi {
    private static Retrofit retrofit = null;

    private static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Util.SERVER_HOST)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static SignInService getSignInService() {
        return getClient().create(SignInService.class);
    }

}