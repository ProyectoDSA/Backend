package edu.upc.eetac.dsa.orm.managers;

import edu.upc.eetac.dsa.exceptions.MonedasInsuficientesException;
import edu.upc.eetac.dsa.exceptions.ObjetoNotFoundException;
import edu.upc.eetac.dsa.exceptions.UserNotFoundException;
import edu.upc.eetac.dsa.models.*;
import edu.upc.eetac.dsa.orm.session.FactorySession;
import edu.upc.eetac.dsa.orm.session.Session;


import java.util.HashMap;
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
    public void addPartida(Partida partida) {
        Session session = null;
        Partida p;
        try{
            session = FactorySession.openSession();
            String id = session.findIDByToken(partida.getIdJugador());
            p = new Partida(0, id, partida.getDuracion(), partida.getPuntos(), partida.getNivelMax());
            session.save(p);
        } finally {
            session.close();
        }
    }

    @Override
    public JugadorRanking getJugador(String token) {
        JugadorRanking jugador = null;
        Jugador j;
        User u = null;
        Session session=null;
        String id;
        try{
            session = FactorySession.openSession();
            id = session.findIDByToken(token);
            u = (User) session.findByID(User.class, id);
            j = (Jugador) session.findByID(Jugador.class, id);
            jugador = new JugadorRanking(u.getNombre(),j.getPuntos());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return jugador;
    }

    @Override
    public HashMap<Integer, JugadorRanking> getRanking() {
        Session session = null;
        HashMap<Integer, JugadorRanking> jugadores = new HashMap<>();
        HashMap<Integer, Jugador> j=null;

        try {
            session = FactorySession.openSession();
            j = session.findRanking(Jugador.class, null);
            for(Integer key : j.keySet()){
                JugadorRanking jugador = JugadorRanking.class.newInstance();
                jugador.setPuntos(j.get(key).getPuntos());
                User user = (User) session.findByID(User.class,j.get(key).getIdJugador());
                jugador.setUsername(user.getNombre());
                jugadores.put(key,jugador);
            }

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
    public HashMap<Integer, RankingPartida> getRankingPartidas(String token) {
        Session session = null;
        HashMap<Integer, RankingPartida> partidas = new HashMap<>();
        HashMap<Integer, Partida> p=null;

        try {
            session = FactorySession.openSession();
            String id = session.findIDByToken(token);
            p = session.findRanking(Partida.class, id);
            for(Integer key : p.keySet()){
                RankingPartida partida = RankingPartida.class.newInstance();
                partida.setPuntos(p.get(key).getPuntos());
                partida.setDuracion(p.get(key).getDuracion());
                partida.setNivelMax(p.get(key).getNivelMax());
                partidas.put(key,partida);
            }

        }
        catch (Exception e) {
            log.warning("Error");
        }
        finally {
            session.close();
        }

        /*for(User u : usersList)
            System.out.println(u.toString());*/
        return partidas;
    }

    @Override
    public void updateJugador(String token, int puntos, int accion) throws MonedasInsuficientesException {
        Session session = null;
        Jugador j;
        try {
            session = FactorySession.openSession();
            String idJugador = session.findIDByToken(token);
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
    public HashMap<Integer,Inventario> getObjetosJugador(String token){
        String idJugador;
        Session session = null;
        HashMap<Integer, Inventario> objetos=null;
        try {
            session = FactorySession.openSession();
            idJugador = session.findIDByToken(token);
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
            String idJugador = session.findIDByToken(objeto.getIdJugador());
            o = new Inventario(objeto.getIdObjeto(), objeto.getCantidad(), idJugador);
            session.save(o);
        } finally {
            session.close();
        }
    }

    @Override
    public void updateInventario(String token, int idObjeto, int newCantidad, int accion) throws Exception {
        Session session = null;
        HashMap<Integer,Inventario> objetos;
        String idJugador;
        Inventario i =null;
        try {
            session = FactorySession.openSession();
            idJugador = session.findIDByToken(token);
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
