package com.example.myf1;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myf1.model.Data;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoritosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoritosFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ImageView imgDrView, imgDrPillView, imgDrNumView, imgDrHelmView, imgTeamLogoView, imgTeamPillView, imgTeamCarView, imgGearView;
    TextView txtDrNameView, txtDrPosView, txtDrPtsView, txtDrWinsView, txtTeamPosView, txtTeamPtsView, txtTeamWinsView, txtTeamName;
    String driverId, constructorId;

    private String mParam1;
    private String mParam2;

    public FavoritosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavoritosFragment.
     */
    public static FavoritosFragment newInstance(String param1, String param2) {
        FavoritosFragment fragment = new FavoritosFragment();
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
    public void onResume() {
        super.onResume();
        driverId = GlobalClass.getPilotoFav();
        constructorId = GlobalClass.getEquipoFav();

        setPiloto(driverId);
        setEquipo(constructorId);
        asignarFotos(driverId, constructorId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_favoritos, container, false);


        imgDrView = view.findViewById(R.id.imgFavDriver);
        imgDrPillView = view.findViewById(R.id.imgFavDriverPill);
        imgDrNumView = view.findViewById(R.id.imgFavDriverNum);
        imgDrHelmView = view.findViewById(R.id.imgFavDriverHelm);
        imgTeamLogoView = view.findViewById(R.id.imgFavTeamLogo);
        imgTeamPillView = view.findViewById(R.id.imgFavTeamPill);
        imgTeamCarView = view.findViewById(R.id.imgFavTeamCar);
        txtDrNameView = view.findViewById(R.id.txtFavDriverName);
        txtDrPosView = view.findViewById(R.id.txtFavDriverPos);
        txtDrPtsView = view.findViewById(R.id.txtFavDriverPts);
        txtDrWinsView = view.findViewById(R.id.txtFavDriverWins);
        txtTeamPosView = view.findViewById(R.id.txtFavTeamPos);
        txtTeamPtsView = view.findViewById(R.id.txtFavTeamPts);
        txtTeamWinsView = view.findViewById(R.id.txtFavTeamWins);
        txtTeamName = view.findViewById(R.id.txtFavTeamName);

        imgGearView = view.findViewById(R.id.imageGearFav);


        imgGearView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(),ProfileActivity.class);
                startActivity(i);
            }
        });

        driverId = GlobalClass.getPilotoFav();
        constructorId = GlobalClass.getEquipoFav();

        setPiloto(driverId);
        setEquipo(constructorId);
        asignarFotos(driverId, constructorId);


        return view;
    }

    private void setEquipo(String constructorId) {
        Data datos = GlobalClass.getEquipos();
        List<Data.MRData.StandingsTable.StandingsLists.ConstructorStandings> listaEquipos = datos.MRData.StandingsTable.StandingsLists.get(0).ConstructorStandings;
        Data.MRData.StandingsTable.StandingsLists.ConstructorStandings equipoFav = listaEquipos.stream()
                .filter(equipo -> constructorId.equals(equipo.Constructor.constructorId))
                .findAny()
                .orElse(null);

        txtTeamPosView.setText("Posicion: " + equipoFav.position);
        txtTeamPtsView.setText("Puntos: " + equipoFav.points);
        txtTeamWinsView.setText("Carreras Ganadas: " + equipoFav.wins);
        txtTeamName.setText(equipoFav.Constructor.name);
    }

    private void setPiloto(String driverId) {
        Data datos = GlobalClass.getPilotos();
        List<Data.MRData.StandingsTable.StandingsLists.DriverStandings> listaPilotos = datos.MRData.StandingsTable.StandingsLists.get(0).DriverStandings;
        Data.MRData.StandingsTable.StandingsLists.DriverStandings pilotoFav = listaPilotos.stream()
                .filter(piloto -> driverId.equals(piloto.Driver.driverId))
                .findAny()
                .orElse(null);

        //Buscar en el loop de q equipo es el pilotoId
        String imgDrPillString = "@drawable/pill_" + pilotoFav.Constructors.get(0).constructorId;
        int imgDrPill = getResources().getIdentifier(imgDrPillString, null, getActivity().getPackageName());
        imgDrPillView.setImageResource(imgDrPill);
        txtDrNameView.setText(pilotoFav.Driver.givenName + " " + pilotoFav.Driver.familyName);
        txtDrPosView.setText("Posicion: " + pilotoFav.position);
        txtDrPtsView.setText("Puntos: " + pilotoFav.points);
        txtDrWinsView.setText("Carreras Ganadas: " + pilotoFav.wins);
    }

    private void asignarFotos(String driverId, String constructorId) {
        String imgDrString = "@drawable/driver_" + driverId;
        String imgDrNumString = "@drawable/driver_num_" + driverId;
        String imgDrHelmString = "@drawable/driver_helm_" + driverId;
        String imgTeamLogoString = "@drawable/img_" + constructorId;
        String imgTeamPillString = "@drawable/pill_" + constructorId;
        String imgTeamCarString = "@drawable/car_" + constructorId;

        int imgDr = getResources().getIdentifier(imgDrString, null, getActivity().getPackageName());
        int imgDrNum = getResources().getIdentifier(imgDrNumString, null, getActivity().getPackageName());
        int imgDrHelm = getResources().getIdentifier(imgDrHelmString, null, getActivity().getPackageName());
        int imgTeamLogo = getResources().getIdentifier(imgTeamLogoString, null, getActivity().getPackageName());
        int imgTeamPill = getResources().getIdentifier(imgTeamPillString, null, getActivity().getPackageName());
        int imgTeamCar = getResources().getIdentifier(imgTeamCarString, null, getActivity().getPackageName());

        imgDrView.setImageResource(imgDr);
        imgDrNumView.setImageResource(imgDrNum);
        imgDrHelmView.setImageResource(imgDrHelm);
        imgTeamLogoView.setImageResource(imgTeamLogo);
        imgTeamPillView.setImageResource(imgTeamPill);
        imgTeamCarView.setImageResource(imgTeamCar);
    }
}