package com.example.myf1;

public class ModeloListaRace {
    public String nombre;
    public String circuito;
    public String round;
    public int imageTeam;
    public String date;
    public String time;

    public ModeloListaRace(String nombre, String circuito, String round, int imageTeam, String date, String time){
        this.nombre = nombre;
        this.circuito = circuito;
        this.round = round;
        this.imageTeam = imageTeam;
        this.date = date;
        this.time = time;
    }
}
