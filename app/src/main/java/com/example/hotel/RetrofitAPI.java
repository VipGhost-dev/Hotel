package com.example.hotel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface RetrofitAPI {

    // as we are making a post request to post a data
    // so we are annotating it with post
    // and along with that we are passing a parameter as users
    @POST("Hotels")
    //on below line we are creating a method to post our data.
    Call<DataStorage> createPost(@Body DataStorage dataStorage);

    @GET("Hotels/{id}")
    Call<DataStorage> getData(@Query("id") int id);

    @PUT("Hotels/{id}")
    Call<DataStorage> updateData(@Path("id") int id, @Body DataStorage dataModal);

    @DELETE("Hotels/{id}")
    Call<Void> deleteData(@Path("id") int id);
}
