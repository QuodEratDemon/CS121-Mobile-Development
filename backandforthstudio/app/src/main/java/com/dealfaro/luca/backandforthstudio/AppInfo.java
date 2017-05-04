package com.dealfaro.luca.backandforthstudio;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by luca on 18/1/2016.
 */
public class AppInfo {

    private static AppInfo instance = null;
    private static final String COLOR_NAME = "color2";
    private static final String STRING_1 = "";
    private static final String STRING_2 = "";
    private static final String STRING_3 = "";

    protected AppInfo() {
        // Exists only to defeat instantiation.
    }

    // Here are some values we want to keep global.
    public String sharedString;

    public String s1;
    public String s2;
    public String s3;

    private Context my_context;

    public static AppInfo getInstance(Context context) {
        if(instance == null) {
            instance = new AppInfo();
            instance.my_context = context;
            SharedPreferences settings = context.getSharedPreferences(MainActivity.MYPREFS, 0);
            instance.sharedString = settings.getString(COLOR_NAME, null);
        }
        return instance;
    }

    public void setColor(String c) {
        instance.sharedString = c;
        SharedPreferences settings = my_context.getSharedPreferences(MainActivity.MYPREFS, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(COLOR_NAME, c);
        editor.commit();
    }

    public void setString1(String c) {
        instance.s1 = c;
        SharedPreferences settings = my_context.getSharedPreferences(MainActivity.MYPREFS, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(STRING_1, c);
        editor.commit();
    }

    public void setString2(String c) {
        instance.s2 = c;
        SharedPreferences settings = my_context.getSharedPreferences(MainActivity.MYPREFS, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(STRING_2, c);
        editor.commit();
    }

    public void setString3(String c) {
        instance.s3 = c;
        SharedPreferences settings = my_context.getSharedPreferences(MainActivity.MYPREFS, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(STRING_3, c);
        editor.commit();
    }

}
