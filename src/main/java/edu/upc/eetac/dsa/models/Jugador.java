package edu.upc.eetac.dsa.models;

import java.util.LinkedList;
import java.util.List;

public class Jugador{

    public String idJugador; //Mismo id que User
    public int puntos; //Puntuación-monedas totales del jugador
    public int vida; //Vida del jugador
    public List<Objeto> objetos = new LinkedList<>(); //Lista con los objetos del usuario
    public List<Partida> partidas = new LinkedList<>(); //Lista con las partidas del jugador
    public User user;

    public Jugador(){
        this.idJugador = user.getIdUser();
    }

    public Jugador(int puntos, int vida) {
        this();
        this.puntos = puntos;
        this.vida = vida;
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

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public List<Objeto> getObjetos() {
        return objetos;
    }

    public void setObjetos(List<Objeto> objetos) {
        this.objetos = objetos;
    }

    public List<Partida> getPartidas() {
        return partidas;
    }

    public void setPartidas(List<Partida> partidas) {
        this.partidas = partidas;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Jugador{" +
                "idJugador='" + idJugador + '\'' +
                ", puntos=" + puntos +
                ", vida=" + vida +
                '}';
    }
}
