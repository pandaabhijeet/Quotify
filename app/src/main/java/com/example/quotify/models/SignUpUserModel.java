package com.example.quotify.models;

public class SignUpUserModel
{
    String id,username, email, password, error;
    Boolean success;

    public SignUpUserModel(String id, String username, String email, String password)
    {
        this.username = username;
        this.email = email;
        this.password = password;
        this.id = id;
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
