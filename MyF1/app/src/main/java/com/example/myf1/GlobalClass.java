package com.example.myf1;

import com.example.myf1.model.Data;
import com.example.myf1.model.JSONPlaceHolderAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GlobalClass {
    public static Data pilotos = null;
    public static Data equipos = null;
    public static Data carreras = null;
    public static String pilotoFav = "leclerc";
    public static String equipoFav = "ferrari";


    public static void setEquipoFav(String equipoFav) {
        GlobalClass.equipoFav = equipoFav;
    }

    public static void setPilotoFav(String pilotoFav) {
        GlobalClass.pilotoFav = pilotoFav;
    }

    public static String getEquipoFav() {
        return equipoFav;
    }

    public static String getPilotoFav() {
        return pilotoFav;
    }

    public static void setPilotos() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ergast.com/api/f1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JSONPlaceHolderAPI jsonPlaceHolderAPI = retrofit.create(JSONPlaceHolderAPI.class);
        Call<Data> call = jsonPlaceHolderAPI.getPilotos();

        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                if(!response.isSuccessful()){
                    /*No trajo la response*/
                    /*No es lo mismo que si hubo un problema*/
                    /*Aca se devuelven codigos HTTP (400~ etc.)*/
                    return;
                }

                /*Aca trajo los datos satisfactoriamente*/
                Data datos = response.body();
                GlobalClass.pilotos = datos;
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                /*Hubo algun problema para traer los datos*/
            }
        });
    }

    public static Data getPilotos() {
        return pilotos;
    }

    public static Data getEquipos() {
        return equipos;
    }

    public static void setEquipos() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ergast.com/api/f1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JSONPlaceHolderAPI jsonPlaceHolderAPI = retrofit.create(JSONPlaceHolderAPI.class);
        Call<Data> call = jsonPlaceHolderAPI.getConstructores();

        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                if(!response.isSuccessful()){
                    /*No trajo la response*/
                    /*No es lo mismo que si hubo un problema*/
                    /*Aca se devuelven codigos HTTP (400~ etc.)*/
                    return;
                }

                /*Aca trajo los datos satisfactoriamente*/
                Data datos = response.body();
                GlobalClass.equipos = datos;
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                /*Hubo algun problema para traer los datos*/
            }
        });
    }

    public static Data getCarreras() {
        return carreras;
    }

    public static void setCarreras() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ergast.com/api/f1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JSONPlaceHolderAPI jsonPlaceHolderAPI = retrofit.create(JSONPlaceHolderAPI.class);
        Call<Data> call = jsonPlaceHolderAPI.getCarreras();

        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                if(!response.isSuccessful()){
                    /*No trajo la response*/
                    /*No es lo mismo que si hubo un problema*/
                    /*Aca se devuelven codigos HTTP (400~ etc.)*/
                    return;
                }

                /*Aca trajo los datos satisfactoriamente*/
                Data datos = response.body();
                GlobalClass.carreras = datos;
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                /*Hubo algun problema para traer los datos*/
            }
        });
    }

    public static void fetchData(){
        GlobalClass.setPilotos();
        GlobalClass.setEquipos();
        GlobalClass.setCarreras();
    }
}