package com.mspo.comspo.data.remote.model.responses.profile_view;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class InternalAuditStatus implements Serializable {

    @SerializedName("pending_count")
    @Expose
    private Integer pendingCount;
    @SerializedName("newly_assigned_count")
    @Expose
    private Integer newlyAssignedCount;
    @SerializedName("approved_count")
    @Expose
    private Integer approvedCount;
    @SerializedName("completed_count")
    @Expose
    private Integer completedCount;
    @SerializedName("ongoing_count")
    @Expose
    private Integer ongoingCount;

    public Integer getPendingCount() {
        return pendingCount;
    }

    public void setPendingCount(Integer pendingCount) {
        this.pendingCount = pendingCount;
    }

    public Integer getNewlyAssignedCount() {
        return newlyAssignedCount;
    }

    public void setNewlyAssignedCount(Integer newlyAssignedCount) {
        this.newlyAssignedCount = newlyAssignedCount;
    }

    public Integer getApprovedCount() {
        return approvedCount;
    }

    public void setApprovedCount(Integer approvedCount) {
        this.approvedCount = approvedCount;
    }

    public Integer getCompletedCount() {
        return completedCount;
    }

    public void setCompletedCount(Integer completedCount) {
        this.completedCount = completedCount;
    }

    public Integer getOngoingCount() {
        return ongoingCount;
    }

    public void setOngoingCount(Integer ongoingCount) {
        this.ongoingCount = ongoingCount;
    }

}
