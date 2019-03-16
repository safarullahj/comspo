package com.mspo.comspo.data.remote.webservice;

import android.util.Log;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    private static String TAG = "MSPO_URL";

    public static Retrofit getClient() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                HttpUrl url = original.url().newBuilder()
                        .build();

                Request request = original.newBuilder()
                        .url(url)
                        .build();

                Log.e(TAG, "url : " + request.url().url().toString());
                if (request.header("access-token") != null)
                    Log.e(TAG, "head : " + request.header("access-token"));
                if (request.body() != null)
                    Log.e(TAG, "body : " + request.body().toString());


                return chain.proceed(request);
            }
        }).build();

        return new Retrofit.Builder()
                .baseUrl(ApiConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

}
