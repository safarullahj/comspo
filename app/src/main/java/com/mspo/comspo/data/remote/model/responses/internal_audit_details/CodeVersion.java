package com.mspo.comspo.data.remote.model.responses.internal_audit_details;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CodeVersion {

    @SerializedName("version_number")
    @Expose
    private Integer versionNumber;

    public Integer getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(Integer versionNumber) {
        this.versionNumber = versionNumber;
    }

}