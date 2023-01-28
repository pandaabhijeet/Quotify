package com.example.quotify.models;

public class SignUpUserModel
{
    String id,username, email, password, error, profile_image, fav_quote;
    Boolean success;

    public SignUpUserModel(String id, String username, String email, String password, String error, String profile_image, String fav_quote, Boolean success)
    {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.error = error;
        this.profile_image = profile_image;
        this.fav_quote = fav_quote;
        this.success = success;
    }

    public SignUpUserModel(String username, String email, String password)
    {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public SignUpUserModel(String error, Boolean success) {
        this.error = error;
        this.success = success;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public String getFav_quote() {
        return fav_quote;
    }

    public void setFav_quote(String fav_quote) {
        this.fav_quote = fav_quote;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
