package com.example.quotify.models;

public class LoginUserModel
{
    String error, email,password, id, token;
    Boolean success;


    public LoginUserModel(String email, String password)
    {
        this.email = email;
        this.password = password;
    }

    public LoginUserModel(String error, String email, String password, String id, String token, Boolean success) {
        this.error = error;
        this.email = email;
        this.password = password;
        this.id = id;
        this.token = token;
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
