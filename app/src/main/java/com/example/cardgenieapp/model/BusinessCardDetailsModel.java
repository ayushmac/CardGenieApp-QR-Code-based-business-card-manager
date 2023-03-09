package com.example.cardgenieapp.model;

public class BusinessCardDetailsModel {
    String full_name;
    String phone_no;
    String phone_no2;
    String email;
    String designation;
    String linkedinlink;
    String address;

    public BusinessCardDetailsModel() {

    }

    public BusinessCardDetailsModel(String full_name, String phone_no, String phone_no2, String email, String designation, String linkedinlink, String address) {
        this.full_name = full_name;
        this.phone_no = phone_no;
        this.phone_no2 = phone_no2;
        this.email = email;
        this.designation = designation;
        this.linkedinlink = linkedinlink;
        this.address = address;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getLinkedinlink() {
        return linkedinlink;
    }

    public void setLinkedinlink(String linkedinlink) {
        this.linkedinlink = linkedinlink;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
