package com.example.cardgenieapp.model;

public class PersonalCardDetailsModel {
    String username;
    String full_name;
    String phone_no;
    String phone_no2;
    String date_of_birth;
    String blood_type;
    String email;
    String img_url;
    public PersonalCardDetailsModel() {

    }


    public PersonalCardDetailsModel(String username, String full_name, String phone_no, String phone_no2, String date_of_birth, String blood_type, String email, String img_url) {
        this.username = username;
        this.full_name = full_name;
        this.phone_no = phone_no;
        this.phone_no2 = phone_no2;
        this.date_of_birth = date_of_birth;
        this.blood_type = blood_type;
        this.email = email;
        this.img_url = img_url;
    }

    public PersonalCardDetailsModel(String username, String full_name, String phone_no, String phone_no2, String date_of_birth, String blood_type, String email) {
        this.username = username;
        this.full_name = full_name;
        this.phone_no = phone_no;
        this.phone_no2 = phone_no2;
        this.date_of_birth = date_of_birth;
        this.blood_type = blood_type;
        this.email = email;

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

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

}
