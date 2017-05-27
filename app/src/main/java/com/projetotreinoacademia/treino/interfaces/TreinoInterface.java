package com.projetotreinoacademia.treino.interfaces;

import com.projetotreinoacademia.treino.model.Treino;
import java.util.List;

public interface TreinoInterface {

    public String inserir(Treino treino);

    public void excluir(Treino treino);

    public void atualizar(Treino treino);

    public List<Treino> listar();

    public Treino procurarPorId(int id);

}
