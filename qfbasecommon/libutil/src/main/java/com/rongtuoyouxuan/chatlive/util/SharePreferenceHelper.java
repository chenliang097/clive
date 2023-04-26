package com.rongtuoyouxuan.chatlive.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.List;
import java.util.Map;

import com.rongtuoyouxuan.chatlive.log.PLog;

/**
 * @author KingOX on 2015/12/18.
 */
public class SharePreferenceHelper {
    private static final String TAG = SharePreferenceHelper.class.getSimpleName();
    private static SharedPreferences mSharedPreferences;

    private static SharedPreferences getSharePreference(Context context) {
        if (mSharedPreferences == null) {
            mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        }
        return mSharedPreferences;
    }

    public static boolean save(Context context, String key, String value) {
        SharedPreferences.Editor editor = getSharePreference(context).edit();
        try {
            editor.putString(key, value);
        } catch (Exception e) {
            editor.putString(key, value);
            PLog.e(TAG,e.getMessage());
        }
        return editor.commit();
    }

    public static boolean clear(Context context) {
        SharedPreferences.Editor editor = getSharePreference(context).edit();
        return editor.clear().commit();
    }

    public static String load(Context context, String key, String defValue) {
        String str = defValue;
        try {
            str = getSharePreference(context).getString(key, defValue);
        } catch (Exception e) {
            PLog.e(TAG,e.getMessage());
        }
        return str;
    }

    public static boolean save(Context context, String key, int value) {
        SharedPreferences.Editor editor = getSharePreference(context).edit();
        editor.putInt(key, value);
        return editor.commit();
    }

    public static int getInt(Context context, String key, int defValue) {
        return getSharePreference(context).getInt(key, defValue);
    }

    public static boolean save(Context context, String key, float value) {
        SharedPreferences.Editor editor = getSharePreference(context).edit();
        editor.putFloat(key, value);
        return editor.commit();
    }

    public static float getFloat(Context context, String key, float defValue) {
        return getSharePreference(context).getFloat(key, defValue);
    }

    public static boolean save(Context context, String key, long value) {
        SharedPreferences.Editor editor = getSharePreference(context).edit();
        editor.putLong(key, value);
        return editor.commit();
    }

    public static long getLong(Context context, String key, long defValue) {
        return getSharePreference(context).getLong(key, defValue);
    }

    public static boolean save(Context context, String key, Boolean value) {
        SharedPreferences.Editor editor = getSharePreference(context).edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }

    public static boolean getBoolean(Context context, String key, boolean defValue) {
        return getSharePreference(context).getBoolean(key, defValue);
    }

    public static boolean save(Context context, String keyName, List<?> list) {
        int size = list.size();
        if (size < 1) {
            return false;
        }
        SharedPreferences.Editor editor = getSharePreference(context).edit();
        if (list.get(0) instanceof String) {
            for (int i = 0; i < size; i++) {
                editor.putString(keyName + i, (String) list.get(i));
            }
        } else if (list.get(0) instanceof Long) {
            for (int i = 0; i < size; i++) {
                editor.putLong(keyName + i, (Long) list.get(i));
            }
        } else if (list.get(0) instanceof Float) {
            for (int i = 0; i < size; i++) {
                editor.putFloat(keyName + i, (Float) list.get(i));
            }
        } else if (list.get(0) instanceof Integer) {
            for (int i = 0; i < size; i++) {
                editor.putLong(keyName + i, (Integer) list.get(i));
            }
        } else if (list.get(0) instanceof Boolean) {
            for (int i = 0; i < size; i++) {
                editor.putBoolean(keyName + i, (Boolean) list.get(i));
            }
        }
        return editor.commit();
    }

    public static Map<String, ?> loadAllSharePreference(Context context, String key) {
        return getSharePreference(context).getAll();
    }

    public static boolean removeKey(Context context, String key) {
        SharedPreferences.Editor editor = getSharePreference(context).edit();
        editor.remove(key);
        return editor.commit();
    }

    public static boolean removeAllKey(Context context) {
        SharedPreferences.Editor editor = getSharePreference(context).edit();
        editor.clear();
        return editor.commit();
    }

    public static boolean HasKey(Context context, String key) {
        return getSharePreference(context).contains(key);
    }
}