package edu.upc.eetac.dsa.models;

public class JugadorDB {

    public String idJugador;
    public int puntos;

    public JugadorDB(){}

    public JugadorDB(String id, int puntos) {
        this();
        this.idJugador = id;
        this.puntos = puntos;
    }

    public String getId() {
        return idJugador;
    }

    public void setId(String id) {
        this.idJugador = id;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    @Override
    public String toString() {
        return "JugadorDB{" +
                "id='" + idJugador + '\'' +
                ", puntos=" + puntos +
                '}';
    }
}
