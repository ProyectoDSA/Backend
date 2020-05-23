package edu.upc.eetac.dsa.models;

public class Objeto {

    public String idObjeto; //ID's numericos 1-6 (Desinfectante ->1 Mascarilla -> 2)
    public String nombre;
    public int precio;

    public Objeto(){}

    public Objeto(String idObjeto, String nombre, int precio){
        this();
        this.idObjeto=idObjeto;
        this.nombre=nombre;
        this.precio=precio;
    }

    public String getIdObjeto() {
        return idObjeto;
    }

    public void setIdObjeto(String id) {
        this.idObjeto = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                '}';
    }
}
