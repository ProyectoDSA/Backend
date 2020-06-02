package edu.upc.eetac.dsa.orm.managers;

import edu.upc.eetac.dsa.exceptions.MonedasInsuficientesException;
import edu.upc.eetac.dsa.exceptions.ObjetoNotFoundException;
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
                if(accion==1){ //GUARDAR NUEVA PUNTUACION Y AÑADIR MONEDAS
                    j.setPuntos(j.getPuntos()+puntos);
                    j.setMonedas(j.getMonedas()+puntos);
                }
                else { //COMPRAR OBJETO (accion=2)
                    if(j.getMonedas()<puntos)
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
    public void addObjetoJugador(Inventario objeto) {
        Session session = null;
        Inventario o = null;
        try {
            session = FactorySession.openSession();
            o = new Inventario(objeto.getIdObjeto(), objeto.getCantidad(), objeto.getIdJugador());
            session.save(o);
        } finally {
            session.close();
        }
    }

    @Override
    public void updateInventario(String idJugador, int idObjeto, int newCantidad, int accion) throws Exception {
        Session session = null;
        HashMap<Integer,Inventario> objetos;
        Inventario i =null;
        try {
            session = FactorySession.openSession();
            objetos = session.getObjetosJugador(Inventario.class, idJugador);
            if (objetos.containsKey(idObjeto))
                i = objetos.get(idObjeto);
            else throw new ObjetoNotFoundException();
            if(accion==1) { //COMPRAR OBJETO
                i.setCantidad(newCantidad);
            } else { //GASTAR OBJETO INVENTARIO (accion=2)
                int cant = i.getCantidad();
                i.setCantidad(cant-1);
            }
            if(i.getCantidad()==0)
                session.delete(i);
            else session.update(i);
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException();
        } finally {
            session.close();
        }
    }

    public int getPrecioObjeto(int idObjeto){
        Session session = null;
        int precio = 0;
        try {
            session = FactorySession.openSession();
            precio = session.getPrecioObjeto(idObjeto);
        } finally {
            session.close();
        }
        return precio;
    }
}
