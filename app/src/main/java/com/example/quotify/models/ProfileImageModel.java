package com.example.quotify.models;

import java.io.File;

public class ProfileImageModel
{
    String id,error;
    File data;
    Boolean success;

    public ProfileImageModel(String id, File data) {
        this.id = id;
        this.data = data;
    }

    public ProfileImageModel(String id, File data, String error, Boolean success) {
        this.id = id;
        this.data = data;
        this.error = error;
        this.success = success;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public File getData() {
        return data;
    }

    public void setData(File data) {
        this.data = data;
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
