package com.donghyeokseo.flow.network;

import android.content.Context;

import com.donghyeokseo.flow.Util;
import com.donghyeokseo.flow.database.DatabaseHelper;
import com.donghyeokseo.flow.network.interfaces.OutService;
import com.donghyeokseo.flow.network.interfaces.SignInService;
import com.donghyeokseo.flow.network.interfaces.SignUpService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class RetrofitApi {

    private Retrofit retrofit = null;
    private String token;

    public RetrofitApi(Context context) {

        DatabaseHelper databaseHelper = new DatabaseHelper(context);

        token = databaseHelper.getToken();
    }


    private Retrofit getClient() {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addInterceptor(chain -> {

            Request request = chain
                    .request()
                    .newBuilder()
                    .addHeader("x-access-token", token)
                    .build();

            return chain.proceed(request);
        });

        httpClient.connectTimeout(1000, TimeUnit.MILLISECONDS);
        httpClient.readTimeout(1000, TimeUnit.MILLISECONDS);

        if (retrofit == null) {

            retrofit = new Retrofit.Builder()
                    .baseUrl(Util.SERVER_HOST)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
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

    public OutService getOutService() {

        return getClient().create(OutService.class);
    }
}