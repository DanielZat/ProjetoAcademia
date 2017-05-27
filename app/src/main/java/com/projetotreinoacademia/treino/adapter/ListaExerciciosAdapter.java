package com.projetotreinoacademia.treino.adapter;

import android.content.Context;
import android.support.v7.widget.*;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.projetotreinoacademia.R;
import com.projetotreinoacademia.treino.model.Exercicio;

import java.util.List;

public class ListaExerciciosAdapter extends RecyclerView.Adapter<ListaExerciciosAdapter.ListaExerciciosViewHolder> {

    protected static final String TAG = "listaExercicios";
    private final List<Exercicio> exercicios;
    private final Context context;
    private ListaExerciciosOnClickListener listaExerciciosOnClickListener;

    public ListaExerciciosAdapter(Context context, List<Exercicio> exercicios,ListaExerciciosOnClickListener listaExerciciosOnClickListener ){
        this.context = context;
        this.exercicios = exercicios;
        this.listaExerciciosOnClickListener = listaExerciciosOnClickListener;
    }

    @Override
    public int getItemCount(){
        return this.exercicios != null ? this.exercicios.size() : 0;
    }

    @Override
    public ListaExerciciosViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_lista_exercicios,viewGroup,false);
        ListaExerciciosViewHolder holder = new ListaExerciciosViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ListaExerciciosViewHolder holder, final int position){
        //Atualiza a view
        Exercicio exercicio = exercicios.get(position);
        holder.textoDescricao.setText("Descrição: "+exercicio.getDescricao());
        holder.textoRepeticoes.setText("Repetição: "+exercicio.getRepeticoes());
        holder.textoCarga.setText("Carga: "+String.valueOf(exercicio.getCarga()) +"Kg");

        //Click
        if(listaExerciciosOnClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    listaExerciciosOnClickListener.onClickExercicio(holder.itemView,position);
                }
            });
        }

    }

    public interface ListaExerciciosOnClickListener{
        public void onClickExercicio(View view,int idx);
    }

    //ViewHolder com as views
    public static class ListaExerciciosViewHolder extends RecyclerView.ViewHolder {

        public TextView textoDescricao;
        public TextView textoRepeticoes;
        public TextView textoCarga;

        CardView cardView;

        public ListaExerciciosViewHolder(View view){
            super(view);
            //Cria as view para salvar no ViewHolder
            textoDescricao = (TextView) view.findViewById(R.id.txtNomeTreino);
            textoRepeticoes = (TextView) view.findViewById(R.id.txtRepeticoes);
            textoCarga = (TextView) view.findViewById(R.id.txtCarga);
            cardView = (CardView) view.findViewById(R.id.card_view);
        }
    }
}
