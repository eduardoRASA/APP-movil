package com.example.movil.models;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.movil.R;

import java.util.List;


public class ListDetalleAdapter extends ArrayAdapter<PostDetalle>{

    private Context context;
    private List<PostDetalle> detalleArrayList = null;
    public ListDetalleAdapter(Context context, List<PostDetalle> detalleArrayList){
        super(context, R.layout.list_item_detalle, detalleArrayList);
        this.context = context;
        this.detalleArrayList = detalleArrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        PostDetalle postDetalle = detalleArrayList.get(position);

        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_detalle, null);
        }
        TextView capacidad = convertView.findViewById(R.id.textViewCapacidadItemDetalle);

        capacidad.setText(Float.toString(postDetalle.getRegistro()));
        for(Drawable drawable : capacidad.getCompoundDrawables()){
            if(drawable != null){
                if(postDetalle.getRegistro() <= 20 ){
                    drawable.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(capacidad.getContext(), R.color.red), PorterDuff.Mode.SRC_IN));
                }else if(postDetalle.getRegistro() > 20 && postDetalle.getRegistro() < 80){
                    drawable.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(capacidad.getContext(), R.color.yellow), PorterDuff.Mode.SRC_IN));
                }else if(postDetalle.getRegistro() >= 80){
                    drawable.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(capacidad.getContext(), R.color.green), PorterDuff.Mode.SRC_IN));
                }
            }
        }

        return convertView;
    }
}