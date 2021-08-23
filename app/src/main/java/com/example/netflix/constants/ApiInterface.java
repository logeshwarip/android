package com.example.netflix.constants;

import com.example.netflix.models.SearchSeries;
import com.example.netflix.models.Series;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("shows")
    Call<ArrayList<Series>> seriesList();

    @GET("search/shows?")
    Call<ArrayList<SearchSeries>> filterList(@Query("q") String data);

    @GET("shows/{id}/episodes")
    Call<ArrayList<Series>> season(@Path("id") int data);
}