package com.mspo.comspo.data.remote.model.responses.offline_audit_sheet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class OfflineAuditSheetResponse extends RealmObject {

    @SerializedName("chapters")
    @Expose
    private RealmList<Chapter> chapters;
    @PrimaryKey
    private Integer auditId;
    private String userType;
    private String year;

    public RealmList<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(RealmList<Chapter> chapters) {
        this.chapters = chapters;
    }

    public Integer getAuditId() {
        return auditId;
    }

    public void setAuditId(Integer auditId) {
        this.auditId = auditId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getYear() {
        return year;
    }

    public void setYeare(String year) {
        this.year = year;
    }


}
