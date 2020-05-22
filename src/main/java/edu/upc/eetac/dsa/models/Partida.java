package edu.upc.eetac.dsa.models;

public class Partida {

    public int idPartida;
    public String idPlayer;
    public int nivel;
    public int puntos;
    public float tiempo;

    public Partida(){}

    public Partida(int idPartida, String idPlayer, int nivel, int puntos, float tiempo) {
        this.idPartida = idPartida;
        this.idPlayer = idPlayer;
        this.nivel = nivel;
        this.puntos = puntos;
        this.tiempo = tiempo;
    }

    public int getIdPartida() {
        return idPartida;
    }

    public void setIdPartida(int idPartida) {
        this.idPartida = idPartida;
    }

    public String getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(String idPlayer) {
        this.idPlayer = idPlayer;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }
}
