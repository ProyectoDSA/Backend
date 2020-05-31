package edu.upc.eetac.dsa.models;

public class Objeto {

    public int idObjeto; //ID's numericos 1-6 (Desinfectante ->1 Mascarilla -> 2)
    public String idJugador;
    public int cantidad;
    public int precio;

    public Objeto(){}

    public Objeto(int idObjeto, int cantidad, String idJugador, int precio){
        this();
        this.idObjeto=idObjeto;
        this.cantidad=cantidad;
        this.idJugador=idJugador;
        this.precio=precio;
    }

    public int getIdObjeto() {
        return idObjeto;
    }

    public void setIdObjeto(int id) {
        this.idObjeto = id;
    }

    public String getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(String idJugador) {
        this.idJugador = idJugador;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "Objeto{" +
                "idObjeto='" + idObjeto + '\'' +
                ", cantidad="+cantidad+'\''+
                ", precio=" + precio +
                '}';
    }
}
