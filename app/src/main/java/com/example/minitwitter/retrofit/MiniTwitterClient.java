package com.example.minitwitter.retrofit;

import com.example.minitwitter.common.Constantes;
import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MiniTwitterClient {

    private static MiniTwitterClient instance = null;
    private MiniTwitterService miniTwitterService;
    private Retrofit retrofit;


    public MiniTwitterClient(){
        //Instancias retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl(Constantes.API_MINITWITTER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //miniTwitterService Service
        miniTwitterService = retrofit.create(MiniTwitterService.class);
    }

    //Devolver instancia de miniTwitterClient
    //Patron Singleton: La instancia se crea solo una vez
    public static MiniTwitterClient getInstance(){
        //Si es nula se crea la instancia
        if (instance == null){
            instance = new MiniTwitterClient();
        }

        return instance;
    }

    //Devolver objeto miniTwitterSetvice, para consumir los servicios
    public MiniTwitterService getMiniTwitterService(){
        return miniTwitterService;
    }


}
