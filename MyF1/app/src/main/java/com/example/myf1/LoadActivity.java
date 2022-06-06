package com.example.myf1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class LoadActivity extends AppCompatActivity {
    private FirebaseAuth myAuth;
    private FirebaseFirestore db;
    private String userID;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        myAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        user = myAuth.getCurrentUser();
        userID = Objects.requireNonNull(myAuth.getCurrentUser()).getUid();

        DocumentReference documentReference = db.collection("users").document(userID);
        documentReference.addSnapshotListener(this, (value, error) -> {
            assert value != null;
            GlobalClass.setPilotoFav(value.getString("Conductor Favorito"));
            GlobalClass.setEquipoFav(value.getString("Escudería Favorita"));
        });

        Thread welcomeThread = new Thread() {
            @Override
            public void run() {
                try {
                    super.run();
                    //hago las calls a la api
                    GlobalClass.fetchData();
                    sleep(10000);  //Delay de 5 segundos
                } catch (Exception e) {

                } finally {
                    FirebaseUser user = myAuth.getCurrentUser();
                    if (user == null){
                        Intent i = new Intent(LoadActivity.this, LoginActivity.class);
                        startActivity(i);
                    }else{
                        startActivity(new Intent(LoadActivity.this,MainActivity.class));
                    }
                    //mato la activity de load
                    finish();
                }
            }
        };
        welcomeThread.start();
    }
}