package com.projetotreinoacademia.treino.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.projetotreinoacademia.treino.interfaces.ExercicioInterface;
import com.projetotreinoacademia.treino.model.Exercicio;
import java.util.ArrayList;
import java.util.List;

public class ExercicioDAO implements ExercicioInterface {

    private BancoDadosOpenHelper bdOpenHelper;

    public  ExercicioDAO(Context contexto){
        bdOpenHelper = new BancoDadosOpenHelper(contexto);
    }

    @Override
    public String inserir(Exercicio exercicio) {

        SQLiteDatabase banco = bdOpenHelper.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("descricao",exercicio.getDescricao());
        valores.put("repeticoes",exercicio.getRepeticoes());
        valores.put("carga", exercicio.getCarga());
        banco.insert("exercicio", null, valores);

        banco.close();

        return "Inserido com sucesso!";
    }

    @Override
    public void excluir(Exercicio exercicio) {
        SQLiteDatabase banco = bdOpenHelper.getWritableDatabase();
        banco.delete("exercicio", "id=?", new String[]{String.valueOf(exercicio.getId())});
        banco.close();
    }

    @Override
    public void atualizar(Exercicio exercicio) {

    }

    @Override
    public List<Exercicio> listar(Context context) {

        //SQLiteDatabase banco = bdOpenHelper.getReadableDatabase();
        SQLiteDatabase banco = bdOpenHelper.getWritableDatabase();

        Cursor cursor = banco.query("exercicio", new String[]{"id", "descricao", "repeticoes", "carga"}, null, null, null, null, "descricao");

        List<Exercicio> listaDeExercicios = new ArrayList<>();

        while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String descricao = cursor.getString(cursor.getColumnIndex("descricao"));
            String repeticoes = cursor.getString(cursor.getColumnIndex("repeticoes"));
            int carga = cursor.getInt(cursor.getColumnIndex("carga"));

            Exercicio exercicio = new Exercicio();

            exercicio.setId(id);
            exercicio.setDescricao(descricao);
            exercicio.setRepeticoes(repeticoes);
            exercicio.setCarga(carga);

            listaDeExercicios.add(exercicio);
        }
        return listaDeExercicios;
    }

    @Override
    public Exercicio procurarPorId(int id) {
        return null;
    }
}
