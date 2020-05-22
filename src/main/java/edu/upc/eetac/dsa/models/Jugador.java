package edu.upc.eetac.dsa.models;

import java.util.List;

public class Jugador extends User{

    public String id;
    public List<Objeto> objetos;
    public List<Partida> games;
    public int monedas;

    public Jugador(String id, List<Objeto> objetos, List<Partida> games, int monedas) {
        this.id = id;
        this.objetos = objetos;
        this.games = games;
        this.monedas = monedas;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public List<Objeto> getObjetos() {
        return objetos;
    }

    public void setObjetos(List<Objeto> objetos) {
        this.objetos = objetos;
    }

    public List<Partida> getGames() {
        return games;
    }

    public void setGames(List<Partida> games) {
        this.games = games;
    }

    public int getMonedas() {
        return monedas;
    }

    public void setMonedas(int monedas) {
        this.monedas = monedas;
    }

    @Override
    public String toString() {
        return "Jugador{" +
                "id='" + id + '\'' +
                ", objetos=" + objetos +
                ", partidas=" + games +
                ", monedas=" + monedas +
                '}';
    }
}
