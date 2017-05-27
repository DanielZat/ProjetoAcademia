package com.projetotreinoacademia.treino.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.projetotreinoacademia.R;
import com.projetotreinoacademia.treino.adapter.ListaTreinosAdapter;
import com.projetotreinoacademia.treino.dao.TreinoDAO;
import com.projetotreinoacademia.treino.model.Treino;
import java.util.List;

public class ListaTreinosFragment extends BaseFragment {

    protected RecyclerView recyclerView;
    private List<Treino> treinos;
    private LinearLayoutManager mLayoutManager;
    private Context context;
    TreinoDAO treinoDAO = new TreinoDAO(context);
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){

        View view = inflater.inflate(R.layout.fragment_lista_treinos,container,false);

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
        listarTreinos();
    }

    private void listarTreinos(){
        //Busca os treinos
        treinoDAO = new TreinoDAO(context);
        //TreinoDAO treinoDAO = new TreinoDAO(context);
        this.treinos = treinoDAO.listar();
        recyclerView.setAdapter(new ListaTreinosAdapter(getContext(), treinos, onClickTreino()));
    }

    private ListaTreinosAdapter.ListaTreinosOnClickListener onClickTreino(){
        return  new ListaTreinosAdapter.ListaTreinosOnClickListener(){

            @Override
            public void onClickTreino(View view, int idx) {
                final Treino treino = treinos.get(idx);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                alertDialogBuilder.setTitle("Atenção");
                alertDialogBuilder.setMessage("Deseja excluir o item " + treino.getNome() +"?").setCancelable(false)
                        .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(getContext(), "Treino excluído: " + treino.getNome(), Toast.LENGTH_LONG).show();
                                treinoDAO.excluir(treino);
                                listarTreinos();
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        };
    }
}
