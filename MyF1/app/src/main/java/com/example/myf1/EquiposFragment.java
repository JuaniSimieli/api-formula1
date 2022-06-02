package com.example.myf1;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.myf1.model.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EquiposFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EquiposFragment extends Fragment {


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ListView mListView;
    private List<ModeloListaDriver> mLista = new ArrayList<>();
    ListAdapterDriver mAdapter;

    private String mParam1;
    private String mParam2;

    public EquiposFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EquiposFragment.
     */
    public static EquiposFragment newInstance(String param1, String param2) {
        EquiposFragment fragment = new EquiposFragment();
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
        View view = inflater.inflate(R.layout.fragment_equipos, container, false);

        mListView = view.findViewById(R.id.listViewEquipos);
        setEquipos();

        return view;
    }

    private void setEquipos() {
        Data data = GlobalClass.getEquipos();
        List<Data.MRData.StandingsTable.StandingsLists.ConstructorStandings> datos = data.MRData.StandingsTable.StandingsLists.get(0).ConstructorStandings;

        for(Data.MRData.StandingsTable.StandingsLists.ConstructorStandings equipo: datos){
            //asigno las imagenes de drawable del equipo (pill y square)
            String imagenPillString = "@drawable/pill_" + equipo.Constructor.constructorId;
            int imagenPill = getResources().getIdentifier(imagenPillString, null, getActivity().getPackageName());
            String imagenPillSquare = "@drawable/img_" + equipo.Constructor.constructorId;
            int imagenSquare = getResources().getIdentifier(imagenPillSquare, null, getActivity().getPackageName());

            mLista.add(new ModeloListaDriver(equipo.Constructor.name,
                    equipo.points, equipo.position, imagenPill, imagenSquare));
        }

        mAdapter = new ListAdapterDriver(getActivity().getApplicationContext(), R.layout.item_row_driver, mLista);
        mListView.setAdapter(mAdapter);

    }
}