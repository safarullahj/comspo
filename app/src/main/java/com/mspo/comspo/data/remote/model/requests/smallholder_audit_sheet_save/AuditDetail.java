package com.mspo.comspo.data.remote.model.requests.smallholder_audit_sheet_save;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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

}