package com.example.myf1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class EquipoDetalleActivity extends AppCompatActivity {

    private ImageView imgLogo, imgNat, imgCar;
    private TextView txtNombre, txtPos, txtPts, txtWins, txtSede;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipo_detalle);

        imgLogo = findViewById(R.id.imageEqDetLogo);
        imgNat = findViewById(R.id.imageEqDetNat);
        imgCar = findViewById(R.id.imageEqDetCar);
        txtNombre = findViewById(R.id.textEqDetNombre);
        txtPos = findViewById(R.id.textEqDetPos);
        txtPts = findViewById(R.id.textEqDetPts);
        txtWins = findViewById(R.id.textEqDetWins);
        txtSede = findViewById(R.id.textEqDetSede);

        setValues();
    }

    private void setValues() {
        Intent intent = getIntent();
        String position = intent.getStringExtra("position");
        String wins = intent.getStringExtra("wins");
        String points = intent.getStringExtra("points");
        String name = intent.getStringExtra("name");
        String nationality = intent.getStringExtra("nationality");
        String constrId = intent.getStringExtra("constrId");

        String imagenLogoString = "@drawable/img_" + constrId;
        int imagenLogo = getResources().getIdentifier(imagenLogoString, null, getPackageName());
        imgLogo.setImageResource(imagenLogo);

        String imagenNatString = "@drawable/nat_" + nationality;
        int imagenNat = getResources().getIdentifier(imagenNatString, null, getPackageName());
        imgNat.setImageResource(imagenNat);

        String imagenCarString = "@drawable/car_" + constrId;
        int imagenCar = getResources().getIdentifier(imagenCarString, null, getPackageName());
        imgCar.setImageResource(imagenCar);

        txtWins.setText("Carreras ganadas: " + wins);
        txtPts.setText("Puntos: " + points);
        txtPos.setText("Posicion: " + position);
        txtNombre.setText(name);
        txtSede.setText("Sede en:");
    }
}