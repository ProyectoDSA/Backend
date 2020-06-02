package edu.upc.eetac.dsa.models;

public class TokenStorage {

    String token;

    public TokenStorage(){}

    public TokenStorage(String token){
        this();
        this.token=token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "TokenStorage{" +
                "token='" + token + '\'' +
                '}';
    }
}
