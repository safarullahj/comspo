package com.mspo.comspo.data.remote.model.responses.offline_audit_sheet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class SuggestionElement extends RealmObject {

    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("suggestion_element_id")
    @Expose
    private Integer suggestionElementId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("suggestion_url")
    @Expose
    private String suggestionUrl;
    @SerializedName("content_type")
    @Expose
    private String contentType;
    @SerializedName("position")
    @Expose
    private Integer position;
    @SerializedName("indicator_suggestion_id")
    @Expose
    private Integer indicatorSuggestionId;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSuggestionElementId() {
        return suggestionElementId;
    }

    public void setSuggestionElementId(Integer suggestionElementId) {
        this.suggestionElementId = suggestionElementId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSuggestionUrl() {
        return suggestionUrl;
    }

    public void setSuggestionUrl(String suggestionUrl) {
        this.suggestionUrl = suggestionUrl;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Integer getIndicatorSuggestionId() {
        return indicatorSuggestionId;
    }

    public void setIndicatorSuggestionId(Integer indicatorSuggestionId) {
        this.indicatorSuggestionId = indicatorSuggestionId;
    }

}
