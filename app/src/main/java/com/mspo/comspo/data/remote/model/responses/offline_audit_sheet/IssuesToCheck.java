package com.mspo.comspo.data.remote.model.responses.offline_audit_sheet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class IssuesToCheck extends RealmObject {

    @SerializedName("position")
    @Expose
    private Integer position;
    @SerializedName("issues_to_check")
    @Expose
    private String issuesToCheck;

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getIssuesToCheck() {
        return issuesToCheck;
    }

    public void setIssuesToCheck(String issuesToCheck) {
        this.issuesToCheck = issuesToCheck;
    }

}