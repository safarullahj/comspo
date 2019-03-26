package com.mspo.comspo.data.remote.model.responses.group_audit_details;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubAudit {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("uploaded_time")
    @Expose
    private String uploadedTime;
    @SerializedName("ic_no")
    @Expose
    private String icNo;
    @SerializedName("home_address")
    @Expose
    private String homeAddress;
    @SerializedName("district")
    @Expose
    private String district;
    @SerializedName("sub_audit_id")
    @Expose
    private Integer subAuditId;
    @SerializedName("land_condition")
    @Expose
    private String landCondition;
    @SerializedName("grant_area")
    @Expose
    private String grantArea;
    @SerializedName("result_status")
    @Expose
    private String resultStatus;
    @SerializedName("code_version")
    @Expose
    private CodeVersion codeVersion;
    @SerializedName("single_audit_id")
    @Expose
    private Integer singleAuditId;
    @SerializedName("cb_name")
    @Expose
    private String cbName;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("chapter_compliance_percentage")
    @Expose
    private List<ChapterCompliancePercentage> chapterCompliancePercentage = null;
    @SerializedName("country_code")
    @Expose
    private String countryCode;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("compliance_percentage")
    @Expose
    private Double compliancePercentage;
    @SerializedName("farm_name")
    @Expose
    private String farmName;
    @SerializedName("licence_no")
    @Expose
    private String licenceNo;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUploadedTime() {
        return uploadedTime;
    }

    public void setUploadedTime(String uploadedTime) {
        this.uploadedTime = uploadedTime;
    }

    public String getIcNo() {
        return icNo;
    }

    public void setIcNo(String icNo) {
        this.icNo = icNo;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Integer getSubAuditId() {
        return subAuditId;
    }

    public void setSubAuditId(Integer subAuditId) {
        this.subAuditId = subAuditId;
    }

    public String getLandCondition() {
        return landCondition;
    }

    public void setLandCondition(String landCondition) {
        this.landCondition = landCondition;
    }

    public String getGrantArea() {
        return grantArea;
    }

    public void setGrantArea(String grantArea) {
        this.grantArea = grantArea;
    }

    public String getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(String resultStatus) {
        this.resultStatus = resultStatus;
    }

    public CodeVersion getCodeVersion() {
        return codeVersion;
    }

    public void setCodeVersion(CodeVersion codeVersion) {
        this.codeVersion = codeVersion;
    }

    public Integer getSingleAuditId() {
        return singleAuditId;
    }

    public void setSingleAuditId(Integer singleAuditId) {
        this.singleAuditId = singleAuditId;
    }

    public String getCbName() {
        return cbName;
    }

    public void setCbName(String cbName) {
        this.cbName = cbName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<ChapterCompliancePercentage> getChapterCompliancePercentage() {
        return chapterCompliancePercentage;
    }

    public void setChapterCompliancePercentage(List<ChapterCompliancePercentage> chapterCompliancePercentage) {
        this.chapterCompliancePercentage = chapterCompliancePercentage;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getCompliancePercentage() {
        return compliancePercentage;
    }

    public void setCompliancePercentage(Double compliancePercentage) {
        this.compliancePercentage = compliancePercentage;
    }

    public String getFarmName() {
        return farmName;
    }

    public void setFarmName(String farmName) {
        this.farmName = farmName;
    }

    public String getLicenceNo() {
        return licenceNo;
    }

    public void setLicenceNo(String licenceNo) {
        this.licenceNo = licenceNo;
    }

}
