package edu.upc.eetac.dsa.models;

public class Objeto {

    public String idObjeto; //ID's numericos 1-6 (Desinfectante ->1 Mascarilla -> 2)
    public String idUser;
    public int cantidad;
    public int precio;

    public Objeto(){}

    public Objeto(String idObjeto, String idUser,int cantidad, int precio){
        this();
        this.idObjeto=idObjeto;
        this.idUser=idUser;
        this.cantidad=cantidad;
        this.precio=precio;
    }

    public String getIdObjeto() {
        return idObjeto;
    }

    public void setIdObjeto(String id) {
        this.idObjeto = id;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
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
                ", precio=" + precio +
                '}';
    }
}
