package edu.upc.eetac.dsa.models;

import edu.upc.eetac.dsa.orm.util.RandomUtils;

public class Token {

    String id;
    String token;

    public Token(){}

    public Token(String id) {
        this();
        this.token = RandomUtils.generateID(15);
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Token{" +
                "token='" + token + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
