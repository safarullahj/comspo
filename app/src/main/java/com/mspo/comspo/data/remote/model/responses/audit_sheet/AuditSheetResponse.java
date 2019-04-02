package com.mspo.comspo.data.remote.model.responses.audit_sheet;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class AuditSheetResponse implements Serializable{

    @SerializedName("chapters")
    @Expose
    private List<Chapter> chapters;
    /*@PrimaryKey
    @Expose(serialize = true, deserialize = false)
    private Integer auditId;
    @Expose(serialize = true, deserialize = false)
    private String userType;*/

    public List<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }

    /*public Integer getAuditId() {
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
    }*/


}
