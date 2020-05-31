package edu.upc.eetac.dsa.orm.managers;

import edu.upc.eetac.dsa.exceptions.UserNotFoundException;
import edu.upc.eetac.dsa.models.Inventario;
import edu.upc.eetac.dsa.models.Jugador;
import edu.upc.eetac.dsa.models.Objeto;

import java.util.HashMap;
import java.util.List;

public interface GameManager {

    public HashMap<Integer,Jugador> getRanking();

    public void updateJugador(String id, int puntos, int accion) throws Exception;
    public HashMap<Integer, Inventario> getObjetosJugador(String idJugador);
    public void updateCantidadObjetoJugador(String idJugador, int idObjeto, int cantidad) throws UserNotFoundException;

}
