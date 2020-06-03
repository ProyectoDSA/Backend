package edu.upc.eetac.dsa.orm.managers;

import edu.upc.eetac.dsa.exceptions.UserNotFoundException;
import edu.upc.eetac.dsa.models.Inventario;
import edu.upc.eetac.dsa.models.Jugador;
import edu.upc.eetac.dsa.models.JugadorRanking;
import edu.upc.eetac.dsa.models.Objeto;

import java.util.HashMap;
import java.util.List;

public interface GameManager {

    public HashMap<Integer,JugadorRanking> getRanking();
    public JugadorRanking getJugador(String token);
    public void updateJugador(String id, int puntos, int accion) throws Exception;
    public HashMap<Integer, Inventario> getObjetosJugador(String token);
    public void addObjetoJugador(Inventario objeto);
    public void updateInventario(String token, int idObjeto, int newCantidad, int accion) throws UserNotFoundException, Exception;
    public int getPrecioObjeto(int idObjeto);

}
