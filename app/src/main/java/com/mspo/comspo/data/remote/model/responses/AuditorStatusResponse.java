package com.mspo.comspo.data.remote.model.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuditorStatusResponse {

    @SerializedName("success")
    @Expose
    private Boolean success;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

}