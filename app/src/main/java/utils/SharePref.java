package utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by DigiT-25 on 12-10-2016.
 */
public class SharePref {

    public static final String USER_LANG_VALUE = "USER_LANG_VALUE";
    public static final String USER_LANG_KEY = "USER_LANG_KEY";

    public static final String USERID_VALUE = "USERID_VALUE";
    public static final String USERID_KEY = "USERID_KEY";
    Context context;
    public SharePref(Context context){
        this.context = context;
    }
    //userimage------------------

    public void saveLangValue(String text) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(USER_LANG_VALUE, Context.MODE_PRIVATE); //1
        editor = settings.edit(); //2

        editor.putString(USER_LANG_KEY, text); //3

        editor.commit(); //4
    }

    public String getLangVal() {
        SharedPreferences settings;
        String text;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(USER_LANG_VALUE, Context.MODE_PRIVATE);
        text = settings.getString(USER_LANG_KEY, "null");
        return text;
    }

    public void clearLangValue() {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(USER_LANG_VALUE, Context.MODE_PRIVATE);
        editor = settings.edit();

        editor.clear();
        editor.commit();
    }

    public void removeLangValue() {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(USER_LANG_VALUE, Context.MODE_PRIVATE);
        editor = settings.edit();

        editor.remove(USER_LANG_KEY);
        editor.commit();
    }
// user Id =============================================

    public void saveUserId(String text) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(USERID_VALUE, Context.MODE_PRIVATE); //1
        editor = settings.edit(); //2

        editor.putString(USERID_KEY, text); //3

        editor.commit(); //4
    }

    public String getUserId() {
        SharedPreferences settings;
        String text;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(USERID_VALUE, Context.MODE_PRIVATE);
        text = settings.getString(USERID_KEY, "null");
        return text;
    }

    public void clearUserId() {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(USERID_VALUE, Context.MODE_PRIVATE);
        editor = settings.edit();

        editor.clear();
        editor.commit();
    }

    public void removeUserId() {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(USERID_VALUE, Context.MODE_PRIVATE);
        editor = settings.edit();

        editor.remove(USERID_KEY);
        editor.commit();
    }

}
