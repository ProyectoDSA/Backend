package edu.upc.eetac.dsa.models;

import edu.upc.eetac.dsa.orm.util.RandomUtils;

public class User {

    public String idUser;
    public String nombre;
    public String mail;
    public String password;
    public String status;

    public User(){}

    public User(String nombre, String mail, String password) {
        this();
        this.idUser = RandomUtils.generateID(6);
        this.nombre = nombre;
        this.mail = mail;
        this.password = password;
        this.status = "active";
    }


    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "User [idUser="+idUser+", name=" + nombre + ", mail=" + mail +", status= " + status +"]";
    }

}
