package com.example.myf1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myf1.model.Data;
import com.example.myf1.model.JSONPlaceHolderAPI;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ResultadosDetalleActivity extends AppCompatActivity {

    private ListView mListView;
    private TextView txtNombreCarrera;
    private List<ModeloListaResult> mLista = new ArrayList<>();
    ListAdapterResult mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados_detalle);

        mListView = findViewById(R.id.listViewResultadosDetalle);
        txtNombreCarrera = findViewById(R.id.textResultadoDetalleCarrera);

        /*Recupero el round desde el intent*/
        Intent intent = getIntent();
        String round = intent.getStringExtra("round");
        setResultados(round);
    }

    private void setResultados(String round) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ergast.com/api/f1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JSONPlaceHolderAPI jsonPlaceHolderAPI = retrofit.create(JSONPlaceHolderAPI.class);
        Call<Data> call = jsonPlaceHolderAPI.getResultadoRound(round);

        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                if(!response.isSuccessful()){
                    /*No trajo la response*/
                    /*No es lo mismo que si hubo un problema*/
                    /*Aca se devuelven codigos de error HTTP*/
                    return;
                }

                /*Aca trajo los datos satisfactoriamente*/
                Data datos = response.body();
                List<Data.MRData.RaceTable.Race.Result> listResults = datos.MRData.RaceTable.Races.get(0).Results;

                String nombreCarrera = datos.MRData.RaceTable.Races.get(0).season + " " + datos.MRData.RaceTable.Races.get(0).raceName;
                txtNombreCarrera.setText(nombreCarrera);

                for(Data.MRData.RaceTable.Race.Result resultado: listResults){
                    /*Asigno la imagen de la nacionalidad*/
                    String imagenNacString = "@drawable/nat_" + resultado.Driver.nationality.toLowerCase();
                    int imagenNac = getResources().getIdentifier(imagenNacString, null, getPackageName());

                    /*Asigno por default foto transparente para vuelta rapida*/
                    int imagenFastLap = R.drawable.transparent;


                    /*Chequeo si existe vuelta rapida */
                    if(Objects.nonNull(resultado.FastestLap)){
                        /*Chequeo si fue vuelta rapida para asignar la imagen*/
                        String fl = resultado.FastestLap.rank;
                        if(fl.equals("1")){
                            /*Es vuelta rapida*/
                            imagenFastLap = R.drawable.chrono;
                        }
                    }

                    /*Los lapped no vienen con Time*/
                    /*Asigno por default el estado*/
                    String tiempo = resultado.status;
                    if(Objects.nonNull(resultado.Time)){
                        tiempo = resultado.Time.time;
                    }


                    mLista.add(new ModeloListaResult(
                            resultado.position,
                            resultado.Driver.givenName + " " + resultado.Driver.familyName,
                            resultado.Constructor.name,
                            imagenNac,
                            imagenFastLap,
                            tiempo,
                            resultado.points)
                    );

                }

                mAdapter = new ListAdapterResult(ResultadosDetalleActivity.this, R.layout.item_row_driver, mLista);
                mListView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                /*Hubo algun problema para traer los datos*/
            }
        });

    }
}