package club.wustfly.inggua.utils;

import android.content.Context;
import android.content.SharedPreferences;

import club.wustfly.inggua.YinGuaApplication;


public class SPUtil {

    private static final String YIN_GUA_SP_KEY = "yingua";

    private static SharedPreferences sp;

    static {
        sp = YinGuaApplication.getInstance().getSharedPreferences(YIN_GUA_SP_KEY, Context.MODE_PRIVATE);
    }

    public static void put(String key, String value) {
        sp.edit().putString(key, value).apply();
    }

    public static String get(String key) {
        return sp.getString(key, "");
    }

}
