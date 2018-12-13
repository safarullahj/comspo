package com.mspo.comspo.data.remote.model.responses.internal_audit_details;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IndividualAuditDetailsResponse {

    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("home_address")
    @Expose
    private String homeAddress;
    @SerializedName("audit_year")
    @Expose
    private String auditYear;
    @SerializedName("single_audit_id")
    @Expose
    private Integer singleAuditId;
    @SerializedName("cb_name")
    @Expose
    private String cbName;
    @SerializedName("country_code")
    @Expose
    private String countryCode;
    @SerializedName("year")
    @Expose
    private String year;
    @SerializedName("compliance_percentage")
    @Expose
    private Integer compliancePercentage;
    @SerializedName("audit_status")
    @Expose
    private String auditStatus;
    @SerializedName("licence_no")
    @Expose
    private String licenceNo;
    @SerializedName("overall_score")
    @Expose
    private Integer overallScore;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("district")
    @Expose
    private String district;
    @SerializedName("land_condition")
    @Expose
    private String landCondition;
    @SerializedName("auditors")
    @Expose
    private List<Object> auditors = null;
    @SerializedName("uploaded_time")
    @Expose
    private String uploadedTime;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("audit_type")
    @Expose
    private String auditType;
    @SerializedName("farm")
    @Expose
    private String farm;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("audit_id")
    @Expose
    private Integer auditId;
    @SerializedName("grant_area")
    @Expose
    private String grantArea;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("chapter_compliance_percentage")
    @Expose
    private List<ChapterCompliancePercentage> chapterCompliancePercentage = null;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("sub_audit_id")
    @Expose
    private Integer subAuditId;
    @SerializedName("farm_name")
    @Expose
    private String farmName;
    @SerializedName("result_status")
    @Expose
    private String resultStatus;
    @SerializedName("code_version")
    @Expose
    private CodeVersion codeVersion;
    @SerializedName("ic_no")
    @Expose
    private String icNo;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getAuditYear() {
        return auditYear;
    }

    public void setAuditYear(String auditYear) {
        this.auditYear = auditYear;
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

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Integer getCompliancePercentage() {
        return compliancePercentage;
    }

    public void setCompliancePercentage(Integer compliancePercentage) {
        this.compliancePercentage = compliancePercentage;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getLicenceNo() {
        return licenceNo;
    }

    public void setLicenceNo(String licenceNo) {
        this.licenceNo = licenceNo;
    }

    public Integer getOverallScore() {
        return overallScore;
    }

    public void setOverallScore(Integer overallScore) {
        this.overallScore = overallScore;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getLandCondition() {
        return landCondition;
    }

    public void setLandCondition(String landCondition) {
        this.landCondition = landCondition;
    }

    public List<Object> getAuditors() {
        return auditors;
    }

    public void setAuditors(List<Object> auditors) {
        this.auditors = auditors;
    }

    public String getUploadedTime() {
        return uploadedTime;
    }

    public void setUploadedTime(String uploadedTime) {
        this.uploadedTime = uploadedTime;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAuditType() {
        return auditType;
    }

    public void setAuditType(String auditType) {
        this.auditType = auditType;
    }

    public String getFarm() {
        return farm;
    }

    public void setFarm(String farm) {
        this.farm = farm;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getAuditId() {
        return auditId;
    }

    public void setAuditId(Integer auditId) {
        this.auditId = auditId;
    }

    public String getGrantArea() {
        return grantArea;
    }

    public void setGrantArea(String grantArea) {
        this.grantArea = grantArea;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSubAuditId() {
        return subAuditId;
    }

    public void setSubAuditId(Integer subAuditId) {
        this.subAuditId = subAuditId;
    }

    public String getFarmName() {
        return farmName;
    }

    public void setFarmName(String farmName) {
        this.farmName = farmName;
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

    public String getIcNo() {
        return icNo;
    }

    public void setIcNo(String icNo) {
        this.icNo = icNo;
    }

}