package com.example.netflix.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchSeries {
    @SerializedName("show")
    @Expose
    public Series show;

    public Series getShow() {
        return show;
    }

    public void setShow(Series show) {
        this.show = show;
    }
}
