package com.example.myf1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText txtLoginEmail, txtLoginPasswd;
    TextView txtLoginForgottenPasswd, txtLoginCreateAccount;
    Button btnLoginIniciarSesion;
    private FirebaseAuth myAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtLoginEmail=(EditText) findViewById(R.id.txtEmailAdressLogin);
        txtLoginPasswd=(EditText) findViewById(R.id.txtPasswordLogin);
        txtLoginForgottenPasswd = (TextView) findViewById(R.id.txtForgottenPasswd);
        txtLoginCreateAccount = (TextView) findViewById(R.id.txtCrearCuenta);
        btnLoginIniciarSesion = (Button) findViewById(R.id.btnLogin);


        myAuth = FirebaseAuth.getInstance();
        /*Acción de presionar en Login*/
        btnLoginIniciarSesion.setOnClickListener(view -> {
            userLogin();
        });

        /*Acción de presionar en crear cuenta*/
        txtLoginCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegisterActivity();
            }
        });

        /*acción a tomar cuando se decida resetear el correo*/
        txtLoginForgottenPasswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText resetMail = new EditText(view.getContext());
                final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(view.getContext());
                passwordResetDialog.setTitle("¿Desea reestablecer la contraseña?");
                passwordResetDialog.setMessage("Ingrese un correo para cambiar la contraseña");
                passwordResetDialog.setView(resetMail);
                passwordResetDialog.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //extraer el email y enviar el link para reestablecer
                        String mail = resetMail.getText().toString();
                        myAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(LoginActivity.this, "Se envió exitosamente el link!", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(LoginActivity.this, "Error, no se pudo enviar el link." + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
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
        String password = txtLoginPasswd.getText().toString();

        if (TextUtils.isEmpty(email)){
            txtLoginEmail.setError("Ingrese un correo");
            txtLoginEmail.requestFocus();
        }else if (TextUtils.isEmpty(password)){
            txtLoginPasswd.setError("Ingrese una contraseña");
            txtLoginPasswd.requestFocus();
        }else{
            myAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(LoginActivity.this, "Bienvenido!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent (LoginActivity.this,DashBoardActivity.class));
                    }else{
                        Log.w("TAG","Error:",task.getException());
                    }
                }
            });
        }
    }
}