package com.example.netflix.constants;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    private static Retrofit retrofit = null;

    public static Retrofit getRetrofit() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        OkHttpClient client = getClient();
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(URLHelper.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(client)
                    .build();
        }
        return retrofit;
    }

    private static OkHttpClient getClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(interceptor)
                .connectTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES)
                .retryOnConnectionFailure(true)
                .build();
        client.connectionPool().evictAll();
        return client;
    }

}