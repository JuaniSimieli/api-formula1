package com.example.myf1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
        spinnerDriver.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String driver = adapterView.getItemAtPosition(i).toString();
                switch(driver) {
                    case "Alexander Albon":
                        Toast.makeText(adapterView.getContext(),"albon",Toast.LENGTH_SHORT).show();
                        break;
                    case "Carlos Sainz":
                        Toast.makeText(adapterView.getContext(),"sainz",Toast.LENGTH_SHORT).show();
                        break;
                    case "Charles Leclerc":
                        Toast.makeText(adapterView.getContext(),"leclerc",Toast.LENGTH_SHORT).show();
                        break;
                    case "Daniel Ricciardo":
                        Toast.makeText(adapterView.getContext(),"ricciardio",Toast.LENGTH_SHORT).show();
                        break;
                    case "Esteban Ocon":
                        Toast.makeText(adapterView.getContext(),"ocon",Toast.LENGTH_SHORT).show();
                        break;
                    case "Fernando Alonso":
                        Toast.makeText(adapterView.getContext(),"alonso",Toast.LENGTH_SHORT).show();
                        break;
                    case "George Russell":
                        Toast.makeText(adapterView.getContext(),"russell",Toast.LENGTH_SHORT).show();
                        break;
                    case "Guanyu Zhou":
                        Toast.makeText(adapterView.getContext(),"zhou",Toast.LENGTH_SHORT).show();
                        break;
                    case "Kevin Magnussen":
                        Toast.makeText(adapterView.getContext(),"kevin_magussen",Toast.LENGTH_SHORT).show();
                        break;
                    case "Lance Stroll":
                        Toast.makeText(adapterView.getContext(),"stroll",Toast.LENGTH_SHORT).show();
                        break;
                    case "Lando Norris":
                        Toast.makeText(adapterView.getContext(),"norris",Toast.LENGTH_SHORT).show();
                        break;
                    case "Lewis Hamilton":
                        Toast.makeText(adapterView.getContext(),"hamilton",Toast.LENGTH_SHORT).show();
                        break;
                    case "Max Verstappen":
                        Toast.makeText(adapterView.getContext(),"max_verstappen",Toast.LENGTH_SHORT).show();
                        break;
                    case "Mick Schumacher":
                        Toast.makeText(adapterView.getContext(),"mick_schumacher",Toast.LENGTH_SHORT).show();
                        break;
                    case "Nicholas Latifi":
                        Toast.makeText(adapterView.getContext(),"latifi",Toast.LENGTH_SHORT).show();
                        break;
                    case "Nico Hülkenberg":
                        Toast.makeText(adapterView.getContext(),"hulkenberg",Toast.LENGTH_SHORT).show();
                        break;
                    case "Pierre Gasly":
                        Toast.makeText(adapterView.getContext(),"gasly",Toast.LENGTH_SHORT).show();
                        break;
                    case "Sebastian Vettel":
                        Toast.makeText(adapterView.getContext(),"vettel",Toast.LENGTH_SHORT).show();
                        break;
                    case "Sergio Pérez":
                        Toast.makeText(adapterView.getContext(),"perez",Toast.LENGTH_SHORT).show();
                        break;
                    case "Valtteri Bottas":
                        Toast.makeText(adapterView.getContext(),"bottas",Toast.LENGTH_SHORT).show();
                        break;
                    case "Yuki Tsunoda":
                        Toast.makeText(adapterView.getContext(),"tsunoda",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spinnerTeam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String team = adapterView.getItemAtPosition(i).toString();
                switch (team) {
                    case "Alfa Romero":
                        Toast.makeText(adapterView.getContext(), "alfa", Toast.LENGTH_SHORT).show();
                        break;
                    case "AlphaTauri":
                        Toast.makeText(adapterView.getContext(),"aphatauri", Toast.LENGTH_SHORT).show();
                        break;
                    case"Alpine F1 Team":
                        Toast.makeText(adapterView.getContext(), "alpine", Toast.LENGTH_SHORT).show();
                        break;
                    case "Aston Martin":
                        Toast.makeText(adapterView.getContext(), "aston_martin", Toast.LENGTH_SHORT).show();
                        break;
                    case "Ferrari":
                        Toast.makeText(adapterView.getContext(), "ferrari", Toast.LENGTH_SHORT).show();
                        break;
                    case "Haas F1 Team":
                        Toast.makeText(adapterView.getContext(), "haas", Toast.LENGTH_SHORT).show();
                        break;
                    case "McLaren":
                        Toast.makeText(adapterView.getContext(), "mclaren", Toast.LENGTH_SHORT).show();
                        break;
                    case "Mercedes":
                        Toast.makeText(adapterView.getContext(), "mercedes", Toast.LENGTH_SHORT).show();
                        break;
                    case "Red Bull":
                        Toast.makeText(adapterView.getContext(), "red_bull", Toast.LENGTH_SHORT).show();
                        break;
                    case "Williams":
                        Toast.makeText(adapterView.getContext(), "williams", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        /*TODO: hacer una función que valide el driver, así no es tanto código en el main*/
    }
}