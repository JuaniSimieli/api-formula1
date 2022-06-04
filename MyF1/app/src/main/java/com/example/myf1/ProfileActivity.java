package com.example.myf1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
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

        txtProfileNameDefault = (TextView) findViewById(R.id.txtNameDefaultProfile);
        txtProfileName = (EditText) findViewById(R.id.txtNameProfile);
        txtProfileEmail = (EditText) findViewById(R.id.txtEmailAdressProfile);
        profileImage = (ImageView) findViewById(R.id.profile_image);
        spinnerDriver = (Spinner) findViewById(R.id.spinnerDriverProfile);
        spinnerTeam = (Spinner) findViewById(R.id.spinnerTeamProfile);
        changeProfile = (Button) findViewById(R.id.btnSaveChangesProfile);
        changePasswordLocal = (Button) findViewById(R.id.btnChangePassword);
        logoutButton = (Button) findViewById(R.id.btnLogout);

        myAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        user = myAuth.getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference();
        userID = Objects.requireNonNull(myAuth.getCurrentUser()).getUid();

        DocumentReference documentReference = db.collection("users").document(userID);
        documentReference.addSnapshotListener(this, (value, error) -> {
            assert value != null;
            txtProfileNameDefault.setText(Objects.requireNonNull(value.getString("Nombre")).toUpperCase(Locale.ROOT));
        });
        StorageReference profileReference = storageReference.child("users/" + userID + "/profile.jpg");
        profileReference.getDownloadUrl().addOnSuccessListener(uri -> Picasso.get().load(uri).into(profileImage));
//TODO: RESOLVER EL ERROR DE LA IMAGEN FIREBASE
        profileImage.setOnClickListener(view -> ImagePicker.with(ProfileActivity.this)
                .crop()                    //Crop image(Optional), Check Customization for more option
                /*.compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)*/
                .start());

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

        /*spinner y adaptador del conductor favorito*/
        ArrayAdapter<CharSequence> driverAdapter = ArrayAdapter.createFromResource(this, R.array.drivers_array, android.R.layout.simple_spinner_item);
        driverAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDriver.setAdapter(driverAdapter);

        /*spinner y adaptador del equipo favorito*/
        ArrayAdapter<CharSequence> teamAdapter = ArrayAdapter.createFromResource(this, R.array.teams_array, android.R.layout.simple_spinner_item);
        teamAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTeam.setAdapter(teamAdapter);

        /*Spinner Driver validation*/
        spinnerDriver.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String driver = adapterView.getItemAtPosition(i).toString();
                switch (driver) {
                    case "Alexander Albon":
                        favDriver = "albon";
                        break;
                    case "Carlos Sainz":
                        favDriver = "sainz";
                        break;
                    case "Charles Leclerc":
                        favDriver = "leclerc";
                        break;
                    case "Daniel Ricciardo":
                        favDriver = "ricciardo";
                        break;
                    case "Esteban Ocon":
                        favDriver = "ocon";
                        break;
                    case "Fernando Alonso":
                        favDriver = "alonso";
                        break;
                    case "George Russell":
                        favDriver = "russell";
                        break;
                    case "Guanyu Zhou":
                        favDriver = "zhou";
                        break;
                    case "Kevin Magnussen":
                        favDriver = "kevin_magnussen";
                        break;
                    case "Lance Stroll":
                        favDriver = "stroll";
                        break;
                    case "Lando Norris":
                        favDriver = "norris";
                        break;
                    case "Lewis Hamilton":
                        favDriver = "hamilton";
                        break;
                    case "Max Verstappen":
                        favDriver = "max_verstappen";
                        break;
                    case "Mick Schumacher":
                        favDriver = "mick_schumacher";
                        break;
                    case "Nicholas Latifi":
                        favDriver = "latifi";
                        break;
                    case "Nico Hülkenberg":
                        favDriver = "hulkenberg";
                        break;
                    case "Pierre Gasly":
                        favDriver = "gasly";
                        break;
                    case "Sebastian Vettel":
                        favDriver = "vettel";
                        break;
                    case "Sergio Pérez":
                        favDriver = "perez";
                        break;
                    case "Valtteri Bottas":
                        favDriver = "bottas";
                        break;
                    case "Yuki Tsunoda":
                        favDriver = "tsunoda";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        /*Spinner Team validation*/
        spinnerTeam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String team = adapterView.getItemAtPosition(i).toString();
                switch (team) {
                    case "Alfa Romero":
                        favTeam = "alfa";
                        break;
                    case "AlphaTauri":
                        favTeam = "alphatauri";
                        break;
                    case "Alpine F1 Team":
                        favTeam = "alpine";
                        break;
                    case "Aston Martin":
                        favTeam = "aston_martin";
                        break;
                    case "Ferrari":
                        favTeam = "ferrari";
                        break;
                    case "Haas F1 Team":
                        favTeam = "haas";
                        break;
                    case "McLaren":
                        favTeam = "mclaren";
                        break;
                    case "Mercedes":
                        favTeam = "mercedes";
                        break;
                    case "Red Bull":
                        favTeam = "red_bull";
                        break;
                    case "Williams":
                        favTeam = "williams";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        changeProfile.setOnClickListener(view -> updateUser());
    }

    //Acá termina el onCreate

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        assert data != null;
        Uri imageUri = data.getData();
        uploadImageFirebase(imageUri);
    }

    private void uploadImageFirebase(Uri imageUri) {
        // upload image to firebase storage
        StorageReference fileReference = storageReference.child("users/" + userID + "/profile.jpg");
        fileReference.putFile(imageUri).addOnSuccessListener(taskSnapshot -> fileReference.getDownloadUrl().addOnSuccessListener(uri -> Picasso.get().load(uri).into(profileImage))).addOnFailureListener(e -> Toast.makeText(ProfileActivity.this, "Error, no se pudo guardar la imagen.", Toast.LENGTH_SHORT).show());
    }

    private void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }

    public void updateUser() {
        String name = txtProfileName.getText().toString();
        String email = txtProfileEmail.getText().toString();
        String driver = favDriver;
        String team = favTeam;

        if (TextUtils.isEmpty(name) && TextUtils.isEmpty(email) && TextUtils.isEmpty(driver) && TextUtils.isEmpty(team)) {
            Toast.makeText(this, "Uno de los campos debe tener información", Toast.LENGTH_SHORT).show();
        } else {
            DocumentReference documentReference = db.collection("users").document(userID);
            Map<String, Object> edited = new HashMap<>();
            if (!TextUtils.isEmpty(name)) {
                edited.put("Nombre", name);
            } else if (!TextUtils.isEmpty(email)) {
                edited.put("Email", email);
            } else if (!TextUtils.isEmpty(driver)) {
                edited.put("Conductor Favorito", driver);
            } else if (!TextUtils.isEmpty(team)) {
                edited.put("Escudería Favorita", team);
            }
            documentReference.update(edited).addOnSuccessListener(unused -> {
                Toast.makeText(ProfileActivity.this, "Perfil cambiado!", Toast.LENGTH_SHORT).show();
                txtProfileName.setText("");
                txtProfileEmail.setText("");
                spinnerDriver.setSelection(0);
                spinnerTeam.setSelection(0);
            });
        }
    }
}