package com.example.myf1;

public class ModeloListaDriver {
    public String nombre;
    public String puntos;
    public String posicion;
    public int imageTeam;
    public int imageNac;

    public ModeloListaDriver(String nombre, String puntos, String posicion, int imageTeam, int imageNac){
        this.nombre = nombre;
        this.puntos = "Puntos: " +puntos;
        this.posicion = posicion;
        this.imageTeam = imageTeam;
        this.imageNac = imageNac;
    }
}
