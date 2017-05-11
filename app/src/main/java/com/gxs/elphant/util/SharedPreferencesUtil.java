package com.gxs.elphant.util;

import android.content.Context;
import android.content.SharedPreferences;


public class SharedPreferencesUtil {
    private static SharedPreferences sp;
    /**
     * 保存字符串
     */
    public static void saveString(Context context,String key,String value){
        if (sp==null) {
            sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        sp.edit().putString(key, value).commit();
    }

    /**
     * 获取字符串
     */
    public static String getString(Context context,String key,String defvalue){
        if (sp==null) {
            sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        return sp.getString(key, defvalue);
    }
    /**
     * 保存boolean值信息
     */
    public static void saveBoolean(Context context, String key, boolean value){
        if (sp==null) {
            sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        sp.edit().putBoolean(key, value).commit();
    }

    /**
     * 获取boolean值信息
     */
    public static boolean getBoolean(Context context,String key,boolean defvalue){
        if (sp==null) {
            sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        return sp.getBoolean(key, defvalue);
    }
}
