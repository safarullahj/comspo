package com.mspo.comspo.data.remote.model.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChangeLanguageRequest {

    @SerializedName("language_chosen")
    @Expose
    private String languageChosen;

    public ChangeLanguageRequest(String languageChosen) {
        this.languageChosen = languageChosen;
    }

    public String getLanguageChosen() {
        return languageChosen;
    }

    public void setLanguageChosen(String languageChosen) {
        this.languageChosen = languageChosen;
    }
}
