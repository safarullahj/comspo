package com.mspo.comspo.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.Log;

import com.mspo.comspo.data.remote.utils.PrefManager;

import java.util.Locale;

public class LocaleManager {
    public static Context setLocale(Context c) {
        return updateResources(c, getLanguage(c));
    }

    public static Context setNewLocale(Context c, String language) {
        Log.e("LOCL", "setNewLocale: " + language);
        persistLanguage(c, language);
        return updateResources(c, language);
    }

    public static String getLanguage(Context c) {
        if (PrefManager.getUserLanguage(c).equals("malay"))
            return "ms";
        else
            return "en";
    }

    private static void persistLanguage(Context c, String language) {
        Log.e("LOCL", "updateResources: " + language);
        String lang;
        if (language.equals("ms")) {
            lang = "malay";
        } else {
            lang = "english";
        }
        PrefManager.setUserLanguage(c, lang);
    }

    private static Context updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());
        if (Build.VERSION.SDK_INT >= 17) {
            config.setLocale(locale);
            context = context.createConfigurationContext(config);
        } else {
            config.locale = locale;
            res.updateConfiguration(config, res.getDisplayMetrics());
        }
        Log.e("LOCL", "updateResources: " + language);

        return context;
    }

    public static Locale getLocale(Resources res) {
        Configuration config = res.getConfiguration();
        return Build.VERSION.SDK_INT >= 24 ? config.getLocales().get(0) : config.locale;
    }
}
