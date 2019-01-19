package com.mspo.comspo.data.remote.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class PrefManagerFilter {

    private static final String PREF_NAME = "FILTER_SHARED";
    public static final String FILTER_STATUS = "status";
    private static final String FILTER_YEAR = "year";
    private static final String FILTER_KEY = "key";

    private Context context;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    public PrefManagerFilter(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public  boolean clearFilter() {
        editor.clear().apply();
        return true;
    }

    public void setFilterStatus(String status) {
        editor.putString(FILTER_STATUS, status);
        editor.commit();
    }

    public String getFilterStatus() {
        return pref.getString(FILTER_STATUS, "");
    }

    public void setFilterYear(int year) {
        editor.putInt(FILTER_YEAR, year);
        editor.commit();
    }

    public int getFilterYear() {
        return pref.getInt(FILTER_YEAR, 0);
    }

    public void setFilterKey(String key) {
        editor.putString(FILTER_KEY, key);
        editor.commit();
    }
    public String getFilterKey() {
        return pref.getString(FILTER_KEY, "");
    }
}
