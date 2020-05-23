package com.example.luka2;


import java.util.Map;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface MyApiEndpointInterface {

    @GET("/")
    Call<Example> getMovieByName (@QueryMap Map<String, String> options);

    @GET("/")
    Call<Detail> getMovieData(@QueryMap Map<String, String> options);

}
