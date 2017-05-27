package com.projetotreinoacademia.treino;

import android.app.Application;
import android.util.Log;

public class TreinoAcademiaApplication extends Application {

    private static final String TAG = "TreinoApplication";
    private static TreinoAcademiaApplication instance = null;

    public static TreinoAcademiaApplication getInstance(){
        return instance; //Singleton
    }

    @Override
    public void onCreate(){
        Log.d(TAG,"TreinoAcademiaApplication.onCreate()");
        //Salva a instância para termos acesso como Singleton
        instance = this;
    }

    @Override
    public void onTerminate(){
        super.onTerminate();
        Log.d(TAG, "TreinoAcademiaApplication.onTerminate()");
    }
}
