package com.sj.core.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by 申健 on 2018/8/12.
 */

public class SharedPreferencesUtil {
    private static final String FILENAME = "sp";

    public SharedPreferencesUtil() {
    }

    public static void save(Context con, String key, String value) {
        SharedPreferences sp = con.getSharedPreferences(FILENAME, 0);
        sp.edit().putString(key, value).commit();
    }

    public static String get(Context con, String key) {
        SharedPreferences sp = con.getSharedPreferences(FILENAME, 0);
        return sp.getString(key, "");
    }

    public static void addStringSet(Context con, String key, String value) {
        SharedPreferences sp = con.getSharedPreferences(FILENAME, 0);
        Set<String> set = getStringSet(con, key);
        set.add(value);
        sp.edit().putStringSet(key, set).commit();
    }

    public static void removeStringSet(Context con, String key, String value) {
        SharedPreferences sp = con.getSharedPreferences(FILENAME, 0);
        Set<String> set = getStringSet(con, key);
        set.remove(value);
        sp.edit().putStringSet(key, set).commit();
    }

    public static Set<String> getStringSet(Context con, String key) {
        SharedPreferences sp = con.getSharedPreferences(FILENAME, 0);
        return sp.getStringSet(key, new HashSet<String>());
    }

}
