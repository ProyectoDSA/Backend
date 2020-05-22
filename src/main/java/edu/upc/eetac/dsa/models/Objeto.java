package edu.upc.eetac.dsa.models;

public class Objeto {

    public String id; //ID's numericos Desinfectante ->1 Mascarilla -> 2.
    public String nombre;
    public int precio;
    public String descripcion;

    public Objeto(){}

    public Objeto(String id, String nombre, int precio, String descripcion){
        this.id=id;
        this.nombre=nombre;
        this.precio=precio;
        this.descripcion=descripcion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Objeto{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
