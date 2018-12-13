package com.mspo.comspo.data.remote.model.responses.audit_sheet;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuditSheetResponse implements Serializable{

    @SerializedName("chapters")
    @Expose
    private List<Chapter> chapters = null;

    public List<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }

}
