package edu.upc.eetac.dsa.orm.managers;

import edu.upc.eetac.dsa.models.Partida;

import java.util.List;
import java.util.logging.Logger;

public class GameManagerImpl implements GameManager{

    private static GameManager instance;

    final static Logger log = Logger.getLogger(GameManagerImpl.class.getName());

    private GameManagerImpl(){}

    public static GameManager getInstance(){
        if (instance==null) instance = new GameManagerImpl();
        return instance;
    }

    @Override
    public Partida getPartida(String idPartida) {
        return null;
    }

    @Override
    public String getIdJugador(String idPartida) {
        return null;
    }

    @Override
    public int getPuntuacion(String idPartida) {
        return 0;
    }
}
