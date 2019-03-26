package com.mspo.comspo.data.remote.model.responses.profile_view.auditor;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AuditorProfileViewResponse implements Serializable {

    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("profile_pic")
    @Expose
    private Object profilePic;
    @SerializedName("newly_assigned_count")
    @Expose
    private Integer newlyAssignedCount;
    @SerializedName("approved_percentage")
    @Expose
    private Double approvedPercentage;
    @SerializedName("pending_count")
    @Expose
    private Integer pendingCount;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("ongoing_count")
    @Expose
    private Integer ongoingCount;
    @SerializedName("total_audits")
    @Expose
    private Integer totalAudits;
    @SerializedName("approved_count")
    @Expose
    private Integer approvedCount;
    @SerializedName("cb_name")
    @Expose
    private String cbName;
    @SerializedName("user_type")
    @Expose
    private String userType;
    @SerializedName("access_token")
    @Expose
    private String accessToken;
    @SerializedName("year_of_registration")
    @Expose
    private Integer yearOfRegistration;
    @SerializedName("pending_percentage")
    @Expose
    private Double pendingPercentage;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("ongoing_percentage")
    @Expose
    private Double ongoingPercentage;
    @SerializedName("education")
    @Expose
    private String education;
    @SerializedName("training_year")
    @Expose
    private String trainingYear;
    @SerializedName("last_accessed_time")
    @Expose
    private String lastAccessedTime;
    @SerializedName("audits_this_year")
    @Expose
    private Integer auditsThisYear;
    @SerializedName("is_blocked")
    @Expose
    private Boolean isBlocked;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("is_approved")
    @Expose
    private Boolean isApproved;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("completed_count")
    @Expose
    private Integer completedCount;
    @SerializedName("experience")
    @Expose
    private String experience;
    @SerializedName("auditor_audit_status")
    @Expose
    private Boolean auditorAuditStatus;
    @SerializedName("newly_assigned_percentage")
    @Expose
    private Double newlyAssignedPercentage;
    @SerializedName("training_details")
    @Expose
    private String trainingDetails;
    @SerializedName("completed_percentage")
    @Expose
    private Double completedPercentage;
    @SerializedName("landline")
    @Expose
    private String landline;
    @SerializedName("last_login_date")
    @Expose
    private String lastLoginDate;
    @SerializedName("not_completed_audits")
    @Expose
    private Integer notCompletedAudits;
    @SerializedName("auditor_id")
    @Expose
    private Integer auditorId;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("language_chosen")
    @Expose
    private String languageChosen;

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

    public Integer getNewlyAssignedCount() {
        return newlyAssignedCount;
    }

    public void setNewlyAssignedCount(Integer newlyAssignedCount) {
        this.newlyAssignedCount = newlyAssignedCount;
    }

    public Double getApprovedPercentage() {
        return approvedPercentage;
    }

    public void setApprovedPercentage(Double approvedPercentage) {
        this.approvedPercentage = approvedPercentage;
    }

    public Integer getPendingCount() {
        return pendingCount;
    }

    public void setPendingCount(Integer pendingCount) {
        this.pendingCount = pendingCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOngoingCount() {
        return ongoingCount;
    }

    public void setOngoingCount(Integer ongoingCount) {
        this.ongoingCount = ongoingCount;
    }

    public Integer getTotalAudits() {
        return totalAudits;
    }

    public void setTotalAudits(Integer totalAudits) {
        this.totalAudits = totalAudits;
    }

    public Integer getApprovedCount() {
        return approvedCount;
    }

    public void setApprovedCount(Integer approvedCount) {
        this.approvedCount = approvedCount;
    }

    public String getCbName() {
        return cbName;
    }

    public void setCbName(String cbName) {
        this.cbName = cbName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Integer getYearOfRegistration() {
        return yearOfRegistration;
    }

    public void setYearOfRegistration(Integer yearOfRegistration) {
        this.yearOfRegistration = yearOfRegistration;
    }

    public Double getPendingPercentage() {
        return pendingPercentage;
    }

    public void setPendingPercentage(Double pendingPercentage) {
        this.pendingPercentage = pendingPercentage;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getOngoingPercentage() {
        return ongoingPercentage;
    }

    public void setOngoingPercentage(Double ongoingPercentage) {
        this.ongoingPercentage = ongoingPercentage;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getTrainingYear() {
        return trainingYear;
    }

    public void setTrainingYear(String trainingYear) {
        this.trainingYear = trainingYear;
    }

    public String getLastAccessedTime() {
        return lastAccessedTime;
    }

    public void setLastAccessedTime(String lastAccessedTime) {
        this.lastAccessedTime = lastAccessedTime;
    }

    public Integer getAuditsThisYear() {
        return auditsThisYear;
    }

    public void setAuditsThisYear(Integer auditsThisYear) {
        this.auditsThisYear = auditsThisYear;
    }

    public Boolean getIsBlocked() {
        return isBlocked;
    }

    public void setIsBlocked(Boolean isBlocked) {
        this.isBlocked = isBlocked;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Boolean getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(Boolean isApproved) {
        this.isApproved = isApproved;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getCompletedCount() {
        return completedCount;
    }

    public void setCompletedCount(Integer completedCount) {
        this.completedCount = completedCount;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public Boolean getAuditorAuditStatus() {
        return auditorAuditStatus;
    }

    public void setAuditorAuditStatus(Boolean auditorAuditStatus) {
        this.auditorAuditStatus = auditorAuditStatus;
    }

    public Double getNewlyAssignedPercentage() {
        return newlyAssignedPercentage;
    }

    public void setNewlyAssignedPercentage(Double newlyAssignedPercentage) {
        this.newlyAssignedPercentage = newlyAssignedPercentage;
    }

    public String getTrainingDetails() {
        return trainingDetails;
    }

    public void setTrainingDetails(String trainingDetails) {
        this.trainingDetails = trainingDetails;
    }

    public Double getCompletedPercentage() {
        return completedPercentage;
    }

    public void setCompletedPercentage(Double completedPercentage) {
        this.completedPercentage = completedPercentage;
    }

    public String getLandline() {
        return landline;
    }

    public void setLandline(String landline) {
        this.landline = landline;
    }

    public String getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(String lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Integer getNotCompletedAudits() {
        return notCompletedAudits;
    }

    public void setNotCompletedAudits(Integer notCompletedAudits) {
        this.notCompletedAudits = notCompletedAudits;
    }

    public Integer getAuditorId() {
        return auditorId;
    }

    public void setAuditorId(Integer auditorId) {
        this.auditorId = auditorId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLanguageChosen() {
        return languageChosen;
    }

    public void setLanguageChosen(String languageChosen) {
        this.languageChosen = languageChosen;
    }

}
