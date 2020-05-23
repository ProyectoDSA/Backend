package edu.upc.eetac.dsa.models;

public class Partida {

    public int idPartida;
    public String idJugador;
    public float tiempo;
    public int puntuacion;
    public int nivelMax;

    public Partida(){}

    public Partida(int idPartida, String idJugador, float tiempo, int puntuacion, int nivelMax) {
        this();
        this.idPartida = idPartida;
        this.idJugador = idJugador;
        this.tiempo = tiempo;
        this.puntuacion = puntuacion;
        this.nivelMax = nivelMax;
    }

    public int getIdPartida() {
        return idPartida;
    }

    public void setIdPartida(int idPartida) {
        this.idPartida = idPartida;
    }

    public String getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(String idJugador) {
        this.idJugador = idJugador;
    }

    public float getTiempo() {
        return tiempo;
    }

    public void setTiempo(float tiempo) {
        this.tiempo = tiempo;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public int getNivelMax() {
        return nivelMax;
    }

    public void setNivelMax(int nivelMax) {
        this.nivelMax = nivelMax;
    }

    @Override
    public String toString() {
        return "Partida{" +
                "idPartida=" + idPartida +
                ", idJugador='" + idJugador + '\'' +
                ", tiempo=" + tiempo +
                ", puntuacion=" + puntuacion +
                ", nivelMax=" + nivelMax +
                '}';
    }
}
