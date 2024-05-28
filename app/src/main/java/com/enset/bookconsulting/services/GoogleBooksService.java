package com.enset.bookconsulting.services;

import com.enset.bookconsulting.models.GoogleBooksResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GoogleBooksService {

    @GET("v1/volumes")
    Call<GoogleBooksResponse> searchBooks(@Query("q") String query);
}
