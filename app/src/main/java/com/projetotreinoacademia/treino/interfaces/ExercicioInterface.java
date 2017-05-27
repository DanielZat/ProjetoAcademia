package com.projetotreinoacademia.treino.interfaces;

import android.content.Context;

import com.projetotreinoacademia.treino.model.Exercicio;
import java.util.List;

public interface ExercicioInterface {

    public String inserir(Exercicio exercicio);

    public void excluir(Exercicio exercicio);

    public void atualizar(Exercicio exercicio);

    public List<Exercicio> listar(Context context);

    public Exercicio procurarPorId(int id);

}
