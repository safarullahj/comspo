package com.mspo.comspo.data.remote;

import android.support.annotation.NonNull;
import android.util.Log;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RequestInterceptor extends OkHttpClient implements Interceptor {

    String TAG = "MSPO_URL";

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Log.e("MSPO_URL", "in....");
        Request originalRequest = chain.request();
        HttpUrl originalHttpUrl = originalRequest.url();

        HttpUrl url = originalHttpUrl.newBuilder().build();

        Request request = originalRequest.newBuilder().url(url).build();

        Log.e(TAG, request.url().url().toString());
        return chain.proceed(request);
    }
}
