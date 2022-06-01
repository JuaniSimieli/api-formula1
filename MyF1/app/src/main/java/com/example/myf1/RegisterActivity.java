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
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    Button btnRegisterFB;
    EditText txtRegisterName, txtRegisterEmail;
    TextInputEditText txtRegisterPasswd, txtRegisterRepeatPasswd;
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
        txtRegisterPasswd = (TextInputEditText) findViewById(R.id.txtPasswordRegister);
        txtRegisterRepeatPasswd = (TextInputEditText) findViewById(R.id.txtRepeatPasswordRegister);
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
                        favDriver="albon";
                        break;
                    case "Carlos Sainz":
                        favDriver="sainz";
                        break;
                    case "Charles Leclerc":
                        favDriver="leclerc";
                        break;
                    case "Daniel Ricciardo":
                        favDriver="ricciardo";
                        break;
                    case "Esteban Ocon":
                        favDriver="ocon";
                        break;
                    case "Fernando Alonso":
                        favDriver="alonso";
                        break;
                    case "George Russell":
                        favDriver="russell";
                        break;
                    case "Guanyu Zhou":
                        favDriver="zhou";
                        break;
                    case "Kevin Magnussen":
                        favDriver="kevin_magnussen";
                        break;
                    case "Lance Stroll":
                        favDriver="stroll";
                        break;
                    case "Lando Norris":
                        favDriver="norris";
                        break;
                    case "Lewis Hamilton":
                        favDriver="hamilton";
                        break;
                    case "Max Verstappen":
                        favDriver="max_verstappen";
                        break;
                    case "Mick Schumacher":
                        favDriver="mick_schumacher";
                        break;
                    case "Nicholas Latifi":
                        favDriver="latifi";
                        break;
                    case "Nico Hülkenberg":
                        favDriver="hulkenberg";
                        break;
                    case "Pierre Gasly":
                        favDriver="gasly";
                        break;
                    case "Sebastian Vettel":
                        favDriver="vettel";
                        break;
                    case "Sergio Pérez":
                        favDriver="perez";
                        break;
                    case "Valtteri Bottas":
                        favDriver="bottas";
                        break;
                    case "Yuki Tsunoda":
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
                        favTeam="alfa";
                        break;
                    case "AlphaTauri":
                        favTeam="alphatauri";
                        break;
                    case"Alpine F1 Team":
                        favTeam="alpine";
                        break;
                    case "Aston Martin":
                        favTeam="aston_martin";
                        break;
                    case "Ferrari":
                        favTeam="ferrari";
                        break;
                    case "Haas F1 Team":
                        favTeam="haas";
                        break;
                    case "McLaren":
                        favTeam="mclaren";
                        break;
                    case "Mercedes":
                        favTeam="mercedes";
                        break;
                    case "Red Bull":
                        favTeam="red_bull";
                        break;
                    case "Williams":
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
        Intent goToLogin = new Intent(this, LoginActivity.class);
        startActivity(goToLogin);
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