package edu.upc.eetac.dsa.models;

public class RegisterCredentials {

    public String nombre;
    public String mail;
    public String password;

    public RegisterCredentials(){}

    public RegisterCredentials(String nombre, String mail, String password) {
        this();
        this.nombre = nombre;
        this.mail = mail;
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
