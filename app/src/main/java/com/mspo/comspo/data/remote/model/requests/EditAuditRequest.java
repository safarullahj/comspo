package com.mspo.comspo.data.remote.model.requests;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EditAuditRequest {
    public EditAuditRequest(String startDate, String endDate, Integer farmerId) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.farmerId = farmerId;
    }

    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("auditor_ids")
    @Expose
    private List<Object> auditorIds = null;
    @SerializedName("farmer_id")
    @Expose
    private Integer farmerId;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public List<Object> getAuditorIds() {
        return auditorIds;
    }

    public void setAuditorIds(List<Object> auditorIds) {
        this.auditorIds = auditorIds;
    }

    public Integer getFarmerId() {
        return farmerId;
    }

    public void setFarmerId(Integer farmerId) {
        this.farmerId = farmerId;
    }

}
