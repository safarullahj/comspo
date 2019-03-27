package com.mspo.comspo.data.remote.model.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuditAcceptRequest {

    @SerializedName("auditor_id")
    @Expose
    private Integer auditorId;
    @SerializedName("status")
    @Expose
    private String status;

    public AuditAcceptRequest(Integer auditorId, String status) {
        this.auditorId = auditorId;
        this.status = status;
    }

    public Integer getAuditorId() {
        return auditorId;
    }

    public void setAuditorId(Integer auditorId) {
        this.auditorId = auditorId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
