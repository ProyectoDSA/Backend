package edu.upc.eetac.dsa.models;

public class TokenStorage {

    String token;
    int monedas;

    public TokenStorage(){}

    public TokenStorage(String token, int monedas){
        this();
        this.token=token;
        this.monedas=monedas;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getMonedas() {
        return monedas;
    }

    public void setMonedas(int monedas) {
        this.monedas = monedas;
    }

    @Override
    public String toString() {
        return "TokenStorage{" +
                "token='" + token + '\'' +
                "monedas="+monedas+'\''+
                '}';
    }
}
