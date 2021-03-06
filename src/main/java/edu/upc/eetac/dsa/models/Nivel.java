package edu.upc.eetac.dsa.models;

public class Nivel {

    public int idNivel;
    public String enemigos;

    public Nivel(){}

    public Nivel(int idNivel, String enemigos) {
        this();
        this.idNivel = idNivel;
        this.enemigos = enemigos;
    }

    public int getIdNivel() {
        return idNivel;
    }

    public void setIdNivel(int idNivel) {
        this.idNivel = idNivel;
    }

    public String getEnemigos() {
        return enemigos;
    }

    public void setEnemigos(String enemigos) {
        this.enemigos = enemigos;
    }

    @Override
    public String toString() {
        return "Nivel{" +
                "idNivel=" + idNivel +
                ", enemigos='" + enemigos + '\'' +
                '}';
    }
}
