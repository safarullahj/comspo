package com.mspo.comspo.data.remote.model.responses.audit_sheet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class EvidenceToCheck implements Serializable{

    @SerializedName("position")
    @Expose
    private Integer position;
    @SerializedName("evidence_to_check")
    @Expose
    private String evidenceToCheck;

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getEvidenceToCheck() {
        return evidenceToCheck;
    }

    public void setEvidenceToCheck(String evidenceToCheck) {
        this.evidenceToCheck = evidenceToCheck;
    }

}
