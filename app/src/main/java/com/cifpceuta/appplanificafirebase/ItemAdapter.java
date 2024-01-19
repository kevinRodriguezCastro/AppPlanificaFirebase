package com.cifpceuta.appplanificafirebase;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    private ArrayList<Tarea> list_item;
    private int par;

    public ItemAdapter(ArrayList<Tarea> listaTareas) {
        this.list_item = list_item;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);
        return new ItemAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

       // holder.bindData(list_item.get(position));


        holder.cardView.setCardBackgroundColor(Color.WHITE);
        if (par == 2){
            if (position%2 == 0){
                holder.cardView.setCardBackgroundColor(Color.GRAY);
            }
        }else if (par == 1){
            if(position%2 != 0){
                holder.cardView.setCardBackgroundColor(Color.GREEN);
            }
        }

    }

    @Override
    public int getItemCount() {
        return list_item.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView modulo,curso,fechaIn,fechaFin,descripcion;
        ItemAdapter adapter;
        CardView cardView;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            modulo = itemView.findViewById(R.id.ilModulo);
            curso = itemView.findViewById(R.id.ilCurso);
            descripcion = itemView.findViewById(R.id.ilDescripcion);
            fechaIn = itemView.findViewById(R.id.ilFechaIn);
            fechaFin = itemView.findViewById(R.id.ilFechaFin);


        }
        void bindData(final String item) {
            modulo.setText(item);
        }
    }

    public void setList_item(ArrayList<Tarea> list_item) {
        this.list_item = list_item;
        notifyDataSetChanged();
    }

    public void setPar(int par) {
        if (par != this.par){
            this.par = par;
        }

        notifyDataSetChanged();
    }

    public void limpiar(){
        //this.par = false;

        notifyDataSetChanged();
    }
}