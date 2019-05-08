package com.mspo.comspo.data.remote.model.responses.upload;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FileDatum {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("file")
    @Expose
    private String file;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

}