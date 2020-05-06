package edu.upc.dsa.models;

import edu.upc.dsa.util.RandomUtils;

public class User {

    public String id;
    public String nombre;
    public String mail;

    public User(){}

    public User(String nombre, String mail) {
        this();
        this.id = RandomUtils.generateID(6);
        this.nombre = nombre;
        this.mail = mail;
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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Override
    public String toString() {
        return "User [id="+id+", name=" + nombre + ", mail=" + mail +"]";
    }

}
