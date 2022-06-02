package com.example.myf1;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.myf1.model.Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ResultadosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResultadosFragment extends Fragment implements AdapterView.OnItemClickListener {


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ListView mListView;
    private List<ModeloListaRace> mLista = new ArrayList<>();
    ListAdapterRace mAdapter;


    private String mParam1;
    private String mParam2;

    public ResultadosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ResultadosFragment.
     */

    public static ResultadosFragment newInstance(String param1, String param2) {
        ResultadosFragment fragment = new ResultadosFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_resultados, container, false);

        mListView = view.findViewById(R.id.listViewResultados);
        mListView.setOnItemClickListener(this);

        setCarreras();

        return view;
    }

    private void setCarreras() {
        Data data = GlobalClass.getCarreras();
        List<Data.MRData.RaceTable.Race> datos = data.MRData.RaceTable.Races;

        for(Data.MRData.RaceTable.Race carrera: datos){
            /*Asigno las imagenes del pais*/
            String imagenNationString = "@drawable/flag_" + carrera.Circuit.circuitId.toLowerCase();
            int imagenNation = getResources().getIdentifier(imagenNationString, null, getActivity().getPackageName());

            /*Comparo las fechas*/
            Date now = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String formattedAhora = df.format(now);
            String apiDate = carrera.date;

            if(formattedAhora.equals(apiDate)){
                /*la carrera es hoy (pero puede no haber terminado, por ende no tener resultados)*/
            } else {
                try {
                    Date raceDate=new SimpleDateFormat("yyyy-MM-dd").parse(apiDate);
                    if(now.compareTo(raceDate) > 0){
                        /*La carrera ya paso asi q la agrego*/
                        mLista.add(new ModeloListaRace(carrera.raceName, carrera.Circuit.circuitName, carrera.round, imagenNation, carrera.date, carrera.time));
                    } else {
                        /*La carrera todavia no paso*/
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

        Collections.reverse(mLista); //doy vuelta la lista para q aparezcan los mas recientes primero
        mAdapter = new ListAdapterRace(getActivity().getApplicationContext(), R.layout.item_row_race, mLista);
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        /*Le paso el numero de ronda al intent*/
        Intent intent = new Intent(getActivity(),ResultadosDetalleActivity.class);
        String round = mLista.get(i).round;
        intent.putExtra("round", round);
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}