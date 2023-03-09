package com.example.cardgenieapp.model;

public class SocialMediaDetailsModel {
    String bio;
    String insta_link;
    String facebook_link;
    String reddit_link;
    String twitter_link;
    String linkedin_link;
    String youtube_link;
    String twitch_link;
    String github_link;
    String whatsapp_link;
    String spotify_link;


    public SocialMediaDetailsModel(String bio, String insta_link, String facebook_link, String reddit_link, String twitter_link, String linkedin_link, String youtube_link, String twitch_link, String github_link, String whatsapp_link, String spotify_link) {
        this.bio = bio;
        this.insta_link = insta_link;
        this.facebook_link = facebook_link;
        this.reddit_link = reddit_link;
        this.twitter_link = twitter_link;
        this.linkedin_link = linkedin_link;
        this.youtube_link = youtube_link;
        this.twitch_link = twitch_link;
        this.github_link = github_link;
        this.whatsapp_link = whatsapp_link;
        this.spotify_link = spotify_link;
    }


    public SocialMediaDetailsModel() {

    }
    public SocialMediaDetailsModel(String insta_link, String facebook_link, String reddit_link, String twitter_link, String linkedin_link, String youtube_link, String twitch_link, String github_link, String whatsapp_link) {
        this.insta_link = insta_link;
        this.facebook_link = facebook_link;
        this.reddit_link = reddit_link;
        this.twitter_link = twitter_link;
        this.linkedin_link = linkedin_link;
        this.youtube_link = youtube_link;
        this.twitch_link = twitch_link;
        this.github_link = github_link;
        this.whatsapp_link = whatsapp_link;
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

    public String getReddit_link() {
        return reddit_link;
    }

    public void setReddit_link(String reddit_link) {
        this.reddit_link = reddit_link;
    }

    public String getTwitter_link() {
        return twitter_link;
    }

    public void setTwitter_link(String twitter_link) {
        this.twitter_link = twitter_link;
    }

    public String getLinkedin_link() {
        return linkedin_link;
    }

    public void setLinkedin_link(String linkedin_link) {
        this.linkedin_link = linkedin_link;
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

    public String getGithub_link() {
        return github_link;
    }

    public void setGithub_link(String github_link) {
        this.github_link = github_link;
    }

    public String getWhatsapp_link() {
        return whatsapp_link;
    }

    public void setWhatsapp_link(String whatsapp_link) {
        this.whatsapp_link = whatsapp_link;
    }

    public String getSpotify_link() {
        return spotify_link;
    }

    public void setSpotify_link(String spotify_link) {
        this.spotify_link = spotify_link;
    }
}
