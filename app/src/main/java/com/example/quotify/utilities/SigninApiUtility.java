package com.example.quotify.utilities;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SigninApiUtility
{
    //public static final String BASE_URL = "https://quotifyapplication.herokuapp.com/";
    public static final String BASE_URL = "https://quotifyapplication.onrender.com";
    public static SigninApiUtility mInstance;
    private Retrofit retrofit;

   private SigninApiUtility()
   {
       retrofit = new Retrofit.Builder()
               .baseUrl(BASE_URL)
               .addConverterFactory(GsonConverterFactory.create())
               .build();
   }

   public static synchronized SigninApiUtility getApiInstance()
   {
       if(mInstance == null)
       {
           mInstance = new SigninApiUtility();
       }

       return mInstance;
   }

   public  SignupUserInterface getApiInterface()
   {
       return retrofit.create(SignupUserInterface.class);
   }
}
