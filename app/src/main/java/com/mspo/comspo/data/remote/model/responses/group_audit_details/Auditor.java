package com.mspo.comspo.data.remote.model.responses.group_audit_details;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Auditor {

    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("profile_pic")
    @Expose
    private Object profilePic;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("access_token")
    @Expose
    private String accessToken;
    @SerializedName("auditor_id")
    @Expose
    private Integer auditorId;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Object getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(Object profilePic) {
        this.profilePic = profilePic;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Integer getAuditorId() {
        return auditorId;
    }

    public void setAuditorId(Integer auditorId) {
        this.auditorId = auditorId;
    }

}
