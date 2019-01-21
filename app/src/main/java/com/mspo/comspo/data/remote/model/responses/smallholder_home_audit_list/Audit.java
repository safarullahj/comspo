package com.mspo.comspo.data.remote.model.responses.smallholder_home_audit_list;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Audit {

    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("audit_type")
    @Expose
    private String auditType;
    @SerializedName("certification_body")
    @Expose
    private String certificationBody;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("audit_id")
    @Expose
    private Integer auditId;
    @SerializedName("farm")
    @Expose
    private String farm;
    @SerializedName("audit_status")
    @Expose
    private String auditStatus;
    @SerializedName("year")
    @Expose
    private String year;
    @SerializedName("overall_score")
    @Expose
    private Double overallScore;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAuditType() {
        return auditType;
    }

    public void setAuditType(String auditType) {
        this.auditType = auditType;
    }

    public String getCertificationBody() {
        return certificationBody;
    }

    public void setCertificationBody(String certificationBody) {
        this.certificationBody = certificationBody;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Double getOverallScore() {
        return overallScore;
    }

    public void setOverallScore(Double overallScore) {
        this.overallScore = overallScore;
    }

}
