package com.projetotreinoacademia.treino.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.*;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.projetotreinoacademia.R;
import com.projetotreinoacademia.treino.adapter.ListaExerciciosAdapter;
import com.projetotreinoacademia.treino.dao.ExercicioDAO;
import com.projetotreinoacademia.treino.model.Exercicio;
import java.util.List;

public class ListaExerciciosFragment extends BaseFragment {

    protected RecyclerView recyclerView;
    private List<Exercicio> exercicios;
    private LinearLayoutManager mLayoutManager;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){

        View view = inflater.inflate(R.layout.fragment_lista_exercicios,container,false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        mLayoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setHasFixedSize(true);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        context = getContext();
        listarExercicios();
    }

    private void listarExercicios(){
        //Busca os exercicios

        ExercicioDAO exercicioDAO = new ExercicioDAO(context);
        this.exercicios = exercicioDAO.listar(context);
        recyclerView.setAdapter(new ListaExerciciosAdapter(getContext(), exercicios, onClickExercicio()));
    }

    private ListaExerciciosAdapter.ListaExerciciosOnClickListener onClickExercicio(){
        return  new ListaExerciciosAdapter.ListaExerciciosOnClickListener(){

            @Override
            public void onClickExercicio(View view, int idx) {
                Exercicio exercicio = exercicios.get(idx);
                Toast.makeText(getContext(),"Exercicio: "+ exercicio.getDescricao(),Toast.LENGTH_LONG).show();
            }
        };
    }
}