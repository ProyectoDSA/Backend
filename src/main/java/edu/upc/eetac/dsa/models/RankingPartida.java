package edu.upc.eetac.dsa.models;

import edu.upc.eetac.dsa.orm.util.RandomUtils;

public class RankingPartida {

    public int puntos;
    public String duracion;
    public int nivelMax;

    public RankingPartida(){}

    public RankingPartida(int puntos, String duracion, int nivelMax) {
        this();
        this.puntos = puntos;
        this.duracion = duracion;
        this.nivelMax = nivelMax;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public int getNivelMax() {
        return nivelMax;
    }

    public void setNivelMax(int nivelMax) {
        this.nivelMax = nivelMax;
    }

    @Override
    public String toString() {
        return "RankingPartida{" +
                "puntos=" + puntos +
                ", duracion='" + duracion + '\'' +
                ", nivelMax=" + nivelMax +
                '}';
    }
}
