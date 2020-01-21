package com.example.minitwitter.common;

import android.app.Application;
import android.content.Context;

public class MyApp extends Application {

    public static MyApp instance;

    public static MyApp getInstance(){
        return instance;
    }

    public  static Context getContext(){
        return instance;
    }

    /*onCreate solo crea una vez cuando se inicia la aplicación
      por ello instance solo se creara una vez (singleton)*/
    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
    }
}
