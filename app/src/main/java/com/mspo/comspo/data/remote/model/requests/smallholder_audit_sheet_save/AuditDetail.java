package com.mspo.comspo.data.remote.model.requests.smallholder_audit_sheet_save;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuditDetail {

    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("country_code")
    @Expose
    private String countryCode;
    @SerializedName("district")
    @Expose
    private String district;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("farm_name")
    @Expose
    private String farmName;
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
    @SerializedName("start_date")
    @Expose
    private String startDate;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getFarmName() {
        return farmName;
    }

    public void setFarmName(String farmName) {
        this.farmName = farmName;
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

}


/*
public class AuditDetail {

    @SerializedName("registration_number")
    @Expose
    private String registrationNumber;
    @SerializedName("grant_area_in_ha")
    @Expose
    private String grantAreaInHa;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("district")
    @Expose
    private String district;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("tehsil")
    @Expose
    private String tehsil;
    @SerializedName("village")
    @Expose
    private String village;
    @SerializedName("officer_name")
    @Expose
    private String officerName;
    @SerializedName("crop")
    @Expose
    private String crop;
    @SerializedName("total_production_area")
    @Expose
    private String totalProductionArea;

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getGrantAreaInHa() {
        return grantAreaInHa;
    }

    public void setGrantAreaInHa(String grantAreaInHa) {
        this.grantAreaInHa = grantAreaInHa;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTehsil() {
        return tehsil;
    }

    public void setTehsil(String tehsil) {
        this.tehsil = tehsil;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getOfficerName() {
        return officerName;
    }

    public void setOfficerName(String officerName) {
        this.officerName = officerName;
    }

    public String getCrop() {
        return crop;
    }

    public void setCrop(String crop) {
        this.crop = crop;
    }

    public String getTotalProductionArea() {
        return totalProductionArea;
    }

    public void setTotalProductionArea(String totalProductionArea) {
        this.totalProductionArea = totalProductionArea;
    }

}*/
