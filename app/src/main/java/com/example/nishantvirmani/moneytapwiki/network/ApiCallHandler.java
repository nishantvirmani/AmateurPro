package com.example.nishantvirmani.moneytapwiki.network;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiCallHandler {
    private OkHttpClient okHttpClient;
    private Retrofit retrofit;
    private static final String TAG = "ApiCallHandler";
    private static final String BASE_URL = "https://en.wikipedia.org//w/";

    public static ApiCallHandler newInstance() {
        return new ApiCallHandler();
    }

    private ApiCallHandler() {
        initOkHttpClient();
        initRetrofit();
    }

    private void initOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(60, TimeUnit.SECONDS);
        builder.addInterceptor(interceptor);
        okHttpClient = builder.build();
    }

    private void initRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    private Interceptor interceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request;
            try {
                request = ApiCallHandler.this.getRequest(chain);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
                request = chain.request();
            }
            return chain.proceed(request);
        }
    };

    private Request getRequest(Interceptor.Chain chain) {
        Request original = chain.request();
        Request.Builder requestBuilder = original.newBuilder();
        return requestBuilder.build();
    }

    public <T> T createService(Class<T> service) {
        return retrofit.create(service);
    }
}
