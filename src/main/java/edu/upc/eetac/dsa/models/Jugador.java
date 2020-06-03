package edu.upc.eetac.dsa.models;

public class Jugador {

    public String idJugador;
    public int puntos;
    public int monedas;
    public String status;

    public Jugador(){}

    public Jugador(String id, int puntos, int monedas) {
        this();
        this.idJugador = id;
        this.puntos = puntos;
        this.monedas = monedas;
        this.status="active";
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

    public int getMonedas() {
        return monedas;
    }

    public void setMonedas(int monedas) {
        this.monedas = monedas;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Jugador{" +
                "idJugador='" + idJugador + '\'' +
                ", puntos=" + puntos + '\'' +
                ", monedas=" + monedas+
                '}';
    }
}
