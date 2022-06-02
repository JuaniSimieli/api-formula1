package com.example.myf1;

import static java.lang.Thread.sleep;

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
                    /*Hago las calls a la api*/
                    GlobalClass.fetchData();
                    sleep(2500);  /*Delay de 2,5 segundos*/
                } catch (Exception e) {

                } finally {
                    /*Llamo al activity*/
                    Intent i = new Intent(LoadActivity.this,
                            MainActivity.class);
                    startActivity(i);
                    /*Mato la activity de load*/
                    finish();
                }
            }
        };
        welcomeThread.start();
    }
}