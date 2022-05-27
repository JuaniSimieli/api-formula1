package com.example.myf1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    Button btnRegisterFB;
    EditText txtRegisterName, txtRegisterEmail, txtRegisterPasswd, txtRegisterRepeatPasswd;
    TextView txtRegisterCancelation;
    private Spinner spinnerDriver, spinnerTeam;
    private String userID;
    private FirebaseAuth myAuth;
    private FirebaseFirestore db;
    private String favDriver;
    private String favTeam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btnRegisterFB = (Button) findViewById(R.id.btnFinishRegister);
        txtRegisterName = (EditText)findViewById(R.id.txtNameRegister);
        txtRegisterEmail = (EditText)findViewById(R.id.txtEmailAdressRegister);
        txtRegisterPasswd = (EditText) findViewById(R.id.txtPasswdRegister);
        txtRegisterRepeatPasswd = (EditText) findViewById(R.id.txtPasswdRepeatRegister);
        txtRegisterCancelation = (TextView) findViewById(R.id.txtCancelRegister);
        spinnerDriver = (Spinner) findViewById(R.id.spinnerFavDriverRegister);
        spinnerTeam = (Spinner) findViewById(R.id.spinnerFavTeamRegister);

        myAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        /*spinner y adaptador del conductor favorito*/
        ArrayAdapter <CharSequence> driverAdapter = ArrayAdapter.createFromResource(this,R.array.drivers_array, android.R.layout.simple_spinner_item);
        driverAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDriver.setAdapter(driverAdapter);

        /*spinner y adaptador del equipo favorito*/
        ArrayAdapter <CharSequence> teamAdapter = ArrayAdapter.createFromResource(this,R.array.teams_array, android.R.layout.simple_spinner_item);
        teamAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTeam.setAdapter(teamAdapter);

        /*Spinner Driver validation*/
        spinnerDriver.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String driver = adapterView.getItemAtPosition(i).toString();
                switch(driver) {
                    case "Alexander Albon":
                        Toast.makeText(adapterView.getContext(),"albon",Toast.LENGTH_SHORT).show();
                        favDriver="albon";
                        break;
                    case "Carlos Sainz":
                        Toast.makeText(adapterView.getContext(),"sainz",Toast.LENGTH_SHORT).show();
                        favDriver="sainz";
                        break;
                    case "Charles Leclerc":
                        Toast.makeText(adapterView.getContext(),"leclerc",Toast.LENGTH_SHORT).show();
                        favDriver="leclerc";
                        break;
                    case "Daniel Ricciardo":
                        Toast.makeText(adapterView.getContext(),"ricciardio",Toast.LENGTH_SHORT).show();
                        favDriver="ricciardio";
                        break;
                    case "Esteban Ocon":
                        Toast.makeText(adapterView.getContext(),"ocon",Toast.LENGTH_SHORT).show();
                        favDriver="ocon";
                        break;
                    case "Fernando Alonso":
                        Toast.makeText(adapterView.getContext(),"alonso",Toast.LENGTH_SHORT).show();
                        favDriver="alonso";
                        break;
                    case "George Russell":
                        Toast.makeText(adapterView.getContext(),"russell",Toast.LENGTH_SHORT).show();
                        favDriver="russell";
                        break;
                    case "Guanyu Zhou":
                        Toast.makeText(adapterView.getContext(),"zhou",Toast.LENGTH_SHORT).show();
                        favDriver="zhou";
                        break;
                    case "Kevin Magnussen":
                        Toast.makeText(adapterView.getContext(),"kevin_magnussen",Toast.LENGTH_SHORT).show();
                        favDriver="kevin_magnussen";
                        break;
                    case "Lance Stroll":
                        Toast.makeText(adapterView.getContext(),"stroll",Toast.LENGTH_SHORT).show();
                        favDriver="stroll";
                        break;
                    case "Lando Norris":
                        Toast.makeText(adapterView.getContext(),"norris",Toast.LENGTH_SHORT).show();
                        favDriver="norris";
                        break;
                    case "Lewis Hamilton":
                        Toast.makeText(adapterView.getContext(),"hamilton",Toast.LENGTH_SHORT).show();
                        favDriver="hamilton";
                        break;
                    case "Max Verstappen":
                        Toast.makeText(adapterView.getContext(),"max_verstappen",Toast.LENGTH_SHORT).show();
                        favDriver="max_verstappen";
                        break;
                    case "Mick Schumacher":
                        Toast.makeText(adapterView.getContext(),"mick_schumacher",Toast.LENGTH_SHORT).show();
                        favDriver="mick_schumacher";
                        break;
                    case "Nicholas Latifi":
                        Toast.makeText(adapterView.getContext(),"latifi",Toast.LENGTH_SHORT).show();
                        favDriver="latifi";
                        break;
                    case "Nico Hülkenberg":
                        Toast.makeText(adapterView.getContext(),"hulkenberg",Toast.LENGTH_SHORT).show();
                        favDriver="hulkenberg";
                        break;
                    case "Pierre Gasly":
                        Toast.makeText(adapterView.getContext(),"gasly",Toast.LENGTH_SHORT).show();
                        favDriver="gasly";
                        break;
                    case "Sebastian Vettel":
                        Toast.makeText(adapterView.getContext(),"vettel",Toast.LENGTH_SHORT).show();
                        favDriver="vettel";
                        break;
                    case "Sergio Pérez":
                        Toast.makeText(adapterView.getContext(),"perez",Toast.LENGTH_SHORT).show();
                        favDriver="perez";
                        break;
                    case "Valtteri Bottas":
                        Toast.makeText(adapterView.getContext(),"bottas",Toast.LENGTH_SHORT).show();
                        favDriver="bottas";
                        break;
                    case "Yuki Tsunoda":
                        Toast.makeText(adapterView.getContext(),"tsunoda",Toast.LENGTH_SHORT).show();
                        favDriver="tsunoda";
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        /*Spinner Team validation*/
        spinnerTeam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String team = adapterView.getItemAtPosition(i).toString();
                switch (team) {
                    case "Alfa Romero":
                        Toast.makeText(adapterView.getContext(), "alfa", Toast.LENGTH_SHORT).show();
                        favTeam="alfa";
                        break;
                    case "AlphaTauri":
                        Toast.makeText(adapterView.getContext(),"aphatauri", Toast.LENGTH_SHORT).show();
                        favTeam="aphatauri";
                        break;
                    case"Alpine F1 Team":
                        Toast.makeText(adapterView.getContext(), "alpine", Toast.LENGTH_SHORT).show();
                        favTeam="alpine";
                        break;
                    case "Aston Martin":
                        Toast.makeText(adapterView.getContext(), "aston_martin", Toast.LENGTH_SHORT).show();
                        favTeam="aston_martin";
                        break;
                    case "Ferrari":
                        Toast.makeText(adapterView.getContext(), "ferrari", Toast.LENGTH_SHORT).show();
                        favTeam="ferrari";
                        break;
                    case "Haas F1 Team":
                        Toast.makeText(adapterView.getContext(), "haas", Toast.LENGTH_SHORT).show();
                        favTeam="haas";
                        break;
                    case "McLaren":
                        Toast.makeText(adapterView.getContext(), "mclaren", Toast.LENGTH_SHORT).show();
                        favTeam="mclaren";
                        break;
                    case "Mercedes":
                        Toast.makeText(adapterView.getContext(), "mercedes", Toast.LENGTH_SHORT).show();
                        favTeam="mercedes";
                        break;
                    case "Red Bull":
                        Toast.makeText(adapterView.getContext(), "red_bull", Toast.LENGTH_SHORT).show();
                        favTeam="red_bull";
                        break;
                    case "Williams":
                        Toast.makeText(adapterView.getContext(), "williams", Toast.LENGTH_SHORT).show();
                        favTeam="williams";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        /*TODO: hacer una función que valide el driver, así no es tanto código en el main*/

        btnRegisterFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser();
            }
        });
        /*Cancelar registro*/
        txtRegisterCancelation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLoginActivity();
            }
        });
    }

    /*Función intent para cancelar registro*/
    public void openLoginActivity(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void createUser(){
        String name = txtRegisterName.getText().toString();
        String email = txtRegisterEmail.getText().toString();
        String password = txtRegisterPasswd.getText().toString();
        String repeat_password = txtRegisterRepeatPasswd.getText().toString();
        String driver = favDriver;
        String team = favTeam;

        if (TextUtils.isEmpty(name)){
            txtRegisterName.setError("Ingrese un nombre");
            txtRegisterName.requestFocus();
        }else if(TextUtils.isEmpty(email)){
            txtRegisterEmail.setError("Ingrese un email");
            txtRegisterEmail.requestFocus();
        }else if(TextUtils.isEmpty(password)){
            txtRegisterPasswd.setError("Ingrese una contraseña");
            txtRegisterPasswd.requestFocus();
        }else if(TextUtils.isEmpty(repeat_password)){
            txtRegisterRepeatPasswd.setError("Repita la contraseña");
            txtRegisterRepeatPasswd.requestFocus();
        }else if (TextUtils.isEmpty(driver)){
            Toast.makeText(this, "Error, seleccione un conductor", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(team)){
            Toast.makeText(this, "Error, seleccione un equipo", Toast.LENGTH_SHORT).show();
        }else if (!password.equals(repeat_password)){
            Toast.makeText(this, "Error, las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
        }else{
            myAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        userID = myAuth.getCurrentUser().getUid();
                        DocumentReference documentReference = db.collection("users").document(userID);

                        Map<String, Object> user = new HashMap<>();
                        user.put("Nombre", name);
                        user.put("Email",email);
                        user.put("Contraseña",password);
                        user.put("Conductor Favorito", driver);
                        user.put("Escudería Favorita", team);

                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d("TAG", "OnSucces: Datos registrados"+userID);
                            }
                        });
                        Toast.makeText(RegisterActivity.this, "Usuario registrado!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                    }else{
                        Toast.makeText(RegisterActivity.this, "Usuario no registrado"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
}