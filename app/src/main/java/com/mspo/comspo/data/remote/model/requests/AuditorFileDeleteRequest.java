package com.mspo.comspo.data.remote.model.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuditorFileDeleteRequest {

    @SerializedName("auditor_id")
    @Expose
    private Integer auditor_id;

    public AuditorFileDeleteRequest(Integer auditor_id) {
        this.auditor_id = auditor_id;
    }

    public Integer getFarmId() {
        return auditor_id;
    }

    public void setFarmId(Integer auditor_id) {
        this.auditor_id = auditor_id;
    }
}
