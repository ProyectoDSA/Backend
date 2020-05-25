package edu.upc.eetac.dsa.orm.managers;

import edu.upc.eetac.dsa.models.Jugador;

import java.util.HashMap;
import java.util.List;

public interface GameManager {

    public HashMap<String,Jugador> getRanking();

}
