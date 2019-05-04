package com.mspo.comspo.data.remote.model.responses.offline_audit_sheet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class File extends RealmObject {

    @SerializedName("aic_file_id")
    @Expose
    private Integer aicFileId;
    @SerializedName("aic_file")
    @Expose
    private String aicFile;
    @SerializedName("file_name")
    @Expose
    private String fileName;

    public Integer getAicFileId() {
        return aicFileId;
    }

    public void setAicFileId(Integer aicFileId) {
        this.aicFileId = aicFileId;
    }

    public String getAicFile() {
        return aicFile;
    }

    public void setAicFile(String aicFile) {
        this.aicFile = aicFile;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
