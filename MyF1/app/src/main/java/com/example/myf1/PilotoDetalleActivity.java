package com.example.myf1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class PilotoDetalleActivity extends AppCompatActivity {
    ImageView imgPiloto, imgHelm, imgNum, imgTeam, imgCar;
    TextView txtWins, txtPts, txtNum, txtNombre, txtEquipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piloto_detalle);

        imgPiloto = findViewById(R.id.imagePilotoDetalle);
        imgHelm = findViewById(R.id.imagePilotoDetalleHelm);
        imgNum= findViewById(R.id.imagePilotoDetalleNum);
        imgTeam = findViewById(R.id.imagePilotoDetalleTeamLogo);
        imgCar = findViewById(R.id.imagePilotoDetalleTeamCar);
        txtWins = findViewById(R.id.textPilotoDetalleWins);
        txtPts = findViewById(R.id.textPilotoDetallePts);
        txtNum = findViewById(R.id.textPilotoDetalleNumb);
        txtNombre = findViewById(R.id.textPilotoDetalleNombre);
        txtEquipo = findViewById(R.id.textPilotoDetalleEquipo);

        setValues();
    }

    private void setValues() {
        Intent intent = getIntent();
        String nombre = intent.getStringExtra("nombre");
        String wins = intent.getStringExtra("wins");
        String number = intent.getStringExtra("number");
        String points = intent.getStringExtra("points");
        String teamName = intent.getStringExtra("teamName");
        String driverId = intent.getStringExtra("driverId");
        String constrId = intent.getStringExtra("constrId");

        String imagenPilotoString = "@drawable/driver_" + driverId;
        int imagenPiloto = getResources().getIdentifier(imagenPilotoString, null, getPackageName());
        imgPiloto.setImageResource(imagenPiloto);

        String imagenHelmString = "@drawable/driver_helm_" + driverId;
        int imagenHelm = getResources().getIdentifier(imagenHelmString, null, getPackageName());
        imgHelm.setImageResource(imagenHelm);

        String imagenNumString = "@drawable/driver_num_" + driverId;
        int imagenNum = getResources().getIdentifier(imagenNumString, null, getPackageName());
        imgNum.setImageResource(imagenNum);

        String imagenTeamString = "@drawable/img_" + constrId;
        int imagenTeam = getResources().getIdentifier(imagenTeamString, null, getPackageName());
        imgTeam.setImageResource(imagenTeam);

        String imagenCarString = "@drawable/car_" + constrId;
        int imagenCar = getResources().getIdentifier(imagenCarString, null, getPackageName());
        imgCar.setImageResource(imagenCar);

        txtWins.setText("Carreras ganadas: " + wins);
        txtPts.setText("Puntos: " + points);
        txtNum.setText("Numero: " + number);
        txtNombre.setText(nombre);
        txtEquipo.setText("Piloto de " + teamName);
    }
}