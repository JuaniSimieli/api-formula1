package com.example.myf1;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.myf1.model.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PilotosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PilotosFragment extends Fragment implements AdapterView.OnItemClickListener  {


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ListView mListView;
    private List<ModeloListaDriver> mLista = new ArrayList<>();
    ListAdapterDriver mAdapter;


    private String mParam1;
    private String mParam2;

    public PilotosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PilotosFragment.
     */

    public static PilotosFragment newInstance(String param1, String param2) {
        PilotosFragment fragment = new PilotosFragment();
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
        View view = inflater.inflate(R.layout.fragment_pilotos, container, false);

        mListView = view.findViewById(R.id.listViewPilotos);
        mListView.setOnItemClickListener(this);
        setPilotos();

        return view;
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(getActivity(),PilotoDetalleActivity.class);
        ModeloListaDriver piloto = mLista.get(i);
        intent.putExtra("nombre", piloto.pilotoDetalle.Driver.givenName + " " + piloto.pilotoDetalle.Driver.familyName);
        intent.putExtra("wins", piloto.pilotoDetalle.wins);
        intent.putExtra("points", piloto.pilotoDetalle.points);
        intent.putExtra("number", piloto.pilotoDetalle.Driver.permanentNumber);
        intent.putExtra("teamName", piloto.pilotoDetalle.Constructors.get(0).name);
        intent.putExtra("driverId", piloto.pilotoDetalle.Driver.driverId);
        intent.putExtra("constrId", piloto.pilotoDetalle.Constructors.get(0).constructorId);
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
    }


    private void setPilotos() {
        Data datos = GlobalClass.getPilotos();
        List<Data.MRData.StandingsTable.StandingsLists.DriverStandings> listPilotos = datos.MRData.StandingsTable.StandingsLists.get(0).DriverStandings;

        for(Data.MRData.StandingsTable.StandingsLists.DriverStandings piloto: listPilotos){
            //asigno las imagenes de drawable del equipo y de la nacionalidad
            String imagenPillString = "@drawable/pill_" + piloto.Constructors.get(0).constructorId;
            int imagenPill = getResources().getIdentifier(imagenPillString, null, getActivity().getPackageName());
            String imagenNacString = "@drawable/nat_" + piloto.Driver.nationality.toLowerCase();
            int imagenNac = getResources().getIdentifier(imagenNacString, null, getActivity().getPackageName());

            mLista.add(new ModeloListaDriver(piloto.Driver.givenName + " " + piloto.Driver.familyName,
                    piloto.points, piloto.position, imagenPill, imagenNac, piloto));
        }

        mAdapter = new ListAdapterDriver(getActivity().getApplicationContext(), R.layout.item_row_driver, mLista);
        mListView.setAdapter(mAdapter);
    }
}