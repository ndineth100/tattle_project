package com.ceyentra.tattle.model;

public class NewsFeed {
    // This class is create temp for testing
    private String profileName;
    private int profileImgUrl;
    private String postLacation;
    private int postImageUrl;
    private String postTime;
    private String postDescription;
    private int noOfLikes;
    private int noOfDislikes;
    private int noOfShares;



    public NewsFeed() {
    }

    public NewsFeed(String profileName, int profileImgUrl, String postLacation, int postImageUrl, String postTime, String postDescription, int noOfLikes, int noOfDislikes, int noOfShares) {
        this.profileName = profileName;
        this.profileImgUrl = profileImgUrl;
        this.postLacation = postLacation;
        this.postImageUrl = postImageUrl;
        this.postTime = postTime;
        this.postDescription = postDescription;
        this.noOfLikes = noOfLikes;
        this.noOfDislikes = noOfDislikes;
        this.noOfShares = noOfShares;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public int getProfileImgUrl() {
        return profileImgUrl;
    }

    public void setProfileImgUrl(int profileImgUrl) {
        this.profileImgUrl = profileImgUrl;
    }

    public String getPostLacation() {
        return postLacation;
    }

    public void setPostLacation(String postLacation) {
        this.postLacation = postLacation;
    }

    public int getPostImageUrl() {
        return postImageUrl;
    }

    public void setPostImageUrl(int postImageUrl) {
        this.postImageUrl = postImageUrl;
    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }

    public String getPostDescription() {
        return postDescription;
    }

    public void setPostDescription(String postDescription) {
        this.postDescription = postDescription;
    }

    public int getNoOfLikes() {
        return noOfLikes;
    }

    public void setNoOfLikes(int noOfLikes) {
        this.noOfLikes = noOfLikes;
    }

    public int getNoOfDislikes() {
        return noOfDislikes;
    }

    public void setNoOfDislikes(int noOfDislikes) {
        this.noOfDislikes = noOfDislikes;
    }

    public int getNoOfShares() {
        return noOfShares;
    }

    public void setNoOfShares(int noOfShares) {
        this.noOfShares = noOfShares;
    }
}
