package com.mspo.comspo.data.remote.model.responses.audit_sheet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class File implements Serializable {

    @SerializedName("aic_file_id")
    @Expose
    private Integer aicFileId;
    @SerializedName("aic_file")
    @Expose
    private String aicFile;
    @SerializedName("file_name")
    @Expose
    private String fileName;

    public File(Integer aicFileId, String aicFile, String fileName) {
        this.aicFileId = aicFileId;
        this.aicFile = aicFile;
        this.fileName = fileName;
    }

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
