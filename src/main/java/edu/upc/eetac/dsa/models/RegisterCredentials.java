package edu.upc.eetac.dsa.models;

public class RegisterCredentials {

    public String nombre;
    public String mail;
    public String password;
    public String confirm;

    public RegisterCredentials(){}

    public RegisterCredentials(String nombre, String mail, String password, String confirm) {
        this();
        this.nombre = nombre;
        this.mail = mail;
        this.password = password;
        this.confirm = confirm;
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

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    @Override
    public String toString() {
        return "RegisterCredentials{" +
                "nombre='" + nombre + '\'' +
                ", mail='" + mail + '\'' +
                ", password='" + password + '\'' +
                ", confirm='" + confirm + '\'' +
                '}';
    }
}
