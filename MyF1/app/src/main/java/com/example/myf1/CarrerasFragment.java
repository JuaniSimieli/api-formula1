package com.example.myf1;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myf1.model.Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CarrerasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CarrerasFragment extends Fragment {


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ListView mListView;
    private List<ModeloListaRace> mLista = new ArrayList<>();
    ListAdapterRace mAdapter;


    private String mParam1;
    private String mParam2;

    public CarrerasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CarrerasFragment.
     */

    public static CarrerasFragment newInstance(String param1, String param2) {
        CarrerasFragment fragment = new CarrerasFragment();
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
        View view = inflater.inflate(R.layout.fragment_carreras, container, false);
        mListView = view.findViewById(R.id.listViewCarreras);
        setCarreras();

        //TODO: NO ANDA
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                ModeloListaRace carrera = mLista.get(position);

                //creo una alerta

                //origin
                new AlertDialog.Builder(getActivity())
                        .setTitle("Agregar Alerta")
                        .setMessage("¿Estas seguro que deseas agregar una alerta para " + carrera.nombre + "?")
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Clickeo en aceptar
                                String date = carrera.date + carrera.time;
                                try {
                                    Date raceDate=new SimpleDateFormat("yyyy-MM-ddHH:mm:ssX").parse(date);
                                    //raceDate convertido al horario del telefono
                                    //agrego al calendario con intent
                                    Intent intent = new Intent(Intent.ACTION_INSERT);
                                    intent.setData(CalendarContract.Events.CONTENT_URI);
                                    intent.putExtra(CalendarContract.Events.ALL_DAY, "false");
                                    intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, raceDate.getTime());
                                    intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, raceDate.getTime()+(3600000*2));
                                    intent.putExtra(CalendarContract.Events.TITLE, carrera.nombre);
                                    intent.putExtra(CalendarContract.Events.EVENT_LOCATION, carrera.circuito);
                                    intent.putExtra(CalendarContract.Events.HAS_ALARM, 1);
                                    if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(getActivity(), "No se pudo agregar al calendario", Toast.LENGTH_LONG).show();
                                    }
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //clickeo en cancelar
                            }
                        }).show();

            }
        });

        return view;
    }

    private void setCarreras() {
        Data data = GlobalClass.getCarreras();
        List<Data.MRData.RaceTable.Race> datos = data.MRData.RaceTable.Races;

        for(Data.MRData.RaceTable.Race carrera: datos){
            //asigno las imagenes del pais
            String imagenNationString = "@drawable/flag_" + carrera.Circuit.circuitId.toLowerCase();
            int imagenNation = getResources().getIdentifier(imagenNationString, null, getActivity().getPackageName());

            //comparo las fechas
            Date now = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String formattedAhora = df.format(now);
            String apiDate = carrera.date;

            if(formattedAhora.equals(apiDate)){
                //la carrera es hoy (puede no haber terminado, asi q la agrego)
                mLista.add(new ModeloListaRace(carrera.raceName, carrera.Circuit.circuitName, carrera.round, imagenNation, carrera.date, carrera.time));

            } else {
                try {
                    Date raceDate=new SimpleDateFormat("yyyy-MM-dd").parse(apiDate);
                    if(now.compareTo(raceDate) > 0){
                        //La carrera ya paso (va en la activity de resultados)
                    } else {
                        //La carrera esta por venir
                        mLista.add(new ModeloListaRace(carrera.raceName, carrera.Circuit.circuitName, carrera.round, imagenNation, carrera.date, carrera.time));
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        mAdapter = new ListAdapterRace(getActivity().getApplicationContext(), R.layout.item_row_race, mLista);
        mListView.setAdapter(mAdapter);
    }
}