package com.mspo.comspo.data.remote.model.responses.group_audit_details;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChapterCompliancePercentage {

    @SerializedName("compliance_percentage")
    @Expose
    private Double compliancePercentage;
    @SerializedName("graph_color")
    @Expose
    private String graphColor;

    public Double getCompliancePercentage() {
        return compliancePercentage;
    }

    public void setCompliancePercentage(Double compliancePercentage) {
        this.compliancePercentage = compliancePercentage;
    }

    public String getGraphColor() {
        return graphColor;
    }

    public void setGraphColor(String graphColor) {
        this.graphColor = graphColor;
    }

}
