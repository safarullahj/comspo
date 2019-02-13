package com.mspo.comspo.data.remote.model.responses.auditor_home_audit_list;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Audit {

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
    @SerializedName("audit_year")
    @Expose
    private String auditYear;
    @SerializedName("year")
    @Expose
    private String year;
    @SerializedName("audit_status")
    @Expose
    private String auditStatus;
    @SerializedName("overall_score")
    @Expose
    private Double overallScore;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("certification_body")
    @Expose
    private String certificationBody;
    @SerializedName("auditors")
    @Expose
    private List<Auditor> auditors = null;
    @SerializedName("start_date")
    @Expose
    private String startDate;

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

    public String getAuditYear() {
        return auditYear;
    }

    public void setAuditYear(String auditYear) {
        this.auditYear = auditYear;
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

    public Double getOverallScore() {
        return overallScore;
    }

    public void setOverallScore(Double overallScore) {
        this.overallScore = overallScore;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

}
