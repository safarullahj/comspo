package com.mspo.comspo.data.remote.model.responses.result_sheet;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultSheetResponse implements Serializable {

    @SerializedName("compliance_percentage")
    @Expose
    private Double compliancePercentage;
    @SerializedName("total_score")
    @Expose
    private Double totalScore;
    @SerializedName("total_applicable_points_count")
    @Expose
    private Double totalApplicablePointsCount;
    @SerializedName("chapters")
    @Expose
    private List<Chapter> chapters = null;

    public Double getCompliancePercentage() {
        return compliancePercentage;
    }

    public void setCompliancePercentage(Double compliancePercentage) {
        this.compliancePercentage = compliancePercentage;
    }

    public Double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Double totalScore) {
        this.totalScore = totalScore;
    }

    public Double getTotalApplicablePointsCount() {
        return totalApplicablePointsCount;
    }

    public void setTotalApplicablePointsCount(Double totalApplicablePointsCount) {
        this.totalApplicablePointsCount = totalApplicablePointsCount;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }

}
