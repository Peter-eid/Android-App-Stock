package com.grizly.newposapp.connectivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;


public class Factory {

    public static MyApiEndpointInterface create() {

        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.readTimeout(5, TimeUnit.MINUTES);
        builder.connectTimeout(5, TimeUnit.MINUTES);
        //builder.addInterceptor(new HeaderInterceptor());
        builder.networkInterceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = null;
                try {
                    request = chain.request().newBuilder()
                            .addHeader("Content-Type", "application/json")
                            .addHeader("Accept", "application/json")
                            .build();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return chain.proceed(request);
            }
        });

        OkHttpClient client = builder.build();

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MyApiEndpointInterface.ENDPOINT)
                .client(client)
                .addConverterFactory(new ToStringConverterFactory())
                .build();

        return retrofit.create(MyApiEndpointInterface.class);
    }

}
