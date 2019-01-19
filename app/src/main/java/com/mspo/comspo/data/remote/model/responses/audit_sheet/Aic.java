package com.mspo.comspo.data.remote.model.responses.audit_sheet;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Aic implements Serializable{

    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("indicator_description")
    @Expose
    private String indicatorDescription;
    @SerializedName("evidence_to_check")
    @Expose
    private List<EvidenceToCheck> evidenceToCheck = null;
    @SerializedName("indicator_suggestion")
    @Expose
    private String indicatorSuggestion;
    @SerializedName("suggestions")
    @Expose
    private List<Suggestion> suggestions = null;
    @SerializedName("issues_to_check")
    @Expose
    private List<IssuesToCheck> issuesToCheck = null;
    @SerializedName("compliance_value")
    @Expose
    private Double complianceValue;
    @SerializedName("indicator_type")
    @Expose
    private String indicatorType;
    @SerializedName("indicator_id")
    @Expose
    private Integer indicatorId;
    @SerializedName("last_edited_time")
    @Expose
    private String lastEditedTime;
    @SerializedName("position")
    @Expose
    private Integer position;
    @SerializedName("files")
    @Expose
    private List<Object> files = null;
    @SerializedName("audit_indicator_id")
    @Expose
    private Integer auditIndicatorId;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getIndicatorDescription() {
        return indicatorDescription;
    }

    public void setIndicatorDescription(String indicatorDescription) {
        this.indicatorDescription = indicatorDescription;
    }

    public List<EvidenceToCheck> getEvidenceToCheck() {
        return evidenceToCheck;
    }

    public void setEvidenceToCheck(List<EvidenceToCheck> evidenceToCheck) {
        this.evidenceToCheck = evidenceToCheck;
    }

    public String  getIndicatorSuggestion() {
        return indicatorSuggestion;
    }

    public void setIndicatorSuggestion(String indicatorSuggestion) {
        this.indicatorSuggestion = indicatorSuggestion;
    }

    public List<Suggestion> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<Suggestion> suggestions) {
        this.suggestions = suggestions;
    }

    public List<IssuesToCheck> getIssuesToCheck() {
        return issuesToCheck;
    }

    public void setIssuesToCheck(List<IssuesToCheck> issuesToCheck) {
        this.issuesToCheck = issuesToCheck;
    }

    public Double getComplianceValue() {
        return complianceValue;
    }

    public void setComplianceValue(Double complianceValue) {
        this.complianceValue = complianceValue;
    }

    public String getIndicatorType() {
        return indicatorType;
    }

    public void setIndicatorType(String indicatorType) {
        this.indicatorType = indicatorType;
    }

    public Integer getIndicatorId() {
        return indicatorId;
    }

    public void setIndicatorId(Integer indicatorId) {
        this.indicatorId = indicatorId;
    }

    public String getLastEditedTime() {
        return lastEditedTime;
    }

    public void setLastEditedTime(String lastEditedTime) {
        this.lastEditedTime = lastEditedTime;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public List<Object> getFiles() {
        return files;
    }

    public void setFiles(List<Object> files) {
        this.files = files;
    }

    public Integer getAuditIndicatorId() {
        return auditIndicatorId;
    }

    public void setAuditIndicatorId(Integer auditIndicatorId) {
        this.auditIndicatorId = auditIndicatorId;
    }

}
