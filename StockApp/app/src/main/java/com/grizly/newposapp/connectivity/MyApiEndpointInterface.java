package com.grizly.newposapp.connectivity;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Path;
import retrofit2.http.DELETE;
import retrofit2.http.POST;


public interface MyApiEndpointInterface {

    String BASE_URL = "http://smb215.ddns.net:4999";
    String ENDPOINT = BASE_URL;

    @POST("/login")
    Call<String> login(@Body String body);

    @POST("/user")
    Call<String> addUser(@Body String body);

    @DELETE("/user/{id}")
    Call<String> deleteUser(@Path("id") String id);

    @POST("/product")
    Call<String> addProduct(@Body String body);

    @DELETE("/product/{id}")
    Call<String> deleteProduct(@Path("id") String id);

    @POST("/operation")
    Call<String> addOperation(@Body String body);

    @DELETE("/product/{id}")
    Call<String> deleteOperation(@Path("id") String id);

}
