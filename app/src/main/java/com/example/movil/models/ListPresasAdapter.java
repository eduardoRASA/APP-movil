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
import androidx.core.content.ContextCompat;
import com.example.movil.R;
import java.util.List;

public class ListPresasAdapter extends ArrayAdapter<PostPresas> {
    private Context context;
    private List<PostPresas> presasArrayList = null;

    public ListPresasAdapter(Context context2, List<PostPresas> presasArrayList2) {
        super(context2, R.layout.list_item, presasArrayList2);
        this.context = context2;
        this.presasArrayList = presasArrayList2;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        PostPresas postPresas = this.presasArrayList.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(this.context).inflate(R.layout.list_item, (ViewGroup) null);
        }
        TextView capacidadPresa = (TextView) convertView.findViewById(R.id.textViewCapacidadItem);
        ((TextView) convertView.findViewById(R.id.textViewId)).setText(Integer.toString(postPresas.getId_presa()));
        ((TextView) convertView.findViewById(R.id.textViewNombreItem)).setText(this.presasArrayList.get(position).nombre);
        capacidadPresa.setText(Float.toString(postPresas.getRegistro()));
        for (Drawable drawable : capacidadPresa.getCompoundDrawables()) {
            if (drawable != null) {
                if (postPresas.getRegistro() <= 20) {
                    drawable.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(capacidadPresa.getContext(), R.color.red), PorterDuff.Mode.SRC_IN));
                } else if (postPresas.getRegistro() > 20 && postPresas.getRegistro() < 80) {
                    drawable.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(capacidadPresa.getContext(), R.color.yellow), PorterDuff.Mode.SRC_IN));
                } else if (postPresas.getRegistro() >= 80) {
                    drawable.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(capacidadPresa.getContext(), R.color.green), PorterDuff.Mode.SRC_IN));
                }
            }
        }
        return convertView;
    }
}
