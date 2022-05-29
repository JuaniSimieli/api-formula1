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

/*Adaptador para vista lista de Pilotos y Equipos*/
public class ListAdapterDriver extends ArrayAdapter<ModeloListaDriver> {
    private List<ModeloListaDriver> mList;
    private Context mContext;
    private int resourceLayout;

    public ListAdapterDriver(@NonNull Context context, int resource, List<ModeloListaDriver> objects) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.item_row_driver, null);
        }

        ModeloListaDriver modelo = mList.get(position);

        ImageView imagenNac = view.findViewById(R.id.imageDriverNat);
        imagenNac.setImageResource(modelo.imageNac);
        ImageView imagenTeam = view.findViewById(R.id.imageDriverTeam);
        imagenTeam.setImageResource(modelo.imageTeam);
        TextView textoNombre = view.findViewById(R.id.textDriverName);
        textoNombre.setText(modelo.nombre);
        TextView textoPuntos = view.findViewById(R.id.textDriverPoints);
        textoPuntos.setText(modelo.puntos);
        TextView textoPosicion = view.findViewById(R.id.textDriverPos);
        textoPosicion.setText(modelo.posicion);

        return view;
    }
}
