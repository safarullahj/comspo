package com.mspo.comspo.data.remote.model.responses.internal_audit_details;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChapterCompliancePercentage {

    @SerializedName("compliance_percentage")
    @Expose
    private Integer compliancePercentage;
    @SerializedName("graph_color")
    @Expose
    private String graphColor;

    public Integer getCompliancePercentage() {
        return compliancePercentage;
    }

    public void setCompliancePercentage(Integer compliancePercentage) {
        this.compliancePercentage = compliancePercentage;
    }

    public String getGraphColor() {
        return graphColor;
    }

    public void setGraphColor(String graphColor) {
        this.graphColor = graphColor;
    }

}
