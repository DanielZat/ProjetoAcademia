package com.projetotreinoacademia.treino.model;

import java.io.Serializable;
import java.util.List;

public class Treino implements Serializable {

    private int id;
    private String nome;
    private List<Exercicio> exercicios;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Exercicio> getExercicios() {
        return exercicios;
    }

    public void setExercicios(List<Exercicio> exercicios) {
        this.exercicios = exercicios;
    }
}
