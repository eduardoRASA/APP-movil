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


public class ListDepositosAdapter extends ArrayAdapter<PostDepositos>{

    private Context context;
    private List<PostDepositos> depositosArrayList = null;
    public ListDepositosAdapter(Context context, List<PostDepositos> depositosArrayList){
        super(context, R.layout.list_item, depositosArrayList);
        this.context = context;
        this.depositosArrayList = depositosArrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        PostDepositos postDepositos = depositosArrayList.get(position);

        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, null);
        }
        TextView nombreDeposito = convertView.findViewById(R.id.textViewNombreItem);
        TextView capacidadDeposito = convertView.findViewById(R.id.textViewCapacidadItem);
        TextView id_deposito = convertView.findViewById(R.id.textViewId);

        id_deposito.setText(Integer.toString(postDepositos.getId_deposito()));
        nombreDeposito.setText(depositosArrayList.get(position).nombre);
        capacidadDeposito.setText(Float.toString(postDepositos.getRegistro()));
        for(Drawable drawable : capacidadDeposito.getCompoundDrawables()){
            if(drawable != null){
                if(postDepositos.getRegistro() <= 20){
                    drawable.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(capacidadDeposito.getContext(), R.color.red), PorterDuff.Mode.SRC_IN));
                }else if(postDepositos.getRegistro() > 20 && postDepositos.getRegistro() < 80){
                    drawable.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(capacidadDeposito.getContext(), R.color.yellow), PorterDuff.Mode.SRC_IN));
                }else if(postDepositos.getRegistro() >= 80){
                    drawable.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(capacidadDeposito.getContext(), R.color.green), PorterDuff.Mode.SRC_IN));
                }
            }
        }

        return convertView;
    }
}
