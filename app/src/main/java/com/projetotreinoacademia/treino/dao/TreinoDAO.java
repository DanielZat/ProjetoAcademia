package com.projetotreinoacademia.treino.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.projetotreinoacademia.treino.interfaces.TreinoInterface;
import com.projetotreinoacademia.treino.model.Exercicio;
import com.projetotreinoacademia.treino.model.Treino;
import java.util.ArrayList;
import java.util.List;

public class TreinoDAO implements TreinoInterface {

    private BancoDadosOpenHelper bdOpenHelper;

    public  TreinoDAO(Context contexto){
        bdOpenHelper = new BancoDadosOpenHelper(contexto);
    }

    @Override
    public String inserir(Treino treino) {

        SQLiteDatabase banco = bdOpenHelper.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("nome",treino.getNome());
        banco.insert("treino", null, valores);

        //PEGA ID INSERIDO NO INSERT DO TREINO
        final String ultimoId = "SELECT last_insert_rowid()";
        Cursor cursor = banco.rawQuery(ultimoId, null);
        cursor.moveToFirst();
        int id = cursor.getInt(0);
        cursor.close();

       for(int i=0;i<treino.getExercicios().size();i++){
           valores = new ContentValues();
           valores.put("treino_id",id);
           valores.put("exercicio_id",treino.getExercicios().get(i).getId());
           banco.insert("treinoexercicio",null,valores);
       }

        banco.close();

        return "Inserido com sucesso!";
    }

    @Override
    public void excluir(Treino treino) {
        SQLiteDatabase banco = bdOpenHelper.getWritableDatabase();
        banco.delete("treino", "id=?", new String[]{String.valueOf(treino.getId())});
        banco.close();
    }

    @Override
    public void atualizar(Treino treino) {

    }

    @Override
    public List<Treino> listar() {

        SQLiteDatabase banco = bdOpenHelper.getWritableDatabase();
        Cursor cursor = banco.query("treino", new String[]{"id", "nome"}, null, null, null, null,null);
        List<Treino> listaDeTreinos = new ArrayList<>();

        while(cursor.moveToNext()){
            int idTreino = cursor.getInt(cursor.getColumnIndex("id"));
            String nome = cursor.getString(cursor.getColumnIndex("nome"));

            Treino treino = new Treino();
            treino.setId(idTreino);
            treino.setNome(nome);

            final String queryExerciciosTreino = "SELECT e.id, e.descricao, e.repeticoes, e.carga FROM exercicio e JOIN treinoexercicio te ON e.id = te.exercicio_id WHERE te.treino_id = ?";
            Cursor cursorExercicio = banco.rawQuery(queryExerciciosTreino, new String[]{String.valueOf(treino.getId())});

            List<Exercicio> exerciciosTreino = new ArrayList<>();

            while(cursorExercicio.moveToNext()){
                int idExercicio = cursorExercicio.getInt(cursorExercicio.getColumnIndex("id"));
                String descricao = cursorExercicio.getString(cursorExercicio.getColumnIndex("descricao"));
                String repeticoes = cursorExercicio.getString(cursorExercicio.getColumnIndex("repeticoes"));
                int carga = cursorExercicio.getInt(cursorExercicio.getColumnIndex("carga"));

                Exercicio exercicio = new Exercicio();

                exercicio.setId(idExercicio);
                exercicio.setDescricao(descricao);
                exercicio.setRepeticoes(repeticoes);
                exercicio.setCarga(carga);

                exerciciosTreino.add(exercicio);

            }

            treino.setExercicios(exerciciosTreino);

            listaDeTreinos.add(treino);
        }
        return listaDeTreinos;
    }

    public Treino exibirTreinoDiario(){

        SQLiteDatabase banco = bdOpenHelper.getWritableDatabase();
        Treino treino = new Treino();

        final String queryHistoricoTreino = "SELECT h.id, h.treino_id FROM historicotreino h ORDER BY h.id DESC LIMIT 1";
        Cursor cursorHistoricoTreino = banco.rawQuery(queryHistoricoTreino,null);

        //SE TIVER CONTEÚDO
        if(cursorHistoricoTreino.moveToNext()){

            final String queryTreino = "SELECT * FROM treino WHERE treino.id > ? LIMIT 1";
            Cursor cursorTreino = banco.rawQuery(queryTreino,new String[]{String.valueOf(cursorHistoricoTreino.getInt(cursorHistoricoTreino.getColumnIndex("treino_id")))});

            //SE TIVER PROXIMO (NAO FOR O ULTIMO)
            if(cursorTreino.moveToNext()){
                int idTreino = cursorTreino.getInt(cursorTreino.getColumnIndex("id"));
                String nome = cursorTreino.getString(cursorTreino.getColumnIndex("nome"));

                treino = new Treino();
                treino.setId(idTreino);
                treino.setNome(nome);
                final String queryExerciciosTreino = "SELECT e.id, e.descricao, e.repeticoes, e.carga FROM exercicio e JOIN treinoexercicio te ON e.id = te.exercicio_id WHERE te.treino_id = ?";
                Cursor cursorExercicios = banco.rawQuery(queryExerciciosTreino, new String[]{String.valueOf(treino.getId())});

                List<Exercicio> exerciciosTreino = new ArrayList<>();

                while (cursorExercicios.moveToNext()) {
                    int idExercicio = cursorExercicios.getInt(cursorExercicios.getColumnIndex("id"));
                    String descricao = cursorExercicios.getString(cursorExercicios.getColumnIndex("descricao"));
                    String repeticoes = cursorExercicios.getString(cursorExercicios.getColumnIndex("repeticoes"));
                    int carga = cursorExercicios.getInt(cursorExercicios.getColumnIndex("carga"));

                    Exercicio exercicio = new Exercicio();

                    exercicio.setId(idExercicio);
                    exercicio.setDescricao(descricao);
                    exercicio.setRepeticoes(repeticoes);
                    exercicio.setCarga(carga);

                    exerciciosTreino.add(exercicio);

                }
                treino.setExercicios(exerciciosTreino);
            }
            //NÃO EXISTE PROXIMO TREINO, VOLTAR AO PRIMEIRO
            else{
                Log.i("Teste","Voltou ao primeiro treino pois era o ultimo");
                final String queryPrimeiroTreino = "SELECT * FROM treino ORDER BY treino.id ASC LIMIT 1";
                Cursor cursorPrimeiroTreino = banco.rawQuery(queryPrimeiroTreino,null);

                if(cursorPrimeiroTreino.moveToNext()) {
                    int idTreino = cursorPrimeiroTreino.getInt(cursorPrimeiroTreino.getColumnIndex("id"));
                    String nome = cursorPrimeiroTreino.getString(cursorPrimeiroTreino.getColumnIndex("nome"));

                    treino = new Treino();
                    treino.setId(idTreino);
                    treino.setNome(nome);
                    final String queryExerciciosTreino = "SELECT e.id, e.descricao, e.repeticoes, e.carga FROM exercicio e JOIN treinoexercicio te ON e.id = te.exercicio_id WHERE te.treino_id = ?";
                    Cursor cursorExercicios = banco.rawQuery(queryExerciciosTreino, new String[]{String.valueOf(treino.getId())});

                    List<Exercicio> exerciciosTreino = new ArrayList<>();

                    while (cursorExercicios.moveToNext()) {
                        int idExercicio = cursorExercicios.getInt(cursorExercicios.getColumnIndex("id"));
                        String descricao = cursorExercicios.getString(cursorExercicios.getColumnIndex("descricao"));
                        String repeticoes = cursorExercicios.getString(cursorExercicios.getColumnIndex("repeticoes"));
                        int carga = cursorExercicios.getInt(cursorExercicios.getColumnIndex("carga"));

                        Exercicio exercicio = new Exercicio();

                        exercicio.setId(idExercicio);
                        exercicio.setDescricao(descricao);
                        exercicio.setRepeticoes(repeticoes);
                        exercicio.setCarga(carga);

                        exerciciosTreino.add(exercicio);

                    }
                    treino.setExercicios(exerciciosTreino);
                }
            }
        }
        //SE ESTIVER EM BRANCO, BUSCA PRIMEIRO TREINO E RETORNA
        else{
            //final String queryPrimeiroTreino = "SELECT * FROM treino WHERE ROWNUM = 1";
            final String queryPrimeiroTreino = "SELECT * FROM treino ORDER BY treino.id ASC LIMIT 1";
            Cursor cursorTreino = banco.rawQuery(queryPrimeiroTreino,null);

            if(cursorTreino.moveToNext()) {
                int idTreino = cursorTreino.getInt(cursorTreino.getColumnIndex("id"));
                String nome = cursorTreino.getString(cursorTreino.getColumnIndex("nome"));

                treino = new Treino();
                treino.setId(idTreino);
                treino.setNome(nome);
                final String queryExerciciosTreino = "SELECT e.id, e.descricao, e.repeticoes, e.carga FROM exercicio e JOIN treinoexercicio te ON e.id = te.exercicio_id WHERE te.treino_id = ?";
                Cursor cursorExercicios = banco.rawQuery(queryExerciciosTreino, new String[]{String.valueOf(treino.getId())});

                List<Exercicio> exerciciosTreino = new ArrayList<>();

                while (cursorExercicios.moveToNext()) {
                    int idExercicio = cursorExercicios.getInt(cursorExercicios.getColumnIndex("id"));
                    String descricao = cursorExercicios.getString(cursorExercicios.getColumnIndex("descricao"));
                    String repeticoes = cursorExercicios.getString(cursorExercicios.getColumnIndex("repeticoes"));
                    int carga = cursorExercicios.getInt(cursorExercicios.getColumnIndex("carga"));

                    Exercicio exercicio = new Exercicio();

                    exercicio.setId(idExercicio);
                    exercicio.setDescricao(descricao);
                    exercicio.setRepeticoes(repeticoes);
                    exercicio.setCarga(carga);

                    exerciciosTreino.add(exercicio);

                }
                treino.setExercicios(exerciciosTreino);
            }
        }
        return treino;
    }

    public String finalizarTreino(int id){

        Log.i("Teste. Finalizei: ",String.valueOf(id));

        SQLiteDatabase banco = bdOpenHelper.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("treino_id",id);
        banco.insert("historicotreino", null, valores);

        banco.close();

        return "Exercícios Realizados com sucesso";
    }

    @Override
    public Treino procurarPorId(int id) {
        return null;
    }
}
