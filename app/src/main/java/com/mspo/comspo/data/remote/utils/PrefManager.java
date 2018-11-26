package com.mspo.comspo.data.remote.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {

    private static final String PREF_NAME = "MSPO_SHARED";
    public static final String LOGIN_SHARED = "LoginShared";
    private static final String LOGIN_CHECK = "LoginCheck";
    private static final String LOGIN_ACCESSTOCKEN = "LoginAccessToken";
    private static final String LOGIN_USERID = "LoginUserId";
    private static final String LOGIN_USER_TYPE = "LoginUserTYpe";

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

    public static boolean saveLoginToken(Context ctx, String userType, Integer userId, String accessToken) {
        SharedPreferences sharedpreferences = ctx.getSharedPreferences(LOGIN_SHARED,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(LOGIN_USER_TYPE, userType)
                .putInt(LOGIN_USERID, userId)
                .putString(LOGIN_ACCESSTOCKEN, accessToken)
                .putBoolean(LOGIN_CHECK, true)
                .apply();
        return true;
    }

    public static boolean getLoginStatus(Context ctx) {
        SharedPreferences sharedpreferences = ctx.getSharedPreferences(LOGIN_SHARED,
                Context.MODE_PRIVATE);
        return sharedpreferences.getBoolean(LOGIN_CHECK, false);
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

    public static String getAccessToken(Context ctx) {
        SharedPreferences sharedpreferences = ctx.getSharedPreferences(LOGIN_SHARED,
                Context.MODE_PRIVATE);
        return sharedpreferences.getString(LOGIN_ACCESSTOCKEN, "");
    }

}
