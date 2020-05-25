package edu.upc.eetac.dsa.models;

public class Jugador {

    public String idJugador;
    public int puntos;

    public Jugador(){}

    public Jugador(String id, int puntos) {
        this();
        this.idJugador = id;
        this.puntos = puntos;
    }

    public String getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(String idJugador) {
        this.idJugador = idJugador;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    @Override
    public String toString() {
        return "Jugador{" +
                "idJugador='" + idJugador + '\'' +
                ", puntos=" + puntos +
                '}';
    }
}
