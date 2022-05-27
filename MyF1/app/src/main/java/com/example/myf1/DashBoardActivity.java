package com.example.myf1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class DashBoardActivity extends AppCompatActivity {

    Button btnProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        btnProfile = (Button) findViewById(R.id.btnPerfil);

        Intent i = new Intent(DashBoardActivity.this,ProfileActivity.class);
        startActivity(i);
    }
}