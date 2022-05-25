package com.example.myf1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {
    Button btnRegisterFB;
    EditText txtRegisterName, txtRegisterEmail, txtRegisterPasswd, txtRegisterRepeatPasswd;
    TextView txtRegisterCancelation;
    private Spinner spinnerDriver, spinnerTeam;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txtRegisterName = (EditText)findViewById(R.id.txtNameRegister);
        txtRegisterEmail = (EditText)findViewById(R.id.txtEmailAdressRegister);
        txtRegisterPasswd = (EditText) findViewById(R.id.txtPasswdRegister);
        txtRegisterRepeatPasswd = (EditText) findViewById(R.id.txtPasswdRegister);
        txtRegisterCancelation = (TextView) findViewById(R.id.txtCancelRegister);
        spinnerDriver = (Spinner) findViewById(R.id.spinnerFavDriverRegister);
        spinnerTeam = (Spinner) findViewById(R.id.spinnerFavTeamRegister);

        /*spinner y adaptador del conductor favorito*/
        ArrayAdapter <CharSequence> driverAdapter = ArrayAdapter.createFromResource(this,R.array.drivers_array, android.R.layout.simple_spinner_item);
        driverAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDriver.setAdapter(driverAdapter);

        /*spinner y adaptador del equipo favorito*/
        ArrayAdapter <CharSequence> teamAdapter = ArrayAdapter.createFromResource(this,R.array.teams_array, android.R.layout.simple_spinner_item);
        teamAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTeam.setAdapter(teamAdapter);


    }
}