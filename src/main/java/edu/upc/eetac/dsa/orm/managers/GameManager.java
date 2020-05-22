package edu.upc.eetac.dsa.orm.managers;

import edu.upc.eetac.dsa.models.Partida;

import java.util.List;

public interface GameManager {

    public Partida getPartida(String idPartida);
    public String getIdJugador(String idPartida);
    public int getPuntuacion(String idPartida);

}
