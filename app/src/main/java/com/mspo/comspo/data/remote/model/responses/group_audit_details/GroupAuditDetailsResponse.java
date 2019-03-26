package com.mspo.comspo.data.remote.model.responses.group_audit_details;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GroupAuditDetailsResponse {

    @SerializedName("audit_year")
    @Expose
    private String auditYear;
    @SerializedName("group_name")
    @Expose
    private String groupName;
    @SerializedName("avg_overall_score")
    @Expose
    private Integer avgOverallScore;
    @SerializedName("year")
    @Expose
    private String year;
    @SerializedName("audit_status")
    @Expose
    private String auditStatus;
    @SerializedName("overall_score")
    @Expose
    private Integer overallScore;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("certification_body")
    @Expose
    private String certificationBody;
    @SerializedName("auditors")
    @Expose
    private List<Auditor> auditors = null;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("audit_type")
    @Expose
    private String auditType;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("audit_id")
    @Expose
    private Integer auditId;
    @SerializedName("farm")
    @Expose
    private String farm;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("no_of_farmers")
    @Expose
    private String noOfFarmers;
    @SerializedName("auditor_audit_status")
    @Expose
    private Boolean auditorAuditStatus;
    @SerializedName("sub_audits")
    @Expose
    private List<SubAudit> subAudits = null;
    @SerializedName("head_farmer")
    @Expose
    private String headFarmer;
    @SerializedName("group_audit_id")
    @Expose
    private Integer groupAuditId;

    public String getAuditYear() {
        return auditYear;
    }

    public void setAuditYear(String auditYear) {
        this.auditYear = auditYear;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getAvgOverallScore() {
        return avgOverallScore;
    }

    public void setAvgOverallScore(Integer avgOverallScore) {
        this.avgOverallScore = avgOverallScore;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
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

    public String getCertificationBody() {
        return certificationBody;
    }

    public void setCertificationBody(String certificationBody) {
        this.certificationBody = certificationBody;
    }

    public List<Auditor> getAuditors() {
        return auditors;
    }

    public void setAuditors(List<Auditor> auditors) {
        this.auditors = auditors;
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

    public String getFarm() {
        return farm;
    }

    public void setFarm(String farm) {
        this.farm = farm;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getNoOfFarmers() {
        return noOfFarmers;
    }

    public void setNoOfFarmers(String noOfFarmers) {
        this.noOfFarmers = noOfFarmers;
    }

    public Boolean getAuditorAuditStatus() {
        return auditorAuditStatus;
    }

    public void setAuditorAuditStatus(Boolean auditorAuditStatus) {
        this.auditorAuditStatus = auditorAuditStatus;
    }

    public List<SubAudit> getSubAudits() {
        return subAudits;
    }

    public void setSubAudits(List<SubAudit> subAudits) {
        this.subAudits = subAudits;
    }

    public String getHeadFarmer() {
        return headFarmer;
    }

    public void setHeadFarmer(String headFarmer) {
        this.headFarmer = headFarmer;
    }

    public Integer getGroupAuditId() {
        return groupAuditId;
    }

    public void setGroupAuditId(Integer groupAuditId) {
        this.groupAuditId = groupAuditId;
    }

}
