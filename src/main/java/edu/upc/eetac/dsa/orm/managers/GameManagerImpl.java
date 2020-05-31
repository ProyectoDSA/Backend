package edu.upc.eetac.dsa.orm.managers;

import edu.upc.eetac.dsa.exceptions.MonedasInsuficientesException;
import edu.upc.eetac.dsa.exceptions.UserAlreadyExistsException;
import edu.upc.eetac.dsa.exceptions.UserNotFoundException;
import edu.upc.eetac.dsa.models.Inventario;
import edu.upc.eetac.dsa.models.Jugador;
import edu.upc.eetac.dsa.models.Objeto;
import edu.upc.eetac.dsa.models.User;
import edu.upc.eetac.dsa.orm.session.FactorySession;
import edu.upc.eetac.dsa.orm.session.Session;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public class GameManagerImpl implements GameManager{

    private static GameManager instance;

    HashMap<String, Jugador> jugadorHashMap;
    HashMap<String, Objeto> objetosHashMap;

    final static Logger log = Logger.getLogger(GameManagerImpl.class.getName());

    private GameManagerImpl(){
        this.jugadorHashMap = new HashMap<>();
        this.objetosHashMap = new HashMap<>();
    }

    public static GameManager getInstance(){
        if (instance==null) instance = new GameManagerImpl();
        return instance;
    }

    @Override
    public HashMap<Integer, Jugador> getRanking() {
        Session session = null;
        HashMap<Integer, Jugador> jugadores=null;
        try {
            session = FactorySession.openSession();
            jugadores = session.findRanking(Jugador.class);
        }
        catch (Exception e) {
            log.warning("Error");
        }
        finally {
            session.close();
        }

        /*for(User u : usersList)
            System.out.println(u.toString());*/
        return jugadores;
    }

    @Override
    public void updateJugador(String idJugador, int puntos, int accion) throws MonedasInsuficientesException {
        Session session = null;
        Jugador j;
        try {
            session = FactorySession.openSession();
            j = (Jugador) session.findByID(Jugador.class, idJugador);
            if(j!=null) {
                if(accion==1){
                    j.setPuntos(j.getPuntos()+puntos);
                    j.setMonedas(j.getMonedas()+puntos);
                }
                else {
                    if(j.getMonedas()<=puntos)
                        throw new MonedasInsuficientesException();
                    else j.setMonedas(j.getMonedas()-puntos);
                }
                session.update(j);
            }
        }
        finally {
            session.close();
        }
    }

    @Override
    public HashMap<Integer,Inventario> getObjetosJugador(String idJugador){
        Session session = null;
        HashMap<Integer, Inventario> objetos=null;
        try {
            session = FactorySession.openSession();
            objetos = session.getObjetosJugador(Inventario.class,idJugador);
        }
        catch (Exception e) {
            log.warning("Error");
        }
        finally {
            session.close();
        }

        /*for(User u : usersList)
            System.out.println(u.toString());*/
        return objetos;
    }

    @Override
    public void updateCantidadObjetoJugador(String idJugador, int idObjeto, int cantidad) throws UserNotFoundException {
        Session session = null;
        HashMap<Integer,Inventario> objetos;
        Inventario i =null;
        try {
            session = FactorySession.openSession();
            objetos = session.getObjetosJugador(Inventario.class,idJugador);
            for(Integer key : objetos.keySet())
                if(key==idObjeto) i=objetos.get(key);
            i.setCantidad(cantidad);
            session.update(i);
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException();
        } finally {
            session.close();
        }
    }
}
