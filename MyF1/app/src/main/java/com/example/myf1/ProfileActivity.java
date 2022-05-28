package com.example.myf1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;


public class ProfileActivity extends AppCompatActivity {
    private static final int GALLERY_INTENT_CODE=1023;
    EditText txtProfileName, txtProfileEmail;
    FirebaseAuth myAuth;
    FirebaseFirestore db;
    String userID;
    FirebaseUser user;
    ImageView profileImage;
    Button changeProfile;
    private Spinner spinnerDriver, spinnerTeam;
    private String favDriver;
    private String favTeam;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        txtProfileName = (EditText)findViewById(R.id.txtNameProfile);
        txtProfileEmail = (EditText) findViewById(R.id.txtEmailAdressProfile);
        profileImage = (ImageView) findViewById(R.id.profile_image);
        spinnerDriver = (Spinner) findViewById(R.id.spinnerDriverProfile);
        spinnerTeam = (Spinner) findViewById(R.id.spinnerTeamProfile);
        changeProfile = (Button)findViewById(R.id.btnSaveChangesProfile);

        myAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // open gallery
                Intent openGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGallery,1000);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            if(resultCode == Activity.RESULT_OK){
                Uri imageUri = data.getData();
                profileImage.setImageURI(imageUri);
            }
        }
    }
}