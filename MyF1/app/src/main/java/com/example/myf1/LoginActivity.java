package com.example.myf1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    EditText txtLoginEmail;
    TextInputEditText txtLoginPasswd;
    TextView txtLoginForgottenPasswd, txtLoginCreateAccount;
    Button btnLoginIniciarSesion;
    private FirebaseAuth myAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtLoginEmail= findViewById(R.id.txtEmailAdressLogin);
        txtLoginPasswd= findViewById(R.id.txtPasswordLogin);
        txtLoginForgottenPasswd = findViewById(R.id.txtForgottenPasswd);
        txtLoginCreateAccount = findViewById(R.id.txtCrearCuenta);
        btnLoginIniciarSesion = findViewById(R.id.btnLogin);


        myAuth = FirebaseAuth.getInstance();
        /*Acción de presionar en Login*/
        btnLoginIniciarSesion.setOnClickListener(view -> userLogin());

        /*Acción de presionar en crear cuenta*/
        txtLoginCreateAccount.setOnClickListener(view -> openRegisterActivity());

        /*acción a tomar cuando se decida resetear el correo*/
        txtLoginForgottenPasswd.setOnClickListener(view -> {
            final EditText resetMail = new EditText(view.getContext());
            final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(view.getContext());
            passwordResetDialog.setTitle("¿Desea reestablecer la contraseña?");
            passwordResetDialog.setMessage("Ingrese un correo para cambiar la contraseña");
            passwordResetDialog.setView(resetMail);
            passwordResetDialog.setPositiveButton("Sí", (dialogInterface, i) -> {
                //extraer el email y enviar el link para reestablecer
                String mail = resetMail.getText().toString();
                myAuth.sendPasswordResetEmail(mail).addOnSuccessListener(unused -> Toast.makeText(LoginActivity.this, "Se envió exitosamente el link!", Toast.LENGTH_SHORT).show()).addOnFailureListener(e -> Toast.makeText(LoginActivity.this, "Error, no se pudo enviar el link." + e.getMessage(), Toast.LENGTH_SHORT).show());
            });
            passwordResetDialog.setNegativeButton("No", (dialogInterface, i) -> {
                //do something
            });
            passwordResetDialog.create().show();
        });
    }

    /*Función de Intent para Registrar*/
    public void openRegisterActivity(){
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }

    /*Función de Login*/
    public void userLogin(){
        String email = txtLoginEmail.getText().toString();
        String password = Objects.requireNonNull(txtLoginPasswd.getText()).toString();

        if (TextUtils.isEmpty(email)){
            txtLoginEmail.setError("Ingrese un correo");
            txtLoginEmail.requestFocus();
        }else if (TextUtils.isEmpty(password)){
            txtLoginPasswd.setError("Ingrese una contraseña");
            txtLoginPasswd.requestFocus();
        }else{
            myAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "Bienvenido!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent (LoginActivity.this,MainActivity.class));
                }else{
                    Toast.makeText(this, "Usuario o contraseña incorrecto", Toast.LENGTH_SHORT).show();
                    Log.w("TAG","Error:",task.getException());
                }
            });
        }
    }
}