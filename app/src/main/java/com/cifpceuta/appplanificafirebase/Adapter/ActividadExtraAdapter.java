package com.cifpceuta.appplanificafirebase.Adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.cifpceuta.appplanificafirebase.Clases.ActividadExtra;
import com.cifpceuta.appplanificafirebase.Clases.Practica;
import com.cifpceuta.appplanificafirebase.R;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class ActividadExtraAdapter extends RecyclerView.Adapter<ActividadExtraAdapter.ViewHolder>{
    private ArrayList<ActividadExtra> list_item;
    private String inicio,fin;

    public ActividadExtraAdapter(ArrayList<ActividadExtra> listaTareas) {
        this.list_item = listaTareas;
    }

    @NonNull
    @Override
    public ActividadExtraAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_actividad_layout,parent,false);
        return new ActividadExtraAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ActividadExtraAdapter.ViewHolder holder, int position) {
        holder.bindData(list_item.get(position));
        /*
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
        */
    }

    @Override
    public int getItemCount() {
        return list_item.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView grupo,fechaIn,titulo;
        ItemAdapter adapter;
        CardView cardView;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            cardView = itemView.findViewById(R.id.cardViewActividad);
            titulo = itemView.findViewById(R.id.tituloActividad);
            grupo = itemView.findViewById(R.id.cursoActividad);
            fechaIn = itemView.findViewById(R.id.FechaInActividad);

        }
        void bindData(final ActividadExtra item) {
            //inicio = item.getFechaInicio();
            if(!list_item.isEmpty()){
                grupo.setText(item.getGrupo());
                titulo.setText(item.getTitulo());
                fechaIn.setText(item.getFechaInicio());
            }
        }
    }

    public void setList_item(ArrayList<ActividadExtra> list_item) {
        this.list_item = list_item;
        notifyDataSetChanged();
    }



    public void limpiar(){
        //this.par = false;

        notifyDataSetChanged();
    }
}
