package com.mspo.comspo.data.remote.model.responses.upload;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FileUploadResponse {

    @SerializedName("file_data")
    @Expose
    private List<FileDatum> fileData = null;

    public List<FileDatum> getFileData() {
        return fileData;
    }

    public void setFileData(List<FileDatum> fileData) {
        this.fileData = fileData;
    }

}