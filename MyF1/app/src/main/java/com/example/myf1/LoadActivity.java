package com.example.myf1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class LoadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);

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
                    //llamo al activity
                    Intent i = new Intent(LoadActivity.this,
                            MainActivity.class);
                    startActivity(i);
                    //mato la activity de load
                    finish();
                }
            }
        };
        welcomeThread.start();
    }
}