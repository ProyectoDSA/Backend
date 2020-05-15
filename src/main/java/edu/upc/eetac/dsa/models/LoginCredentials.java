package edu.upc.eetac.dsa.models;

public class LoginCredentials {

    public String nombre;
    public String password;

    public LoginCredentials(){

    }

    public LoginCredentials(String nombre, String password){
        this();
        this.nombre=nombre;
        this.password=password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
