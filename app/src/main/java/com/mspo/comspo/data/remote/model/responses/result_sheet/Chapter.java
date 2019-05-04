package com.mspo.comspo.data.remote.model.responses.result_sheet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Chapter implements Serializable {

    @SerializedName("major_criteria_score_percentage")
    @Expose
    private Double majorCriteriaScorePercentage;
    @SerializedName("mandatory_count")
    @Expose
    private Integer mandatoryCount;
    @SerializedName("applicable_points_count")
    @Expose
    private Integer applicablePointsCount;
    @SerializedName("compliance_count")
    @Expose
    private Integer complianceCount;
    @SerializedName("total_score")
    @Expose
    private Double totalScore;
    @SerializedName("na_count")
    @Expose
    private Integer naCount;
    @SerializedName("non_mandatory_count")
    @Expose
    private Integer nonMandatoryCount;
    @SerializedName("major_criteria_score")
    @Expose
    private Double majorCriteriaScore;
    @SerializedName("chapter_name")
    @Expose
    private String chapterName;
    @SerializedName("minor_criteria_score")
    @Expose
    private Double minorCriteriaScore;
    @SerializedName("compliance_percentage")
    @Expose
    private Double compliancePercentage;
    @SerializedName("minor_nc_count")
    @Expose
    private Integer minorNcCount;
    @SerializedName("chapter_position")
    @Expose
    private Integer chapterPosition;
    @SerializedName("major_nc_count")
    @Expose
    private Integer majorNcCount;
    @SerializedName("minor_criteria_score_percentage")
    @Expose
    private Double minorCriteriaScorePercentage;

    public Double getMajorCriteriaScorePercentage() {
        return majorCriteriaScorePercentage;
    }

    public void setMajorCriteriaScorePercentage(Double majorCriteriaScorePercentage) {
        this.majorCriteriaScorePercentage = majorCriteriaScorePercentage;
    }

    public Integer getMandatoryCount() {
        return mandatoryCount;
    }

    public void setMandatoryCount(Integer mandatoryCount) {
        this.mandatoryCount = mandatoryCount;
    }

    public Integer getApplicablePointsCount() {
        return applicablePointsCount;
    }

    public void setApplicablePointsCount(Integer applicablePointsCount) {
        this.applicablePointsCount = applicablePointsCount;
    }

    public Integer getComplianceCount() {
        return complianceCount;
    }

    public void setComplianceCount(Integer complianceCount) {
        this.complianceCount = complianceCount;
    }

    public Double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Double totalScore) {
        this.totalScore = totalScore;
    }

    public Integer getNaCount() {
        return naCount;
    }

    public void setNaCount(Integer naCount) {
        this.naCount = naCount;
    }

    public Integer getNonMandatoryCount() {
        return nonMandatoryCount;
    }

    public void setNonMandatoryCount(Integer nonMandatoryCount) {
        this.nonMandatoryCount = nonMandatoryCount;
    }

    public Double getMajorCriteriaScore() {
        return majorCriteriaScore;
    }

    public void setMajorCriteriaScore(Double majorCriteriaScore) {
        this.majorCriteriaScore = majorCriteriaScore;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public Double getMinorCriteriaScore() {
        return minorCriteriaScore;
    }

    public void setMinorCriteriaScore(Double minorCriteriaScore) {
        this.minorCriteriaScore = minorCriteriaScore;
    }

    public Double getCompliancePercentage() {
        return compliancePercentage;
    }

    public void setCompliancePercentage(Double compliancePercentage) {
        this.compliancePercentage = compliancePercentage;
    }

    public Integer getMinorNcCount() {
        return minorNcCount;
    }

    public void setMinorNcCount(Integer minorNcCount) {
        this.minorNcCount = minorNcCount;
    }

    public Integer getChapterPosition() {
        return chapterPosition;
    }

    public void setChapterPosition(Integer chapterPosition) {
        this.chapterPosition = chapterPosition;
    }

    public Integer getMajorNcCount() {
        return majorNcCount;
    }

    public void setMajorNcCount(Integer majorNcCount) {
        this.majorNcCount = majorNcCount;
    }

    public Double getMinorCriteriaScorePercentage() {
        return minorCriteriaScorePercentage;
    }

    public void setMinorCriteriaScorePercentage(Double minorCriteriaScorePercentage) {
        this.minorCriteriaScorePercentage = minorCriteriaScorePercentage;
    }

}
