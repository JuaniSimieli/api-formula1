package com.example.myf1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;

public class LoadActivity extends AppCompatActivity {
    private FirebaseAuth myAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        myAuth = FirebaseAuth.getInstance();

        Thread welcomeThread = new Thread() {
            @Override
            public void run() {
                try {
                    super.run();
                    //hago las calls a la api
                    GlobalClass.fetchData();
                    sleep(2000);  //Delay de 2 segundos
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