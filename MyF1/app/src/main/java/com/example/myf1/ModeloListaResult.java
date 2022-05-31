package com.example.myf1;

public class ModeloListaResult {
    public String nombre;
    public String equipo;
    public String posicion;
    public int imageNac;
    public int imageFastLap;
    public String time;
    public String points;

    public ModeloListaResult(String posicion, String nombre, String equipo, int imageNac, int imageFastLap, String time, String points){
        this.nombre = nombre;
        this.equipo = equipo;
        this.posicion = posicion;
        this.imageNac = imageNac;
        this.imageFastLap = imageFastLap;
        this.time = time;
        this.points = points;
    }
}
