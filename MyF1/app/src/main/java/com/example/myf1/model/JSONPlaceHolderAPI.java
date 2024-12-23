package com.example.myf1.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface JSONPlaceHolderAPI {
    @GET("current/driverStandings.json")
    Call<Data> getPilotos();

    @GET("current/constructorStandings.json")
    Call<Data> getConstructores();

    @GET("current.json")
    Call<Data> getCarreras();

    @GET("current/{round}/results.json")
    Call<Data> getResultadoRound(
            @Path("round") String round);
}
