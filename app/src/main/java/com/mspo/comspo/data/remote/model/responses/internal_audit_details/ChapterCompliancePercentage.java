package com.mspo.comspo.data.remote.model.responses.internal_audit_details;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ChapterCompliancePercentage implements Serializable {

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
