package edu.upc.eetac.dsa.models;

public class Mapa {

    public int idMapa;
    public String mapa;

    public Mapa(){
    }

    public Mapa(int idMapa, String mapa) {
        this();
        this.idMapa = idMapa;
        this.mapa = mapa;
    }

    public int getIdMapa() {
        return idMapa;
    }

    public void setIdMapa(int idMapa) {
        this.idMapa = idMapa;
    }

    public String getMapa() {
        return mapa;
    }

    public void setMapa(String mapa) {
        this.mapa = mapa;
    }

    @Override
    public String toString() {
        return "Mapa{" +
                "idMapa=" + idMapa +
                ", mapa='" + mapa + '\'' +
                '}';
    }
}
