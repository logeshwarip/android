package com.example.netflix.constants;

import com.example.netflix.models.Shows;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("shows")
    Call<ArrayList<Shows>> showsList();

}
