package com.example.myf1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.RelativeLayout;

import com.example.myf1.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new FavoritosFragment());

        binding.bottomNavigationView.setSelectedItemId(R.id.favs);

        binding.bottomNavigationView.setOnItemSelectedListener(item ->  {

            switch (item.getItemId()){

                case R.id.carre:
                    replaceFragment(new CarrerasFragment());
                    break;
                case R.id.resul:
                    replaceFragment(new ResultadosFragment());
                    break;
                case R.id.favs:
                    replaceFragment(new FavoritosFragment());
                    break;
                case R.id.pilo:
                    replaceFragment(new PilotosFragment());
                    break;
                case R.id.equi:
                    replaceFragment(new EquiposFragment());
                    break;
            }

            return true;
        });


    }


    private void replaceFragment (Fragment fragment){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout,fragment);
        fragmentTransaction.commit();
    }

}