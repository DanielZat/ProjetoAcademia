package com.projetotreinoacademia.treino.adapter;


import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.projetotreinoacademia.R;
import com.projetotreinoacademia.treino.model.Treino;

import java.util.List;

public class ListaTreinosAdapter extends RecyclerView.Adapter<ListaTreinosAdapter.ListaTreinosViewHolder> {

    protected static final String TAG = "listaExercicios";
    private final List<Treino> treinos;
    private final Context context;
    private ListaTreinosOnClickListener listaTreinosOnClickListener;

    public ListaTreinosAdapter(Context context, List<Treino> treinos,ListaTreinosOnClickListener listaTreinosOnClickListener ){
        this.context = context;
        this.treinos = treinos;
        this.listaTreinosOnClickListener = listaTreinosOnClickListener;
    }


    @Override
    public int getItemCount(){
        return this.treinos != null ? this.treinos.size() : 0;
    }

    @Override
    public ListaTreinosViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_lista_treinos,viewGroup,false);
        ListaTreinosViewHolder holder = new ListaTreinosViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ListaTreinosViewHolder holder, final int position){
        //Atualiza a view
        Treino treino = treinos.get(position);
        holder.textoNome.setText("Treino: "+treino.getNome());

        //Click
        if(listaTreinosOnClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    listaTreinosOnClickListener.onClickTreino(holder.itemView, position);
                }
            });
        }
    }

    public interface ListaTreinosOnClickListener{
        public void onClickTreino(View view,int idx);
    }

    //ViewHolder com as views
    public static class ListaTreinosViewHolder extends RecyclerView.ViewHolder {

        public TextView textoNome;
        CardView cardView;

        public ListaTreinosViewHolder(View view){
            super(view);
            //Cria as view para salvar no ViewHolder
            textoNome = (TextView) view.findViewById(R.id.txtTreino);
            cardView = (CardView) view.findViewById(R.id.card_view);
        }
    }
}
