package com.mspo.comspo.data.remote.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;

public class PrefManager {

    private static final String PREF_NAME = "MspoShared";
    public static final String LOGIN_SHARED = "LoginShared";
    private static final String LOGIN_CHECK = "LoginCheck";
    private static final String LOGIN_ACCESSTOCKEN = "LoginAccessToken";
    private static final String LOGIN_USERID = "LoginUserId";
    private static final String LOGIN_FARMID = "LoginFarmId";
    private static final String LOGIN_USERNAME = "LoginUsername";
    private static final String LOGIN_USEREMAIL = "LoginEmail";
    private static final String LOGIN_USERPIC = "LoginUserpic";
    private static final String LOGIN_USER_TYPE = "LoginUserTYpe";
    private static final String LOGIN_USER_LANGUAGE = "LoginUserLLanguage";
    private static final String USER_LOCATION_LAT = "userLocationLat";
    private static final String USER_LOCATION_LON = "userLocationLon";

    private Context context;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    public PrefManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();

        SharedPreferences loginpref = context.getSharedPreferences(LOGIN_SHARED, Context.MODE_PRIVATE);
        SharedPreferences.Editor logineditor = loginpref.edit();
    }

    public static boolean clearLoginShared(Context ctx) {
        SharedPreferences sharedpreferences = ctx.getSharedPreferences(LOGIN_SHARED,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear().apply();
        return true;
    }

    public static boolean saveLoginToken(Context ctx, String userType, Integer userId, String accessToken,Integer farmId, String userName,String userEmail,String profilePic) {
        SharedPreferences sharedpreferences = ctx.getSharedPreferences(LOGIN_SHARED,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(LOGIN_USER_TYPE, userType)
                .putInt(LOGIN_USERID, userId)
                .putString(LOGIN_ACCESSTOCKEN, accessToken)
                .putBoolean(LOGIN_CHECK, true)
                .putInt(LOGIN_FARMID, farmId)
                .putString(LOGIN_USERNAME, userName)
                .putString(LOGIN_USEREMAIL, userEmail)
                .putString(LOGIN_USERPIC, profilePic)
                .apply();
        return true;
    }

    public static boolean getLoginStatus(Context ctx) {
        SharedPreferences sharedpreferences = ctx.getSharedPreferences(LOGIN_SHARED,
                Context.MODE_PRIVATE);
        return sharedpreferences.getBoolean(LOGIN_CHECK,false);
    }

    public static String getUserType(Context ctx) {
        SharedPreferences sharedpreferences = ctx.getSharedPreferences(LOGIN_SHARED,
                Context.MODE_PRIVATE);
        return sharedpreferences.getString(LOGIN_USER_TYPE, "");
    }

    public static Integer getUserId(Context ctx) {
        SharedPreferences sharedpreferences = ctx.getSharedPreferences(LOGIN_SHARED,
                Context.MODE_PRIVATE);
        return sharedpreferences.getInt(LOGIN_USERID, 0);
    }

    public static String getUserName(Context ctx) {
        SharedPreferences sharedpreferences = ctx.getSharedPreferences(LOGIN_SHARED,
                Context.MODE_PRIVATE);
        return sharedpreferences.getString(LOGIN_USERNAME, "");
    }

    public static String getUserEmail(Context ctx) {
        SharedPreferences sharedpreferences = ctx.getSharedPreferences(LOGIN_SHARED,
                Context.MODE_PRIVATE);
        return sharedpreferences.getString(LOGIN_USEREMAIL, "");
    }

    public static String getUserPic(Context ctx) {
        SharedPreferences sharedpreferences = ctx.getSharedPreferences(LOGIN_SHARED,
                Context.MODE_PRIVATE);
        return sharedpreferences.getString(LOGIN_USERPIC, "");
    }

    public static Integer getFarmId(Context ctx) {
        SharedPreferences sharedpreferences = ctx.getSharedPreferences(LOGIN_SHARED,
                Context.MODE_PRIVATE);
        return sharedpreferences.getInt(LOGIN_FARMID, 0);
    }

    public static String getAccessToken(Context ctx) {
        SharedPreferences sharedpreferences = ctx.getSharedPreferences(LOGIN_SHARED,
                Context.MODE_PRIVATE);
        return sharedpreferences.getString(LOGIN_ACCESSTOCKEN, "");
    }


    public static void setUserLanguage(Context mContext, String mLanguage) {
        SharedPreferences.Editor mEditor = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit();
        mEditor.putString(LOGIN_USER_LANGUAGE, mLanguage);
        mEditor.apply();
    }

    public static String getUserLanguage(Context ctx) {
        SharedPreferences sharedpreferences = ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedpreferences.getString(LOGIN_USER_LANGUAGE, "malay");
    }

    public String getLocationLat() {
        return pref.getString(USER_LOCATION_LAT, "");
    }

    public void setLocationLat(String lat) {
        editor.putString(USER_LOCATION_LAT, lat);
        editor.commit();
    }

    public String getLocationLon() {
        return pref.getString(USER_LOCATION_LON, "");
    }

    public void setLocationLon(String lon) {
        editor.putString(USER_LOCATION_LON, lon);
        editor.commit();
    }
}
