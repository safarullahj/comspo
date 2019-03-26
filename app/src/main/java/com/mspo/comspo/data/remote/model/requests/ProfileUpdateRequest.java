package com.mspo.comspo.data.remote.model.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileUpdateRequest {

    public ProfileUpdateRequest(String address, String countryCode, String district, String email, Integer farmerId, String grantArea, String homeAddress, String icNo, String landCondition, String licenceNo, String name, String phone) {
        this.address = address;
        this.countryCode = countryCode;
        this.district = district;
        this.email = email;
        this.farmerId = farmerId;
        this.grantArea = grantArea;
        this.homeAddress = homeAddress;
        this.icNo = icNo;
        this.landCondition = landCondition;
        this.licenceNo = licenceNo;
        this.name = name;
        this.phone = phone;
    }

    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("country_code")
    @Expose
    private String countryCode;
    @SerializedName("district")
    @Expose
    private String district;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("farmer_id")
    @Expose
    private Integer farmerId;
    @SerializedName("grant_area")
    @Expose
    private String grantArea;
    @SerializedName("home_address")
    @Expose
    private String homeAddress;
    @SerializedName("ic_no")
    @Expose
    private String icNo;
    @SerializedName("land_condition")
    @Expose
    private String landCondition;
    @SerializedName("licence_no")
    @Expose
    private String licenceNo;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("phone")
    @Expose
    private String phone;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getFarmerId() {
        return farmerId;
    }

    public void setFarmerId(Integer farmerId) {
        this.farmerId = farmerId;
    }

    public String getGrantArea() {
        return grantArea;
    }

    public void setGrantArea(String grantArea) {
        this.grantArea = grantArea;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getIcNo() {
        return icNo;
    }

    public void setIcNo(String icNo) {
        this.icNo = icNo;
    }

    public String getLandCondition() {
        return landCondition;
    }

    public void setLandCondition(String landCondition) {
        this.landCondition = landCondition;
    }

    public String getLicenceNo() {
        return licenceNo;
    }

    public void setLicenceNo(String licenceNo) {
        this.licenceNo = licenceNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
