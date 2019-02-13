package com.mspo.comspo.data.remote.model.responses.auditor_home_audit_list;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Meta {

    @SerializedName("total_count")
    @Expose
    private Integer totalCount;
    @SerializedName("limit")
    @Expose
    private Integer limit;
    @SerializedName("next_offset")
    @Expose
    private Integer nextOffset;
    @SerializedName("current_offset")
    @Expose
    private Integer currentOffset;
    @SerializedName("previous_offset")
    @Expose
    private Object previousOffset;

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getNextOffset() {
        return nextOffset;
    }

    public void setNextOffset(Integer nextOffset) {
        this.nextOffset = nextOffset;
    }

    public Integer getCurrentOffset() {
        return currentOffset;
    }

    public void setCurrentOffset(Integer currentOffset) {
        this.currentOffset = currentOffset;
    }

    public Object getPreviousOffset() {
        return previousOffset;
    }

    public void setPreviousOffset(Object previousOffset) {
        this.previousOffset = previousOffset;
    }

}
