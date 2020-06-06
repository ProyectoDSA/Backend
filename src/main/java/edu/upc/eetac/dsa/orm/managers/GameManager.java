package edu.upc.eetac.dsa.orm.managers;

import edu.upc.eetac.dsa.exceptions.UserNotFoundException;
import edu.upc.eetac.dsa.models.*;

import java.util.HashMap;
import java.util.List;

public interface GameManager {

    public void addPartida(Partida partida);
    public HashMap<Integer,JugadorRanking> getRanking();
    public HashMap<Integer, RankingPartida> getRankingPartidas(String token);
    public JugadorRanking getJugador(String token);
    public void updateJugador(String id, int puntos, int accion) throws Exception;
    public HashMap<Integer, Inventario> getObjetosJugador(String token);
    public void addObjetoJugador(Inventario objeto);
    public void updateInventario(String token, int idObjeto, int newCantidad, int accion) throws UserNotFoundException, Exception;
    public int getPrecioObjeto(int idObjeto);

}
