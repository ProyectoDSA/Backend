package edu.upc.eetac.dsa.models;

public class Inventario {

    public int idObjeto;
    public int cantidad;
    public String idJugador;

    public Inventario(){}

    public Inventario(int idObjeto, int cantidad, String idJugador) {
        this();
        this.idObjeto = idObjeto;
        this.cantidad = cantidad;
        this.idJugador = idJugador;
    }

    public String getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(String idJugador) {
        this.idJugador = idJugador;
    }

    public int getIdObjeto() {
        return idObjeto;
    }

    public void setIdObjeto(int idObjeto) {
        this.idObjeto = idObjeto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "Inventario{" +
                "idJugador='" + idJugador + '\'' +
                ", idObjeto=" + idObjeto +
                ", cantidad=" + cantidad +
                '}';
    }
}
