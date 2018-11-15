package com.mspo.comspo.data.remote.model.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("profile_pic")
    @Expose
    private String profilePic;
    @SerializedName("user_type")
    @Expose
    private String userType;
    @SerializedName("is_blocked")
    @Expose
    private Boolean isBlocked;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("last_accessed_time")
    @Expose
    private String lastAccessedTime;
    @SerializedName("year_of_registration")
    @Expose
    private Integer yearOfRegistration;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("language_chosen")
    @Expose
    private String languageChosen;
    @SerializedName("access_token")
    @Expose
    private String accessToken;
    @SerializedName("last_login_date")
    @Expose
    private String lastLoginDate;
    @SerializedName("email")
    @Expose
    private String email;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Boolean getIsBlocked() {
        return isBlocked;
    }

    public void setIsBlocked(Boolean isBlocked) {
        this.isBlocked = isBlocked;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLastAccessedTime() {
        return lastAccessedTime;
    }

    public void setLastAccessedTime(String lastAccessedTime) {
        this.lastAccessedTime = lastAccessedTime;
    }

    public Integer getYearOfRegistration() {
        return yearOfRegistration;
    }

    public void setYearOfRegistration(Integer yearOfRegistration) {
        this.yearOfRegistration = yearOfRegistration;
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

    public String getLanguageChosen() {
        return languageChosen;
    }

    public void setLanguageChosen(String languageChosen) {
        this.languageChosen = languageChosen;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(String lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
