package com.example.cardgenieapp.model;

public class GamingCardDetailsModel {
    String bio;
    String insta_link;
    String facebook_link;
    String youtube_link;
    String twitch_link;
    String discord_link;

    public GamingCardDetailsModel() {

    }
    public GamingCardDetailsModel(String bio, String insta_link, String facebook_link, String youtube_link, String twitch_link, String discord_link) {
        this.bio = bio;
        this.insta_link = insta_link;
        this.facebook_link = facebook_link;
        this.youtube_link = youtube_link;
        this.twitch_link = twitch_link;
        this.discord_link = discord_link;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getInsta_link() {
        return insta_link;
    }

    public void setInsta_link(String insta_link) {
        this.insta_link = insta_link;
    }

    public String getFacebook_link() {
        return facebook_link;
    }

    public void setFacebook_link(String facebook_link) {
        this.facebook_link = facebook_link;
    }

    public String getYoutube_link() {
        return youtube_link;
    }

    public void setYoutube_link(String youtube_link) {
        this.youtube_link = youtube_link;
    }

    public String getTwitch_link() {
        return twitch_link;
    }

    public void setTwitch_link(String twitch_link) {
        this.twitch_link = twitch_link;
    }

    public String getDiscord_link() {
        return discord_link;
    }

    public void setDiscord_link(String discord_link) {
        this.discord_link = discord_link;
    }
}
