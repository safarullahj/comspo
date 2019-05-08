package com.mspo.comspo.data.remote.webservice;

public class ApiConstants {
    //public static final String BASE_URL = "http://34.208.48.82:3000/admin/";
    public static final String BASE_URL = "http://ec2-13-126-193-48.ap-south-1.compute.amazonaws.com/";
    public static final String ENDPOINT_LOGIN = "usermanager/login/";
    public static final String ENDPOINT_SIGNUP = "usermanager/farm/signup/";
    public static final String ENDPOINT_FORGOT_PASSWORD = "usermanager/user/password/forgot/";
    public static final String ENDPOINT_LOGOUT = "usermanager/logout/";
    public static final String ENDPOINT_USERNAME_AVAILABILITY = "usermanager/user/username/availability/";
    public static final String ENDPOINT_LANGUAGE = "usermanager/user/language/update/";
    public static final String ENDPOINT_PASSWORD = "usermanager/user/password/update/";
    public static final String ENDPOINT_PROFILE_VIEW = "usermanager/farm/{farmId}/get/";
    public static final String ENDPOINT_EDIT_PROFILE_VIEW = "usermanager/farm/{farmId}/update/";
    public static final String ENDPOINT_AUDITOR_PROFILE_VIEW = "usermanager/auditor/{auditId}/get/";
    public static final String ENDPOINT_EDIT_AUDITOR_PROFILE_VIEW = "usermanager/auditor/{auditorId}/update/";
    public static final String ENDPOINT_SMALLHOLDER_AUDIT_LIST = "scoringtool/audit/farm/{farmId}/get/";
    public static final String ENDPOINT_NEW_INTERNAL_AUDIT = "scoringtool/audit/internal/create/";
    public static final String ENDPOINT_INDIVIDUAL_AUDIT_DETAILS = "scoringtool/audit/single/{auditId}/get/";
    public static final String ENDPOINT_GROUP_AUDIT_DETAILS = "scoringtool/audit/group/{auditId}/get/";
    public static final String ENDPOINT_GET_AUDIT_SHEET = "scoringtool/audit/sheet/{auditId}/get/";
    public static final String ENDPOINT_SAVE_SMALLHOLDER_AUDIT_SHEET = "scoringtool/audit/{auditId}/save/";
    public static final String ENDPOINT_AUDITOR_AUDIT_LIST = "scoringtool/audit/get/";
    public static final String ENDPOINT_RESULT_SHEET = "scoringtool/audit/result/{auditId}/get/";
    public static final String ENDPOINT_EDIT_AUDIT_DURATION = "scoringtool/audit/{auditId}/edit/";
    public static final String ENDPOINT_AUDITOR_AUDIT_STATUS = "scoringtool/audit/{auditId}/auditor/update/";
    public static final String ENDPOINT_FILE_UPLOAD = "scoringtool/aic/{aic_id}/{audit_id}/{sub_audit_id}/create/";
}
