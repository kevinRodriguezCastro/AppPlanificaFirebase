package com.cifpceuta.appplanificafirebase.Adapter;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.cifpceuta.appplanificafirebase.Clases.Practica;
import com.cifpceuta.appplanificafirebase.R;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    private ArrayList<Practica> list_item;
    private String inicio,fin;

    public ItemAdapter(ArrayList<Practica> listaTareas) {
        this.list_item = listaTareas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);
        return new ItemAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(list_item.get(position));
        holder.cardView.setCardBackgroundColor(Color.WHITE);

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        //LocalDate i = LocalDate.parse(LocalDate.now().toString(),formato);
        LocalDate f = LocalDate.parse(fin,formato);
        long diferenciaEnDias = ChronoUnit.DAYS.between(LocalDate.now(), f);

        if (diferenciaEnDias > 3) {
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(holder.cardView.getContext(), R.color.timpoV));
        } else if (diferenciaEnDias <= 3 && diferenciaEnDias >= 0) {
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(holder.cardView.getContext(), R.color.tiempoA));
        } else {
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(holder.cardView.getContext(), R.color.tiempoR));
        }

    }

    @Override
    public int getItemCount() {
        return list_item.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView modulo,curso,fechaIn,fechaFin,titulo;
        ItemAdapter adapter;
        CardView cardView;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            titulo = itemView.findViewById(R.id.ilTitulo);
            modulo = itemView.findViewById(R.id.ilModulo);
            curso = itemView.findViewById(R.id.ilCurso);
            fechaIn = itemView.findViewById(R.id.ilFechaIn);
            fechaFin = itemView.findViewById(R.id.ilFechaFin);



        }
        void bindData(final Practica item) {
            inicio = item.getFechaIn();
            fin = item.getFechaFin();

            modulo.setText(item.getModulo());
            curso.setText(item.getCurso());
            titulo.setText(item.getTitulo());
            fechaIn.setText(inicio);
            fechaFin.setText(fin);


        }
    }

    public void setList_item(ArrayList<Practica> list_item) {
        this.list_item = list_item;
        notifyDataSetChanged();
    }



    public void limpiar(){
        //this.par = false;

        notifyDataSetChanged();
    }
}