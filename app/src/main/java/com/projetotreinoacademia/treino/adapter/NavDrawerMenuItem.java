package com.projetotreinoacademia.treino.adapter;

import com.projetotreinoacademia.R;

import java.util.ArrayList;
import java.util.List;

public class NavDrawerMenuItem {

    public int title;
    public int img;
    public boolean selected;

    public NavDrawerMenuItem(int title, int img){
        this.title = title;
        this.img = img;
    }

    //Cria a lista com os itens de menu
    public static List<NavDrawerMenuItem> getList(){

        List<NavDrawerMenuItem> list = new ArrayList<NavDrawerMenuItem>();
        list.add(new NavDrawerMenuItem(R.string.treino_diario,R.drawable.treino_diario));
        list.add(new NavDrawerMenuItem(R.string.cadastro_exercicio,R.drawable.cadastro_exercicio));
        list.add(new NavDrawerMenuItem(R.string.cadastro_treino,R.drawable.cadastro_treino));
        list.add(new NavDrawerMenuItem(R.string.exercicios_cadastrados,R.drawable.exercicios_cadastrados));
        list.add(new NavDrawerMenuItem(R.string.treinos_cadastrados,R.drawable.treinos_cadastrados));

        return list;
    }

}
