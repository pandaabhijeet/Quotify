package com.example.quotify.models;

public class NotificationModel
{
    private String profileImage,userName;
    private int likeCount, shareCount, commentCount;
    private String dateTimeStamp;

    public NotificationModel(String profileImage, String userName, int likeCount, int shareCount, int commentCount, String dateTimeStamp) {
        this.profileImage = profileImage;
        this.userName = userName;
        this.likeCount = likeCount;
        this.shareCount = shareCount;
        this.commentCount = commentCount;
        this.dateTimeStamp = dateTimeStamp;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getDateTimeStamp() {
        return dateTimeStamp;
    }

    public void setDateTimeStamp(String dateTimeStamp) {
        this.dateTimeStamp = dateTimeStamp;
    }
}
