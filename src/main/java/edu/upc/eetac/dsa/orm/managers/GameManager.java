package edu.upc.eetac.dsa.orm.managers;

import edu.upc.eetac.dsa.models.Jugador;

import java.util.HashMap;
import java.util.List;

public interface GameManager {

    public HashMap<String,Jugador> getRanking();

    public void updatePuntosJugador(String id, int puntos);
    public void updateMonedasJugador(String id, int puntos);
    public void updateJugador(String id, int puntos, int accion);

}
