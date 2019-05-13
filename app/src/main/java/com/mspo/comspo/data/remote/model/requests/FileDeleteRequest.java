package com.mspo.comspo.data.remote.model.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FileDeleteRequest {

    @SerializedName("farm_id")
    @Expose
    private Integer farmId;

    public FileDeleteRequest(Integer farmId) {
        this.farmId = farmId;
    }

    public Integer getFarmId() {
        return farmId;
    }

    public void setFarmId(Integer farmId) {
        this.farmId = farmId;
    }
}
