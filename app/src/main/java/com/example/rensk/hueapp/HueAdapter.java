package com.example.rensk.hueapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class HueAdapter extends RecyclerView.Adapter<HueAdapter.ViewHolder> {
    private static ClickListener clickListener;
    private List<Light> lights;


    public HueAdapter (List<Light> lights){
        this.lights = lights;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);


        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Light light = lights.get(position);
        holder.name.setText(light.getLightnum());
        if (light.getAan().equals("true")) {
            holder.lamp.setImageResource(R.drawable.lampaan);
        }else{
            holder.lamp.setImageResource(R.drawable.lampuit);
        }
    }

    @Override
    public int getItemCount() {
       return lights.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        TextView name;
        ImageView lamp;

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
            name = view.findViewById(R.id.txt_name_id);
            lamp = view.findViewById(R.id.lamp_img);
        }


        @Override
        public void onClick(View view) {
            clickListener.onItemClick(getAdapterPosition(), view);
        }

        @Override
        public boolean onLongClick(View view) {
            clickListener.onItemLongClick(getAdapterPosition(),view);
            return false;
        }
    }
    public void setOnItemClickListener(ClickListener clickListener) {
        HueAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }

}


