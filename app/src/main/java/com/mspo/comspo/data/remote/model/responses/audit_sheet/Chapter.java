package com.mspo.comspo.data.remote.model.responses.audit_sheet;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Chapter implements Serializable{

    @SerializedName("audit_chapter_id")
    @Expose
    private Integer auditChapterId;
    @SerializedName("critical_improvement_areas")
    @Expose
    private String criticalImprovementAreas;
    @SerializedName("audit_id")
    @Expose
    private Integer auditId;
    @SerializedName("chapter_name")
    @Expose
    private String chapterName;
    @SerializedName("graph_color")
    @Expose
    private String graphColor;
    @SerializedName("compliance_percentage")
    @Expose
    private Integer compliancePercentage;
    @SerializedName("accs")
    @Expose
    private List<Acc> accs = null;
    @SerializedName("other_improvement_areas")
    @Expose
    private String otherImprovementAreas;
    @SerializedName("red_percentage")
    @Expose
    private Integer redPercentage;
    @SerializedName("strength_area")
    @Expose
    private String strengthArea;
    @SerializedName("chapter_id")
    @Expose
    private Integer chapterId;
    @SerializedName("green_percentage")
    @Expose
    private Integer greenPercentage;
    @SerializedName("amber_percentage")
    @Expose
    private Integer amberPercentage;
    @SerializedName("chapter_position")
    @Expose
    private Integer chapterPosition;
    @SerializedName("last_edited_time")
    @Expose
    private String lastEditedTime;
    @SerializedName("recommendations")
    @Expose
    private String recommendations;
    @SerializedName("chapter_description")
    @Expose
    private String chapterDescription;

    public Integer getAuditChapterId() {
        return auditChapterId;
    }

    public void setAuditChapterId(Integer auditChapterId) {
        this.auditChapterId = auditChapterId;
    }

    public String getCriticalImprovementAreas() {
        return criticalImprovementAreas;
    }

    public void setCriticalImprovementAreas(String criticalImprovementAreas) {
        this.criticalImprovementAreas = criticalImprovementAreas;
    }

    public Integer getAuditId() {
        return auditId;
    }

    public void setAuditId(Integer auditId) {
        this.auditId = auditId;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public String getGraphColor() {
        return graphColor;
    }

    public void setGraphColor(String graphColor) {
        this.graphColor = graphColor;
    }

    public Integer getCompliancePercentage() {
        return compliancePercentage;
    }

    public void setCompliancePercentage(Integer compliancePercentage) {
        this.compliancePercentage = compliancePercentage;
    }

    public List<Acc> getAccs() {
        return accs;
    }

    public void setAccs(List<Acc> accs) {
        this.accs = accs;
    }

    public String getOtherImprovementAreas() {
        return otherImprovementAreas;
    }

    public void setOtherImprovementAreas(String otherImprovementAreas) {
        this.otherImprovementAreas = otherImprovementAreas;
    }

    public Integer getRedPercentage() {
        return redPercentage;
    }

    public void setRedPercentage(Integer redPercentage) {
        this.redPercentage = redPercentage;
    }

    public String getStrengthArea() {
        return strengthArea;
    }

    public void setStrengthArea(String strengthArea) {
        this.strengthArea = strengthArea;
    }

    public Integer getChapterId() {
        return chapterId;
    }

    public void setChapterId(Integer chapterId) {
        this.chapterId = chapterId;
    }

    public Integer getGreenPercentage() {
        return greenPercentage;
    }

    public void setGreenPercentage(Integer greenPercentage) {
        this.greenPercentage = greenPercentage;
    }

    public Integer getAmberPercentage() {
        return amberPercentage;
    }

    public void setAmberPercentage(Integer amberPercentage) {
        this.amberPercentage = amberPercentage;
    }

    public Integer getChapterPosition() {
        return chapterPosition;
    }

    public void setChapterPosition(Integer chapterPosition) {
        this.chapterPosition = chapterPosition;
    }

    public String getLastEditedTime() {
        return lastEditedTime;
    }

    public void setLastEditedTime(String lastEditedTime) {
        this.lastEditedTime = lastEditedTime;
    }

    public String getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(String recommendations) {
        this.recommendations = recommendations;
    }

    public String getChapterDescription() {
        return chapterDescription;
    }

    public void setChapterDescription(String chapterDescription) {
        this.chapterDescription = chapterDescription;
    }

}
