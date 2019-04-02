package com.mspo.comspo.data.remote.model.responses.offline_audit_sheet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Acc extends RealmObject  {

    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("files")
    @Expose
    private RealmList<String> files;
    @SerializedName("acc_id")
    @Expose
    private Integer accId;
    @SerializedName("red_percentage")
    @Expose
    private Integer redPercentage;
    @SerializedName("audit_id")
    @Expose
    private Integer auditId;
    @SerializedName("aics")
    @Expose
    private RealmList<Aic> aics ;
    @SerializedName("chapter_id")
    @Expose
    private Integer chapterId;
    @SerializedName("green_percentage")
    @Expose
    private Double greenPercentage;
    @SerializedName("compliance_value")
    @Expose
    private Double complianceValue;
    @SerializedName("amber_percentage")
    @Expose
    private Double amberPercentage;
    @SerializedName("criterion_description")
    @Expose
    private String criterionDescription;
    @SerializedName("last_edited_time")
    @Expose
    private String lastEditedTime;
    @SerializedName("criterion_id")
    @Expose
    private Integer criterionId;
    @SerializedName("chapter_description")
    @Expose
    private String chapterDescription;
    @SerializedName("chapter_position")
    @Expose
    private Integer chapterPosition;
    @SerializedName("criterion_position")
    @Expose
    private Integer criterionPosition;
    @SerializedName("chapter_name")
    @Expose
    private String chapterName;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public RealmList<String> getFiles() {
        return files;
    }

    public void setFiles(RealmList<String> files) {
        this.files = files;
    }

    public Integer getAccId() {
        return accId;
    }

    public void setAccId(Integer accId) {
        this.accId = accId;
    }

    public Integer getRedPercentage() {
        return redPercentage;
    }

    public void setRedPercentage(Integer redPercentage) {
        this.redPercentage = redPercentage;
    }

    public Integer getAuditId() {
        return auditId;
    }

    public void setAuditId(Integer auditId) {
        this.auditId = auditId;
    }

    public RealmList<Aic> getAics() {
        return aics;
    }

    public void setAics(RealmList<Aic> aics) {
        this.aics = aics;
    }

    public Integer getChapterId() {
        return chapterId;
    }

    public void setChapterId(Integer chapterId) {
        this.chapterId = chapterId;
    }

    public Double getGreenPercentage() {
        return greenPercentage;
    }

    public void setGreenPercentage(Double greenPercentage) {
        this.greenPercentage = greenPercentage;
    }

    public Double getComplianceValue() {
        return complianceValue;
    }

    public void setComplianceValue(Double complianceValue) {
        this.complianceValue = complianceValue;
    }

    public Double getAmberPercentage() {
        return amberPercentage;
    }

    public void setAmberPercentage(Double amberPercentage) {
        this.amberPercentage = amberPercentage;
    }

    public String getCriterionDescription() {
        return criterionDescription;
    }

    public void setCriterionDescription(String criterionDescription) {
        this.criterionDescription = criterionDescription;
    }

    public String getLastEditedTime() {
        return lastEditedTime;
    }

    public void setLastEditedTime(String lastEditedTime) {
        this.lastEditedTime = lastEditedTime;
    }

    public Integer getCriterionId() {
        return criterionId;
    }

    public void setCriterionId(Integer criterionId) {
        this.criterionId = criterionId;
    }

    public String getChapterDescription() {
        return chapterDescription;
    }

    public void setChapterDescription(String chapterDescription) {
        this.chapterDescription = chapterDescription;
    }

    public Integer getChapterPosition() {
        return chapterPosition;
    }

    public void setChapterPosition(Integer chapterPosition) {
        this.chapterPosition = chapterPosition;
    }

    public Integer getCriterionPosition() {
        return criterionPosition;
    }

    public void setCriterionPosition(Integer criterionPosition) {
        this.criterionPosition = criterionPosition;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

}
