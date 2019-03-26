package com.mspo.comspo.data.remote.model.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuditorProfileUpdateRequest {

    public AuditorProfileUpdateRequest(String name, String email, String mobile, String landline, String address, String education, String experience, String trainingDetails) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.landline = landline;
        this.address = address;
        this.education = education;
        this.experience = experience;
        this.trainingDetails = trainingDetails;
    }

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("landline")
    @Expose
    private String landline;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("education")
    @Expose
    private String education;
    @SerializedName("experience")
    @Expose
    private String experience;
    @SerializedName("training_details")
    @Expose
    private String trainingDetails;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLandline() {
        return landline;
    }

    public void setLandline(String landline) {
        this.landline = landline;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getTrainingDetails() {
        return trainingDetails;
    }

    public void setTrainingDetails(String trainingDetails) {
        this.trainingDetails = trainingDetails;
    }

}
