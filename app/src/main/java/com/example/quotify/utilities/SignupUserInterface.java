package com.example.quotify.utilities;

import com.example.quotify.models.LoginUserModel;
import com.example.quotify.models.SignUpUserModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface SignupUserInterface
{

    @Headers("Content-Type: application/json")
    @POST("api/user/register")
    Call<SignUpUserModel> register
           (
                @Body SignUpUserModel signUpUserModel
           );

    @Headers("Content-Type: application/json")
    @POST("api/user/login")
    Call<LoginUserModel> loginUser
           (
             @Body LoginUserModel loginUserModel
           );
}
