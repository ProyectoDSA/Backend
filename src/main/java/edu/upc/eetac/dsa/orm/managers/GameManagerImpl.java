package edu.upc.eetac.dsa.orm.managers;

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
    public HashMap<String, Jugador> getRanking() {
        Session session = null;
        HashMap<String, Jugador> jugadores=null;
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
    public void updatePuntosJugador(String idJugador, int puntos) {
        Session session = null;
        try {
            session = FactorySession.openSession();
            Jugador j = (Jugador) session.findByID(Jugador.class, idJugador);
            if(j!=null) {
                j.setPuntos(j.getPuntos()+puntos);
                j.setMonedas(j.getMonedas()+puntos);
                session.update(j);
            }
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            session.close();
        }
    }

    @Override
    public void updateMonedasJugador(String idJugador, int puntos) {
        Session session = null;
        try {
            session = FactorySession.openSession();
            Jugador j = (Jugador) session.findByID(Jugador.class, idJugador);
            if(j!=null) {
                j.setPuntos(j.getPuntos()+puntos);
                j.setMonedas(j.getMonedas()+puntos);
                session.update(j);
            }
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            session.close();
        }
    }

    @Override
    public void updateJugador(String idJugador, int puntos, int accion) {
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
                    j.setMonedas(j.getMonedas()-puntos);
                }
                session.update(j);
            }
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            session.close();
        }
    }

}
