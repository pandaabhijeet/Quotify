package com.example.quotify.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageModel {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("alt_description")
    @Expose
    private String altDescription;
    @SerializedName("likes")
    @Expose
    private int likes;
    @SerializedName("liked_by_user")
    @Expose
    private boolean likedByUser;
    @SerializedName("views")
    @Expose
    private int views;
    @SerializedName("downloads")
    @Expose
    private int downloads;
    @SerializedName("user")
    @Expose
    private UserInfoModel user;
    @SerializedName("urls")
    @Expose
    private UrlModel urls;
    @SerializedName("links")
    @Expose
    private LinksModel links;

    /**
     * No args constructor for use in serialization
     *
     */
    public ImageModel() {
    }

    /**
     *
     * @param urls
     * @param likedByUser
     * @param downloads
     * @param link
     * @param description
     * @param id
     * @param altDescription
     * @param user
     * @param views
     * @param likes
     */
    public ImageModel(String id, String description, String altDescription, int likes, boolean likedByUser,
                      int views, int downloads, UserInfoModel user, UrlModel urls, LinksModel links) {
        super();
        this.id = id;
        this.description = description;
        this.altDescription = altDescription;
        this.likes = likes;
        this.likedByUser = likedByUser;
        this.views = views;
        this.downloads = downloads;
        this.user = user;
        this.urls = urls;
        this.links = links;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAltDescription() {
        return altDescription;
    }

    public void setAltDescription(String altDescription) {
        this.altDescription = altDescription;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public boolean isLikedByUser() {
        return likedByUser;
    }

    public void setLikedByUser(boolean likedByUser) {
        this.likedByUser = likedByUser;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getDownloads() {
        return downloads;
    }

    public void setDownloads(int downloads) {
        this.downloads = downloads;
    }

    public UserInfoModel getUser() {
        return user;
    }

    public void setUser(UserInfoModel user) {
        this.user = user;
    }

    public UrlModel getUrls() {
        return urls;
    }

    public void setUrls(UrlModel urls) {
        this.urls = urls;
    }

    public LinksModel getLinks() {
        return links;
    }

    public void setLinks(LinksModel links) {
        this.links = links;
    }

}