package com.example.cardgenieapp.model;

public class SignupModel {

    String uid,profile_pic_url,username,full_name,phone_no,phone_no2,date_of_birth,blood_type,email,password;

    public SignupModel() {
    }

    public SignupModel(String uid, String profile_pic_url, String username, String full_name, String phone_no, String phone_no2, String date_of_birth, String blood_type, String email, String password) {
        this.uid = uid;
        this.profile_pic_url = profile_pic_url;
        this.username = username;
        this.full_name = full_name;
        this.phone_no = phone_no;
        this.phone_no2 = phone_no2;
        this.date_of_birth = date_of_birth;
        this.blood_type = blood_type;
        this.email = email;
        this.password = password;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getProfile_pic_url() {
        return profile_pic_url;
    }

    public void setProfile_pic_url(String profile_pic_url) {
        this.profile_pic_url = profile_pic_url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getPhone_no2() {
        return phone_no2;
    }

    public void setPhone_no2(String phone_no2) {
        this.phone_no2 = phone_no2;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getBlood_type() {
        return blood_type;
    }

    public void setBlood_type(String blood_type) {
        this.blood_type = blood_type;
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
}
