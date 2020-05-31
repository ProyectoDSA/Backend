package edu.upc.eetac.dsa.models;

public class Objeto {

    public int idObjeto; //ID's numericos 1-6 (Desinfectante ->1 Mascarilla -> 2)
    public int precio;

    public Objeto(){}

    public Objeto(int idObjeto, int precio){
        this();
        this.idObjeto=idObjeto;
        this.precio=precio;
    }

    public int getIdObjeto() {
        return idObjeto;
    }

    public void setIdObjeto(int id) {
        this.idObjeto = id;
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
