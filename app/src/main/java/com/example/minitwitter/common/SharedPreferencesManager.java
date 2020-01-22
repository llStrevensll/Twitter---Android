package com.example.minitwitter.common;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {

    private static final String APP_SETTINGS_FILE = "APP_SETTINGS";

    private SharedPreferencesManager(){}

    //Preferencias
    private static SharedPreferences getSharedPreference(){
        return MyApp.getContext()
                .getSharedPreferences( APP_SETTINGS_FILE, Context.MODE_PRIVATE);
    }


    public static void setSomeStringValue(String dataLabel, String dataValue){

        SharedPreferences.Editor editor = getSharedPreference().edit();
        editor.putString(dataLabel, dataValue);
        editor.commit();
    }

    public static void setSomeBooleanValue(String dataLabel, boolean dataValue){

        SharedPreferences.Editor editor = getSharedPreference().edit();
        editor.putBoolean(dataLabel, dataValue);
        editor.commit();
    }

    public static String getSomeStringValue(String dataLabel){
        return getSharedPreference().getString(dataLabel, null);//si busca obtener una variable que no existe retorna null
    }

    public static boolean getSomeBooleanValue(String dataLabel) {
        return getSharedPreference().getBoolean(dataLabel, false);//sino existe retorna false

    }
}
