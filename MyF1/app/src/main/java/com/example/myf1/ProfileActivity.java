package com.example.myf1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.Objects;


public class ProfileActivity extends AppCompatActivity {
    EditText txtProfileName, txtProfileEmail;
    TextView txtProfileNameDefault;
    FirebaseAuth myAuth;
    FirebaseFirestore db;
    String userID;
    FirebaseUser user;
    ImageView profileImage;
    Button changeProfile, changePasswordLocal, logoutButton;
    private Spinner spinnerDriver, spinnerTeam;
    private String favDriver;
    private String favTeam;
    StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        txtProfileNameDefault = (TextView)findViewById(R.id.txtNameDefaultProfile);
        txtProfileName = (EditText)findViewById(R.id.txtNameProfile);
        txtProfileEmail = (EditText) findViewById(R.id.txtEmailAdressProfile);
        profileImage = (ImageView) findViewById(R.id.profile_image);
        spinnerDriver = (Spinner) findViewById(R.id.spinnerDriverProfile);
        spinnerTeam = (Spinner) findViewById(R.id.spinnerTeamProfile);
        changeProfile = (Button)findViewById(R.id.btnSaveChangesProfile);
        changePasswordLocal = (Button)findViewById(R.id.btnChangePassword);
        logoutButton = (Button) findViewById(R.id.btnLogout);

        myAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        user = myAuth.getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference();
        userID = Objects.requireNonNull(myAuth.getCurrentUser()).getUid();

        DocumentReference documentReference = db.collection("users").document(userID);
        documentReference.addSnapshotListener(this, (value, error) -> {
            assert value != null;
            txtProfileNameDefault.setText(value.getString("Nombre"));
        });
        StorageReference profileReference = storageReference.child("users/"+userID+"/profile.jpg");
        profileReference.getDownloadUrl().addOnSuccessListener(uri -> Picasso.get().load(uri).into(profileImage));
//TODO: RESOLVER EL ERROR DE LA IMAGEN FIREBASE
        profileImage.setOnClickListener(view -> {
            ImagePicker.with(ProfileActivity.this)
                    .crop()	    			//Crop image(Optional), Check Customization for more option
                    /*.compress(1024)			//Final image size will be less than 1 MB(Optional)
                    .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)*/
                    .start();
        });

        logoutButton.setOnClickListener(this::logout);

        changePasswordLocal.setOnClickListener(view -> {
            final EditText resetPassword = new EditText(view.getContext());
            final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(view.getContext());
            passwordResetDialog.setTitle("¿Desea cambiar la contraseña?");
            passwordResetDialog.setMessage("Ingrese la nueva contraseña");
            passwordResetDialog.setView(resetPassword);

            passwordResetDialog.setPositiveButton("Sí", (dialogInterface, i) -> {
                //extraer el email y enviar el link para reestablecer
                String password = resetPassword.getText().toString();
                user.updatePassword(password).addOnSuccessListener(unused -> Toast.makeText(ProfileActivity.this, "Contraseña cambiada exitosamente!", Toast.LENGTH_SHORT).show()).addOnFailureListener(e -> Toast.makeText(ProfileActivity.this, "Error, no se pudo cambiar la contraseña.", Toast.LENGTH_SHORT).show());
            });

            passwordResetDialog.setNegativeButton("No", (dialogInterface, i) -> {
                //close
            });
            passwordResetDialog.create().show();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        assert data != null;
        Uri imageUri = data.getData();
        uploadImageFirebase(imageUri);
    }

    private void uploadImageFirebase(Uri imageUri) {
        // upload image to firebase storage
        StorageReference fileReference = storageReference.child("users/"+userID+"/profile.jpg");
        fileReference.putFile(imageUri).addOnSuccessListener(taskSnapshot -> fileReference.getDownloadUrl().addOnSuccessListener(uri -> Picasso.get().load(uri).into(profileImage))).addOnFailureListener(e -> Toast.makeText(ProfileActivity.this, "Error, no se pudo guardar la imagen.", Toast.LENGTH_SHORT).show());
    }

    private void logout(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        finish();
    }
}