package com.grizly.newposapp.connectivity;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.POST;


public interface MyApiEndpointInterface {

    String BASE_URL = "http://smb215.ddns.net:4999";
    String ENDPOINT = BASE_URL;

    @POST("/login")
    Call<String> login(@Body String body);

}
