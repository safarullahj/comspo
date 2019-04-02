package com.mspo.comspo.data.remote.model.responses.offline_audit_sheet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Suggestion extends RealmObject {

    @SerializedName("indicator_id")
    @Expose
    private Integer indicatorId;
    @SerializedName("compliance_type")
    @Expose
    private String complianceType;
    @SerializedName("indicator_suggestion_id")
    @Expose
    private Integer indicatorSuggestionId;
    @SerializedName("suggestion_elements")
    @Expose
    private RealmList<SuggestionElement> suggestionElements = null;

    public Integer getIndicatorId() {
        return indicatorId;
    }

    public void setIndicatorId(Integer indicatorId) {
        this.indicatorId = indicatorId;
    }

    public String getComplianceType() {
        return complianceType;
    }

    public void setComplianceType(String complianceType) {
        this.complianceType = complianceType;
    }

    public Integer getIndicatorSuggestionId() {
        return indicatorSuggestionId;
    }

    public void setIndicatorSuggestionId(Integer indicatorSuggestionId) {
        this.indicatorSuggestionId = indicatorSuggestionId;
    }

    public RealmList<SuggestionElement> getSuggestionElements() {
        return suggestionElements;
    }

    public void setSuggestionElements(RealmList<SuggestionElement> suggestionElements) {
        this.suggestionElements = suggestionElements;
    }

}