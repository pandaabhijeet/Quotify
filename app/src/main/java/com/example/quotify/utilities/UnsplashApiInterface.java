package com.example.quotify.utilities;

import static com.example.quotify.utilities.ApiUtilities.ACCESS_KEY;

import com.example.quotify.models.ImageModel;
import com.example.quotify.models.SearchModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UnsplashApiInterface
{
    //get Images for ImageModel
    @Headers("Authorization: Client-ID "+ ACCESS_KEY)
    @GET("photos")
    Call<List<ImageModel>> getImages(@Query("page") int page, @Query("per_page") int per_page);

    //get Image Details for ImageModel
    @Headers("Authorization: Client-ID "+ ACCESS_KEY)
    @GET("photos/{id}")
    Call<ImageModel> getImageDetails(@Path("id") String id);

    //get ImageModels for SearchModel
    @Headers("Authorization: Client-ID "+ ACCESS_KEY)
    @GET("/search/photos")
    Call<SearchModel> searchImages(@Query("query") String query);

}
