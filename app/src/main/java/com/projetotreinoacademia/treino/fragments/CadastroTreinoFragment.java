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
import com.projetotreinoacademia.treino.adapter.CadastroTreinoAdapter;
import com.projetotreinoacademia.treino.dao.ExercicioDAO;
import com.projetotreinoacademia.treino.model.Exercicio;
import java.util.List;

public class CadastroTreinoFragment extends BaseFragment{

    protected RecyclerView recyclerView;
    private List<Exercicio> exercicios;
    private LinearLayoutManager mLayoutManager;
    private Context context;
    private CadastroTreinoFragmentListener mListener;
   //teste
    private CadastroTreinoAdapter cadastroTreinoAdapter;

    public CadastroTreinoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cadastro_treino,container,false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        mLayoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setHasFixedSize(true);

        Button button = (Button) view.findViewById(R.id.btnFinalizar);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener != null) mListener.inserirTreino(cadastroTreinoAdapter.getExercicios());
            }
        });

        return view;
    }

    public interface CadastroTreinoFragmentListener {
        public void inserirTreino(List<Exercicio> exercicios);
    }

    public void onAttach(Activity a) {
        super.onAttach(a);

        if(a instanceof CadastroTreinoFragmentListener) {
            mListener = (CadastroTreinoFragmentListener) a;
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
        listarExercicios();
    }

    private void listarExercicios(){
        //Busca os exercicios

        ExercicioDAO exercicioDAO = new ExercicioDAO(context);
        this.exercicios = exercicioDAO.listar(context);

        cadastroTreinoAdapter = new CadastroTreinoAdapter(getContext(), exercicios, onClickExercicio());
        recyclerView.setAdapter(cadastroTreinoAdapter);
        //recyclerView.setAdapter(new CadastroTreinoAdapter(getContext(), exercicios, onClickExercicio()));
    }

    private CadastroTreinoAdapter.ListaExerciciosCadastroTreinoOnClickListener onClickExercicio(){
        return  new CadastroTreinoAdapter.ListaExerciciosCadastroTreinoOnClickListener(){

            @Override
            public void onClickExercicio(View view, int idx) {
                Exercicio exercicio = exercicios.get(idx);
                Toast.makeText(getContext(), "Exercicio: " + exercicio.getDescricao(), Toast.LENGTH_LONG).show();
            }
        };
    }
}
