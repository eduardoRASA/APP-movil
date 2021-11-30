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


public class ListTanquesAdapter extends ArrayAdapter<PostTanques>{

    private Context context;
    private List<PostTanques> tanquesArrayList = null;
    public ListTanquesAdapter(Context context, List<PostTanques> tanquesArrayList){
        super(context, R.layout.list_item, tanquesArrayList);
        this.context = context;
        this.tanquesArrayList = tanquesArrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        PostTanques postTanques = tanquesArrayList.get(position);

        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, null);
        }
        TextView nombre = convertView.findViewById(R.id.textViewNombreItem);
        TextView capacidad = convertView.findViewById(R.id.textViewCapacidadItem);
        TextView id_tanque = convertView.findViewById(R.id.textViewId);

//        if(postPresas.estado == 1){
//            capacidadPresa.(R.color.black);
//        }
        id_tanque.setText(Integer.toString(postTanques.getId_tanque()));
        nombre.setText(tanquesArrayList.get(position).colonia);
        capacidad.setText(Float.toString(postTanques.getRegistro()));
        for(Drawable drawable : capacidad.getCompoundDrawables()){
            if(drawable != null){
                if(postTanques.getRegistro() <= 20 ){
                    drawable.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(capacidad.getContext(), R.color.red), PorterDuff.Mode.SRC_IN));
                }else if(postTanques.getRegistro() > 20 && postTanques.getRegistro() < 80){
                    drawable.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(capacidad.getContext(), R.color.yellow), PorterDuff.Mode.SRC_IN));
                }else if(postTanques.getRegistro() >= 80){
                    drawable.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(capacidad.getContext(), R.color.green), PorterDuff.Mode.SRC_IN));
                }
            }
        }

        return convertView;
    }
}
