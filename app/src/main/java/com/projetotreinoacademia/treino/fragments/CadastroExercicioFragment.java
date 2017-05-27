package com.projetotreinoacademia.treino.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.projetotreinoacademia.R;

public class CadastroExercicioFragment extends BaseFragment {

    private CadastroExercicioFragmentListener mListener;

    public CadastroExercicioFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cadastro_exercicio,container,false);

        Button button = (Button) view.findViewById(R.id.btnFinalizar);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener != null) mListener.inserirExercicio();
            }
        });

        return view;
    }

    public interface CadastroExercicioFragmentListener {
        public void inserirExercicio();
    }

    public void onAttach(Activity a) {
        super.onAttach(a);

        if(a instanceof CadastroExercicioFragmentListener) {
            mListener = (CadastroExercicioFragmentListener) a;
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
}
