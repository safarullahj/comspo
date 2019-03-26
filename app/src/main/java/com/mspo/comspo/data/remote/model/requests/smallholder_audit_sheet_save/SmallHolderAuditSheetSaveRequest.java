package com.mspo.comspo.data.remote.model.requests.smallholder_audit_sheet_save;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mspo.comspo.data.remote.model.responses.audit_sheet.Chapter;

public class SmallHolderAuditSheetSaveRequest {

    @SerializedName("farm_id")
    @Expose
    private Integer farmId;
    @SerializedName("auditor_id")
    @Expose
    private Integer auditorId;
    @SerializedName("sub_audit_id")
    @Expose
    private String subAuditId;
    @SerializedName("is_submit")
    @Expose
    private String isSubmit;
    @SerializedName("audit_details")
    @Expose
    private List<AuditDetail> auditDetails = null;
    @SerializedName("chapters")
    @Expose
    private List<Chapter> chapters = null;

    public SmallHolderAuditSheetSaveRequest(Integer farmId,Integer auditorId, String subAuditId,String isSubmit, List<Chapter> chapters, List<AuditDetail> auditDetailList) {
        this.farmId = farmId;
        this.auditorId = auditorId;
        this.subAuditId = subAuditId;
        this.isSubmit = isSubmit;
        this.chapters = chapters;
        this.auditDetails = auditDetailList;
    }

    public Integer getFarmId() {
        return farmId;
    }

    public void setFarmId(Integer farmId) {
        this.farmId = farmId;
    }

    public String getIsSubmit() {
        return isSubmit;
    }

    public void setIsSubmit(String isSubmit) {
        this.isSubmit = isSubmit;
    }

    public List<AuditDetail> getAuditDetails() {
        return auditDetails;
    }

    public void setAuditDetails(List<AuditDetail> auditDetails) {
        this.auditDetails = auditDetails;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }

    public Integer getAuditorId() {
        return auditorId;
    }

    public void setAuditorId(Integer auditorId) {
        this.auditorId = auditorId;
    }

    public String getSubAuditId() {
        return subAuditId;
    }

    public void setSubAuditId(String subAuditId) {
        this.subAuditId = subAuditId;
    }

}
