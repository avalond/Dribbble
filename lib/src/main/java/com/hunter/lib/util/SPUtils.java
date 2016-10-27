package com.hunter.lib.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SPUtils {

    public static final String FILE_NAME = "share_data";

    public static void put(Context context, String key, Object value) {
        put(key, value, context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE));
    }

    public static void put(String key, Object value, SharedPreferences sp) {
        SharedPreferences.Editor editor = sp.edit();

        if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else {
            editor.putString(key, value.toString());
        }

        editor.apply();
    }

    public static Object get(Context context, String key, Object defaultValue) {
        return get(key, defaultValue, context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE));
    }

    public static Object get(String key, Object defaultValue, SharedPreferences sp) {
        if (defaultValue instanceof String) {
            return sp.getString(key, (String) defaultValue);
        } else if (defaultValue instanceof Integer) {
            return sp.getInt(key, (Integer) defaultValue);
        } else if (defaultValue instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultValue);
        } else if (defaultValue instanceof Float) {
            return sp.getFloat(key, (Float) defaultValue);
        } else if (defaultValue instanceof Long) {
            return sp.getLong(key, (Long) defaultValue);
        }
        return null;
    }

    public static void putStrList(Context context, String key, List<String> strList) {
        if (null == strList) return;

        removeStrList(context, key);

        int size = strList.size();
        put(context, key + "size", size);
        for (int i = 0; i < size; i++) put(context, key + i, strList.get(i));
    }

    public static List<String> getStrList(Context context, String key) {
        List<String> strList = new ArrayList<>();
        int size = (int) get(context, key + "size", 0);
        for (int i = 0; i < size; i++) strList.add((String) get(context, key + i, ""));

        return strList;
    }

    public static void remove(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.apply();
    }

    public static void removeStrList(Context context, String key) {
        int size = (int) get(context, key + "size", 0);
        if (0 == size) return;

        remove(context, key + "size");
        for (int i = 0; i < size; i++) remove(context, key + i);
    }

    public static void removeStrListItem(Context context, String key, String str) {
        int size = (int) get(context, key + "size", 0);
        if (0 == size) return;

        List<String> strList = getStrList(context, key);
        List<String> removeList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            if (str.equals(strList.get(i))) {
                removeList.add(strList.get(i));
                remove(context, key + i);
                put(context, key + "size", size - 1);
            }
        }

        strList.removeAll(removeList);
        putStrList(context, key, strList);
    }

    public static void clearAll(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();
    }

    public static Map<String, ?> getAll(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getAll();
    }

    public static boolean contains(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.contains(key);
    }
}