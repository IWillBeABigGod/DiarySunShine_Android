package com.jun.diarysunshine.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

/**
 * Created by junjun on 2017/10/26 0017.
 * SharedPreference工具类
 */

public class ShareUtil {

    static SharedPreferences sharedPreferences;
    public static Gson gson = new Gson();

    public static void init(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * 保存字符串键值对
     *
     * @param key
     * @param value
     */
    public static void setPreferStr(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 保存long键值对
     *
     * @param key
     * @param value
     */
    public static void setPreferLong(String key, Long value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    /**
     * 根据key获取字符串value
     *
     * @param key
     * @return
     */
    public static String getPreferStr(String key) {
        return sharedPreferences.getString(key, "");
    }

    /**
     * 根据key获取long value
     *
     * @param key
     * @return
     */
    public static Long getPreferLong(String key) {
        return sharedPreferences.getLong(key, -1);
    }

    public static boolean getPreferBool(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    public static void setPreferBool(String key) {
        sharedPreferences.edit().putBoolean(key, true).commit();
    }
}
