package com.example.quotify.models;

public class PostModel
{
    String givenName,uniqueName,dateTime,postUrl;
    int likeCount,shareCount,commentCount;

    public PostModel(String givenName, String uniqueName, String dateTime, String postUrl, int likeCount, int shareCount, int commentCount) {
        this.givenName = givenName;
        this.uniqueName = uniqueName;
        this.dateTime = dateTime;
        this.postUrl = postUrl;
        this.likeCount = likeCount;
        this.shareCount = shareCount;
        this.commentCount = commentCount;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getUniqueName() {
        return uniqueName;
    }

    public void setUniqueName(String uniqueName) {
        this.uniqueName = uniqueName;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getPostUrl() {
        return postUrl;
    }

    public void setPostUrl(String postUrl) {
        this.postUrl = postUrl;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getShareCount() {
        return shareCount;
    }

    public void setShareCount(int shareCount) {
        this.shareCount = shareCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }
}
