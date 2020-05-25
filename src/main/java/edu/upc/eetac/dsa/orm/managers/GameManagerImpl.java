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

    /*public int sizeJugadores(){
        int res = this.jugadorHashMap.size();
        log.info("Jugadores encontrados: "+res);
        return res;
    }

    public int sizeObjects(){
        int res = this.objetosHashMap.size();
        log.info("Objetos encontrados: "+res);
        return res;
    }

    public Inventario getInventario(String idJugador) throws UserNotFoundException {
        Jugador jugador = this.jugadorHashMap.get(idJugador);
        if(jugador==null) throw new UserNotFoundException();
        Inventario inv = this.getObjetosJugador();
        return inv;
    }

    private Inventario getObjetosJugador() {
        Session session = null;
        return null;
    }*/

    @Override
    public HashMap<String, Jugador> getRanking() {
        Session session = null;
        HashMap<String, Jugador> jugadores=null;
        try {
            session = FactorySession.openSession();
            jugadores = session.findAll(Jugador.class);
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
}
