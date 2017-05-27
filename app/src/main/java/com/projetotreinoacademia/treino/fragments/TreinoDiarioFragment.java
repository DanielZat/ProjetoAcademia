package com.projetotreinoacademia.treino.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.projetotreinoacademia.R;
import com.projetotreinoacademia.treino.adapter.TreinoDiarioAdapter;
import com.projetotreinoacademia.treino.dao.TreinoDAO;
import com.projetotreinoacademia.treino.model.Exercicio;
import com.projetotreinoacademia.treino.model.Treino;

import java.util.List;

public class TreinoDiarioFragment extends BaseFragment {

    protected RecyclerView recyclerView;
    private Treino treino = new Treino();
    private LinearLayoutManager mLayoutManager;
    private Context context;
    private TreinoDiarioFragmentListener mListener;
    private TreinoDiarioAdapter treinoDiarioAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_treino_diario,container,false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        mLayoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setHasFixedSize(true);

        Button button = (Button) view.findViewById(R.id.btnFinalizar);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener != null) mListener.FinalizarTreino(treino,treinoDiarioAdapter.getExercicios());
            }
        });

        return view;
    }

    public interface TreinoDiarioFragmentListener {
        public void FinalizarTreino(Treino treino, List<Exercicio> exercicios);
    }

    public void onAttach(Activity a) {
        super.onAttach(a);

        if(a instanceof TreinoDiarioFragmentListener) {
            mListener = (TreinoDiarioFragmentListener) a;
        }
    }

    public void onDestroy() {
        super.onDestroy();
        mListener = null;
    }

    public void onDestroyView() {
        super.onDestroyView();
        mListener = null;
    }

    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        context = getContext();
        exibirTreinoDiario();
    }

    private void exibirTreinoDiario(){
        //Busca o treino diario

        TreinoDAO treinoDAO = new TreinoDAO(context);
        treino = treinoDAO.exibirTreinoDiario();

        treinoDiarioAdapter = new TreinoDiarioAdapter(getContext(), treino.getExercicios(), onClickExercicio());
        recyclerView.setAdapter(treinoDiarioAdapter);

    }

    private TreinoDiarioAdapter.ListaExerciciosTreinoDiarioOnClickListener onClickExercicio(){
        return  new TreinoDiarioAdapter.ListaExerciciosTreinoDiarioOnClickListener(){

            @Override
            public void onClickExercicio(View view, int idx) {
                Exercicio exercicio = treino.getExercicios().get(idx);
                Toast.makeText(getContext(), "Exercicio: " + exercicio.getDescricao(), Toast.LENGTH_LONG).show();
            }
        };
    }


}
