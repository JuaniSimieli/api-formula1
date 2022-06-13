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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private FirebaseAuth myAuth;
    private FirebaseFirestore db;
    private String userID;
    private FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new CarrerasFragment());
        //FIREBASE
        myAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        if (Objects.nonNull(myAuth)){
            user = myAuth.getCurrentUser();
            userID = myAuth.getUid();
        }
        if (Objects.nonNull(userID)){
            DocumentReference documentReference = db.collection("users").document(userID);
            documentReference.addSnapshotListener(this, (value, error) -> {
                assert value != null;
                GlobalClass.setPilotoFav(value.getString("Conductor Favorito"));
                GlobalClass.setEquipoFav(value.getString("Escudería Favorita"));
            });

        }
        //TERMINA FIREBASE
        binding.bottomNavigationView.setSelectedItemId(R.id.carre);

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