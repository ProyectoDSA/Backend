package edu.upc.eetac.dsa.models;

public class JugadorRanking {

    String username;
    int puntos;

    public JugadorRanking(){}

    public JugadorRanking(String username, int puntos){
        this();
        this.puntos=puntos;
        this.username=username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    @Override
    public String toString() {
        return "JugadorRanking{" +
                "username='" + username + '\'' +
                ", puntos=" + puntos +
                '}';
    }
}
