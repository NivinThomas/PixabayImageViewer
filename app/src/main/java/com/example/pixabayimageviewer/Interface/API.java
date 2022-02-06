package com.example.pixabayimageviewer.Interface;

import com.example.pixabayimageviewer.ModelClasses.Responses;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface API {
    @GET("?key=25548644-f5fd8560018f1aa02717ecfdf")
    Call<Responses> imageGet(@Query("page")int page,
                             @Query("per_page")int per_page);
}
