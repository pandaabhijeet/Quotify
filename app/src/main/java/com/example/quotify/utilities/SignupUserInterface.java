package com.example.quotify.utilities;

import com.example.quotify.models.LoginUserModel;
import com.example.quotify.models.ProfileImageModel;
import com.example.quotify.models.SignUpUserModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

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

    @Multipart
    @POST ("api/post/profile_image/")
    Call<ProfileImageModel> uploadProfileImage
            (
                @Part MultipartBody.Part profileImage,
                @Part("userId")RequestBody userId
            );

    @Headers("Content-Type: application/json")
    @GET("api/post/{user}")
    Call<SignUpUserModel> currentUser
            (
                    @Path("user") String userId
            );


}
