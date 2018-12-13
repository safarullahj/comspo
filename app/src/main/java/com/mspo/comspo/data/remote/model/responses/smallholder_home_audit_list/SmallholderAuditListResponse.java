package com.mspo.comspo.data.remote.model.responses.smallholder_home_audit_list;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SmallholderAuditListResponse {

    @SerializedName("audits")
    @Expose
    private List<Audit> audits = null;
    @SerializedName("meta")
    @Expose
    private Meta meta;

    public List<Audit> getAudits() {
        return audits;
    }

    public void setAudits(List<Audit> audits) {
        this.audits = audits;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

}
