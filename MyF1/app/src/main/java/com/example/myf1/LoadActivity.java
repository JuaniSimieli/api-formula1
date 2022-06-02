package com.example.myf1;

import static java.lang.Thread.sleep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoadActivity extends AppCompatActivity {
    private FirebaseAuth myAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);

        Thread welcomeThread = new Thread() {
            @Override
            public void run() {
                try {
                    super.run();
                    /*Hago las calls a la api*/
                    GlobalClass.fetchData();
                    myAuth = FirebaseAuth.getInstance();
                    sleep(2500);  /*Delay de 2,5 segundos*/
                } catch (Exception e) {

                } finally {
                    /*Llamo al activity*/
                    FirebaseUser user = myAuth.getCurrentUser();
                    if (user == null){
                        startActivity(new Intent(LoadActivity.this, LoginActivity.class));
                    }else{
                        startActivity(new Intent(LoadActivity.this,DashBoardActivity.class));
                    }
                    /*Mato la activity de load*/
                    finish();
                }
            }
        };
        welcomeThread.start();
    }
}