package com.example.quotify.models;

public class ImageModel
{
    private UrlModel urls;

    public ImageModel(UrlModel urls) {
        this.urls = urls;
    }

    public UrlModel getUrls() {
        return urls;
    }

    public void setUrls(UrlModel urls) {
        this.urls = urls;
    }
}
