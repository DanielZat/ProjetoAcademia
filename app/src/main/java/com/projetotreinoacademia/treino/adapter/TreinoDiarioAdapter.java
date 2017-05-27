package com.projetotreinoacademia.treino.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.projetotreinoacademia.R;
import com.projetotreinoacademia.treino.model.Exercicio;

import java.util.List;

public class TreinoDiarioAdapter extends RecyclerView.Adapter<TreinoDiarioAdapter.ListaExerciciosTreinoDiarioViewHolder> {

    protected static final String TAG = "treinoDiario";
    private final List<Exercicio> exercicios;
    private final Context context;
    private ListaExerciciosTreinoDiarioOnClickListener listaExerciciosTreinoDiarioOnClickListener;

    public TreinoDiarioAdapter(Context context, List<Exercicio> exercicios,ListaExerciciosTreinoDiarioOnClickListener listaExerciciosTreinoDiarioOnClickListener){
        this.context = context;
        this.exercicios = exercicios;
        this.listaExerciciosTreinoDiarioOnClickListener = listaExerciciosTreinoDiarioOnClickListener;
    }

    @Override
    public int getItemCount(){
        return this.exercicios != null ? this.exercicios.size() : 0;
    }

    @Override
    public ListaExerciciosTreinoDiarioViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_lista_exercicios_treino_diario,viewGroup,false);
        ListaExerciciosTreinoDiarioViewHolder holder = new ListaExerciciosTreinoDiarioViewHolder(view);
        return holder;
    }

    public List<Exercicio> getExercicios() {
        return exercicios;
    }

    @Override
    public void onBindViewHolder(final ListaExerciciosTreinoDiarioViewHolder holder, final int position){
        //Atualiza a view
        Exercicio exercicio = exercicios.get(position);

        holder.textoDescricao.setText(exercicio.getDescricao());
        holder.textoRepeticoes.setText("Repetição: "+exercicio.getRepeticoes());
        holder.textoCarga.setText("Carga: "+String.valueOf(exercicio.getCarga()) +"Kg");

        holder.chkExercicio.setOnCheckedChangeListener(null);

        holder.chkExercicio.setChecked(exercicios.get(position).isSelected());

        holder.chkExercicio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                exercicios.get(holder.getAdapterPosition()).setSelected(isChecked);
            }
        });
    }

    private Object getItem(int position) {
        return exercicios.get(position);
    }

    public interface ListaExerciciosTreinoDiarioOnClickListener{
        public void onClickExercicio(View view,int idx);
    }

    //ViewHolder com as views
    public static class ListaExerciciosTreinoDiarioViewHolder extends RecyclerView.ViewHolder {

        public TextView textoDescricao;
        public TextView textoRepeticoes;
        public TextView textoCarga;
        public CheckBox chkExercicio;
        CardView cardView;

        public ListaExerciciosTreinoDiarioViewHolder(View view){
            super(view);
            //Cria as view para salvar no ViewHolder
            textoDescricao = (TextView) view.findViewById(R.id.txtExercicio);
            textoRepeticoes = (TextView) view.findViewById(R.id.txtRepeticoes);
            textoCarga = (TextView) view.findViewById(R.id.txtCarga);
            chkExercicio = (CheckBox) view.findViewById(R.id.chkExercicio);
            cardView = (CardView) view.findViewById(R.id.card_view);
        }
    }
}