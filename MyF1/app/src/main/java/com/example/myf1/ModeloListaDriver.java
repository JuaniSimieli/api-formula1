package com.example.myf1;

import com.example.myf1.model.Data;

public class ModeloListaDriver {
    public String nombre;
    public String puntos;
    public String posicion;
    public int imageTeam;
    public int imageNac;
    public Data.MRData.StandingsTable.StandingsLists.DriverStandings pilotoDetalle;
    public Data.MRData.StandingsTable.StandingsLists.ConstructorStandings equipoDetalle;

    public ModeloListaDriver(String nombre, String puntos, String posicion, int imageTeam, int imageNac, Data.MRData.StandingsTable.StandingsLists.DriverStandings piloto, Data.MRData.StandingsTable.StandingsLists.ConstructorStandings equipo){
        this.nombre = nombre;
        this.puntos = "Puntos: " +puntos;
        this.posicion = posicion;
        this.imageTeam = imageTeam;
        this.imageNac = imageNac;
        this.pilotoDetalle = piloto;
        this.equipoDetalle = equipo;
    }
}
