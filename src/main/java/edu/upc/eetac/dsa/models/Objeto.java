package edu.upc.eetac.dsa.models;

public class Objeto {

    public int idObjeto; //ID's numericos 1-6 (Desinfectante ->1 Mascarilla -> 2)
    public int precio;
    public String nombre;
    public String imagen;

    public Objeto(){}

    public Objeto(int idObjeto, int precio, String nombre, String imagen){
        this();
        this.idObjeto=idObjeto;
        this.precio=precio;
        this.nombre=nombre;
        this.imagen=imagen;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    @Override
    public String toString() {
        return "Objeto{" +
                "idObjeto='" + idObjeto + '\'' +
                ", nombre="+ nombre + '\'' +
                ", precio=" + precio +
                '}';
    }
}
