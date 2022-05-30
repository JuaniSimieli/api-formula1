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

/*Adaptador para vista lista de Carreras*/
public class ListAdapterRace extends ArrayAdapter<ModeloListaRace> {
    private List<ModeloListaRace> mList;
    private Context mContext;
    private int resourceLayout;

    public ListAdapterRace(@NonNull Context context, int resource, List<ModeloListaRace> objects) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.item_row_race, null);
        }

        ModeloListaRace modelo = mList.get(position);

        ImageView imagenTeam = view.findViewById(R.id.imageRaceTeam);
        imagenTeam.setImageResource(modelo.imageTeam);
        TextView textoNombre = view.findViewById(R.id.textRaceName);
        textoNombre.setText(modelo.nombre);
        TextView textoCircuito = view.findViewById(R.id.textRaceCircuit);
        textoCircuito.setText(modelo.circuito);
        TextView textoRound = view.findViewById(R.id.textRaceRound);
        textoRound.setText(modelo.round);

        return view;
    }
}
