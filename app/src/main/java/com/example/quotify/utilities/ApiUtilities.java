package com.example.quotify.utilities;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiUtilities
{
    public static final String BASE_URL = "https://api.unsplash.com/";
    public static final String ACCESS_KEY = "KIX9LFBqxoI8kW54LyDpTP_BsxJjsjOrWfK1wCIniOI";

    public static Retrofit retrofit = null;

    public static  UnsplashApiInterface getApiInterface()
        {
            if (retrofit == null)
            {
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
            return retrofit.create(UnsplashApiInterface.class);
        }

}
