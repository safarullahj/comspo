package com.mspo.comspo.data.remote.model.responses.profile_view;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileViewResponse implements Serializable {

    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("profile_pic")
    @Expose
    private Object profilePic;
    @SerializedName("external_audit_status")
    @Expose
    private List<ExternalAuditStatus> externalAuditStatus = null;
    @SerializedName("home_address")
    @Expose
    private String homeAddress;
    @SerializedName("last_login_date")
    @Expose
    private String lastLoginDate;
    @SerializedName("audits_this_year")
    @Expose
    private Integer auditsThisYear;
    @SerializedName("grant_area")
    @Expose
    private String grantArea;
    @SerializedName("total_audits")
    @Expose
    private Integer totalAudits;
    @SerializedName("farmer_id")
    @Expose
    private Integer farmerId;
    @SerializedName("cb_name")
    @Expose
    private String cbName;
    @SerializedName("user_type")
    @Expose
    private String userType;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("year_of_registration")
    @Expose
    private Integer yearOfRegistration;
    @SerializedName("last_audit_date")
    @Expose
    private String lastAuditDate;
    @SerializedName("country_code")
    @Expose
    private String countryCode;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("licence_no")
    @Expose
    private String licenceNo;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("last_accessed_time")
    @Expose
    private String lastAccessedTime;
    @SerializedName("access_token")
    @Expose
    private String accessToken;
    @SerializedName("is_blocked")
    @Expose
    private Boolean isBlocked;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("is_approved")
    @Expose
    private Boolean isApproved;
    @SerializedName("district")
    @Expose
    private String district;
    @SerializedName("land_condition")
    @Expose
    private String landCondition;
    @SerializedName("language_chosen")
    @Expose
    private String languageChosen;
    @SerializedName("ic_no")
    @Expose
    private String icNo;
    @SerializedName("last_audit_type")
    @Expose
    private String lastAuditType;
    @SerializedName("is_group_farm")
    @Expose
    private Boolean isGroupFarm;
    @SerializedName("internal_audit_status")
    @Expose
    private List<InternalAuditStatus> internalAuditStatus = null;
    @SerializedName("sub_farm_details")
    @Expose
    private List<Object> subFarmDetails = null;
    @SerializedName("not_completed_audits")
    @Expose
    private Integer notCompletedAudits;
    @SerializedName("no_of_audits")
    @Expose
    private Integer noOfAudits;
    @SerializedName("email")
    @Expose
    private String email;

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

    public List<ExternalAuditStatus> getExternalAuditStatus() {
        return externalAuditStatus;
    }

    public void setExternalAuditStatus(List<ExternalAuditStatus> externalAuditStatus) {
        this.externalAuditStatus = externalAuditStatus;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(String lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Integer getAuditsThisYear() {
        return auditsThisYear;
    }

    public void setAuditsThisYear(Integer auditsThisYear) {
        this.auditsThisYear = auditsThisYear;
    }

    public String getGrantArea() {
        return grantArea;
    }

    public void setGrantArea(String grantArea) {
        this.grantArea = grantArea;
    }

    public Integer getTotalAudits() {
        return totalAudits;
    }

    public void setTotalAudits(Integer totalAudits) {
        this.totalAudits = totalAudits;
    }

    public Integer getFarmerId() {
        return farmerId;
    }

    public void setFarmerId(Integer farmerId) {
        this.farmerId = farmerId;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getYearOfRegistration() {
        return yearOfRegistration;
    }

    public void setYearOfRegistration(Integer yearOfRegistration) {
        this.yearOfRegistration = yearOfRegistration;
    }

    public String getLastAuditDate() {
        return lastAuditDate;
    }

    public void setLastAuditDate(String lastAuditDate) {
        this.lastAuditDate = lastAuditDate;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLicenceNo() {
        return licenceNo;
    }

    public void setLicenceNo(String licenceNo) {
        this.licenceNo = licenceNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastAccessedTime() {
        return lastAccessedTime;
    }

    public void setLastAccessedTime(String lastAccessedTime) {
        this.lastAccessedTime = lastAccessedTime;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
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

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getLandCondition() {
        return landCondition;
    }

    public void setLandCondition(String landCondition) {
        this.landCondition = landCondition;
    }

    public String getLanguageChosen() {
        return languageChosen;
    }

    public void setLanguageChosen(String languageChosen) {
        this.languageChosen = languageChosen;
    }

    public String getIcNo() {
        return icNo;
    }

    public void setIcNo(String icNo) {
        this.icNo = icNo;
    }

    public String getLastAuditType() {
        return lastAuditType;
    }

    public void setLastAuditType(String lastAuditType) {
        this.lastAuditType = lastAuditType;
    }

    public Boolean getIsGroupFarm() {
        return isGroupFarm;
    }

    public void setIsGroupFarm(Boolean isGroupFarm) {
        this.isGroupFarm = isGroupFarm;
    }

    public List<InternalAuditStatus> getInternalAuditStatus() {
        return internalAuditStatus;
    }

    public void setInternalAuditStatus(List<InternalAuditStatus> internalAuditStatus) {
        this.internalAuditStatus = internalAuditStatus;
    }

    public List<Object> getSubFarmDetails() {
        return subFarmDetails;
    }

    public void setSubFarmDetails(List<Object> subFarmDetails) {
        this.subFarmDetails = subFarmDetails;
    }

    public Integer getNotCompletedAudits() {
        return notCompletedAudits;
    }

    public void setNotCompletedAudits(Integer notCompletedAudits) {
        this.notCompletedAudits = notCompletedAudits;
    }

    public Integer getNoOfAudits() {
        return noOfAudits;
    }

    public void setNoOfAudits(Integer noOfAudits) {
        this.noOfAudits = noOfAudits;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
