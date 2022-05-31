package com.example.myf1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ListAdapterResult extends ArrayAdapter<ModeloListaResult> {
    private List<ModeloListaResult> mList;
    private Context mContext;
    private int resourceLayout;

    public ListAdapterResult(@NonNull Context context, int resource, List<ModeloListaResult> objects) {
        super(context, resource, objects);
        this.mList = objects;
        this.mContext = context;
        this.resourceLayout = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if(view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.item_row_result, null);
        }

        ModeloListaResult modelo = mList.get(position);

        ImageView imagenNat = view.findViewById(R.id.imageResultNat);
        imagenNat.setImageResource(modelo.imageNac);
        ImageView imagenFL = view.findViewById(R.id.imageResultFastLap);
        imagenFL.setImageResource(modelo.imageFastLap);
        TextView textoNombre = view.findViewById(R.id.textResultName);
        textoNombre.setText(modelo.nombre);
        TextView textoEquipo = view.findViewById(R.id.textResultTeam);
        textoEquipo.setText(modelo.equipo);
        TextView textoPos = view.findViewById(R.id.textResultPos);
        textoPos.setText(modelo.posicion);
        TextView textoPoints = view.findViewById(R.id.textResultPoints);
        textoPoints.setText(modelo.points);
        TextView textoTime = view.findViewById(R.id.textResultTime);
        textoTime.setText(modelo.time);

        return view;
    }
}
