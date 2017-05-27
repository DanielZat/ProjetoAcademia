package com.projetotreinoacademia.treino.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BancoDadosOpenHelper extends SQLiteOpenHelper {

    private static String nomeBD = "ProjetoAcademia.db";
    private static String createTableExercicio = "CREATE TABLE IF NOT EXISTS exercicio (id INTEGER PRIMARY KEY AUTOINCREMENT, descricao VARCHAR(30) NOT NULL, repeticoes VARCHAR(20) NOT NULL, carga INTEGER NOT NULL);";
    private static String createTableTreino = "CREATE TABLE IF NOT EXISTS treino (id INTEGER PRIMARY KEY AUTOINCREMENT, nome VARCHAR(45) NOT NULL);";
    private static String createTableHistoricoTreino = "CREATE TABLE IF NOT EXISTS historicotreino (id INTEGER PRIMARY KEY AUTOINCREMENT, treino_id INTEGER NOT NULL," +
                                                       "CONSTRAINT 'fk_treino_has_historicoexercicio_treino' FOREIGN KEY (treino_id) REFERENCES treino (id) ON DELETE CASCADE ON UPDATE CASCADE);";
    private static String createTableTreinoExercicio = "CREATE TABLE IF NOT EXISTS treinoexercicio (treino_id INTEGER NOT NULL, exercicio_id INTEGER NOT NULL, PRIMARY KEY (treino_id, exercicio_id)," +
                                                        "CONSTRAINT 'fk_treino_has_exercicio_treino' FOREIGN KEY (treino_id) REFERENCES treino (id) ON DELETE CASCADE ON UPDATE CASCADE," +
                                                        "CONSTRAINT 'fk_treino_has_exercicio_exercicio' FOREIGN KEY (exercicio_id) REFERENCES exercicio (id) ON DELETE CASCADE ON UPDATE CASCADE);";

    public BancoDadosOpenHelper(Context context) {
        super(context, nomeBD, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTableExercicio);
        db.execSQL(createTableTreino);
        db.execSQL(createTableHistoricoTreino);
        db.execSQL(createTableTreinoExercicio);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion>oldVersion){
            db.execSQL(createTableExercicio);
            db.execSQL(createTableTreino);
            db.execSQL(createTableHistoricoTreino);
            db.execSQL(createTableTreinoExercicio);
        }
    }
}
