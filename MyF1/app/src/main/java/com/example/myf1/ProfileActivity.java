package com.example.myf1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;


public class ProfileActivity extends AppCompatActivity {
    private static final int GALLERY_INTENT_CODE=1023;
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
        userID = myAuth.getCurrentUser().getUid();
        DocumentReference documentReference = db.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                txtProfileNameDefault.setText(value.getString("Nombre"));
            }
        });
        storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference profileReference = storageReference.child("users/"+userID+"/profile.jpg");
        profileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileImage);
            }
        });
//TODO: RESOLVER EL ERROR DE LA IMAGEN FIREBASE
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // open gallery
                Intent openGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGallery,1000);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout(view);
            }
        });

        changePasswordLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText resetPassword = new EditText(view.getContext());
                final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(view.getContext());
                passwordResetDialog.setTitle("¿Desea cambiar la contraseña?");
                passwordResetDialog.setMessage("Ingrese la nueva contraseña");
                passwordResetDialog.setView(resetPassword);

                passwordResetDialog.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //extraer el email y enviar el link para reestablecer
                        String password = resetPassword.getText().toString();
                        user.updatePassword(password).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(ProfileActivity.this, "Contraseña cambiada exitosamente!", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ProfileActivity.this, "Error, no se pudo cambiar la contraseña.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //close
                    }
                });
                passwordResetDialog.create().show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            if(resultCode == Activity.RESULT_OK){
                Uri imageUri = data.getData();
                //profileImage.setImageURI(imageUri);
                uploadImageFirebase(imageUri);
            }
        }
    }

    private void uploadImageFirebase(Uri imageUri) {
        // upload image to firebase storage
        StorageReference fileReference = storageReference.child("users/"+userID+"/profile.jpg");
        fileReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(profileImage);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ProfileActivity.this, "Error, no se pudo guardar la imagen.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void logout(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        finish();
    }
}