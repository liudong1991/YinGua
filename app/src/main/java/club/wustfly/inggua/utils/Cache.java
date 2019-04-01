package club.wustfly.inggua.utils;

import android.content.Context;
import android.content.SharedPreferences;

import club.wustfly.inggua.YinGuaApplication;

public class Cache {


    private static final String S_NAME = "YinGua";

    public static String getSharedValue(String key) {
        SharedPreferences sp = YinGuaApplication.getInstance().getSharedPreferences(S_NAME, Context.MODE_PRIVATE);
        return sp.getString(key, "");
    }

    public static void putSharedValue(String key, String value) {
        SharedPreferences sp = YinGuaApplication.getInstance().getSharedPreferences(S_NAME, Context.MODE_PRIVATE);
        sp.edit().putString(key, value).commit();
    }

}
