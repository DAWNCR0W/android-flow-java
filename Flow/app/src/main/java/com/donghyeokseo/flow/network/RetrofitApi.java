package com.donghyeokseo.flow.network;

import android.content.Context;
import android.support.annotation.NonNull;

import com.donghyeokseo.flow.Util;
import com.donghyeokseo.flow.database.DatabaseHelper;
import com.donghyeokseo.flow.network.interfaces.OutService;
import com.donghyeokseo.flow.network.interfaces.SignInService;
import com.donghyeokseo.flow.network.interfaces.SignUpService;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitApi {
    private Retrofit retrofit = null;
    private String token;

    public RetrofitApi(Context context) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        token = databaseHelper.getToken();
    }


    private Retrofit getClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
                Request request = chain
                        .request()
                        .newBuilder()
                        .addHeader("x-access-token", token)
                        .build();
                return chain.proceed(request);
            }
        });

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