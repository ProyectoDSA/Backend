package edu.upc.eetac.dsa.services;


import edu.upc.eetac.dsa.exceptions.MonedasInsuficientesException;
import edu.upc.eetac.dsa.exceptions.ObjetoNotFoundException;
import edu.upc.eetac.dsa.models.*;
import edu.upc.eetac.dsa.orm.managers.GameManager;
import edu.upc.eetac.dsa.orm.managers.GameManagerImpl;
import edu.upc.eetac.dsa.orm.session.FactorySession;
import edu.upc.eetac.dsa.orm.session.Session;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javassist.tools.rmi.ObjectNotFoundException;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

//SERVICIO PARA FUNCIONES DEL JUEGO

@Api(value = "/game", description = "API for Game Services")
@Path("/game")
public class GameService {

    private GameManager gm;

    public GameService() {
        this.gm = GameManagerImpl.getInstance();
    }


    //Servicio para añadir partida
    @POST
    @ApiOperation(value = "Añadir partida", notes = "Añade partida al jugador")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 500, message = "Server error")
    })
    @Path("/addpartida")
    public Response addPartida(Partida partida) {
        try {
            this.gm.addPartida(partida);
        } catch (Exception e) {
            return Response.status(500).build();
        }

        return Response.status(200).build();
    }

    //Servicio para obtener el ranking de los mejores jugadores

    @GET
    @ApiOperation(value = "get Ranking", notes = "Obtén el ranking de jugadores")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = JugadorRanking.class, responseContainer="List"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @Path("/ranking")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRanking() {

        HashMap<Integer, JugadorRanking> jugadores = null;
        List<JugadorRanking> j = new LinkedList<>();

        jugadores = this.gm.getRanking();
        for ( int key : jugadores.keySet() ) {
            j.add(jugadores.get(key));
        }

        GenericEntity<List<JugadorRanking>> entity = new GenericEntity<List<JugadorRanking>>(j) {};

        if(entity==null) return Response.status(500).build();
        return Response.status(200).entity(entity).build();
    }

    //Servicio para obtener el ranking de partidas de un jugador

    @GET
    @ApiOperation(value = "get Ranking Partidas", notes = "Obtén las mejores 5 partidas de un jugador")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = RankingPartida.class, responseContainer="List"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @Path("/toppartidas")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRankingPartidas(@QueryParam("token") String token) {

        HashMap<Integer, RankingPartida> partidas = null;
        List<RankingPartida> p = new LinkedList<>();

        partidas = this.gm.getRankingPartidas(token);
        for ( int key : partidas.keySet() ) {
            p.add(partidas.get(key));
        }

        GenericEntity<List<RankingPartida>> entity = new GenericEntity<List<RankingPartida>>(p) {};

        if(entity==null) return Response.status(500).build();
        return Response.status(200).entity(entity).build();
    }

    //Servicio para obtener los puntos de un jugador

    @GET
    @ApiOperation(value = "get puntuación jugador", notes = "Obtén los puntos del jugador")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = JugadorRanking.class),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @Path("/puntosPlayer")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPuntosJugador(@QueryParam("token") String token) {

        JugadorRanking j = null;

        j = this.gm.getJugador(token);

        if(j==null) return Response.status(500).build();
        return Response.status(200).entity(j).build();
    }

    //Servicio que devuelve una lista con los objetos del jugador

    @GET
    @ApiOperation(value = "get objetos jugador", notes = "Obtén los objetos de un jugador")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = Inventario.class, responseContainer="List"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @Path("/objetos")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getObjetosJugador(@QueryParam("token") String token) {

        HashMap<Integer,Inventario> objetos = null;
        List<Inventario> o = new LinkedList<>();

        objetos = this.gm.getObjetosJugador(token);
        for ( Integer key : objetos.keySet() ) {
            o.add(objetos.get(key));
        }

        GenericEntity<List<Inventario>> entity = new GenericEntity<List<Inventario>>(o) {};

        if(entity==null) return Response.status(500).build();
        return Response.status(200).entity(entity).build();
    }

    //Servicio que suma puntos y monedas al finalizar la partida

    @POST
    @ApiOperation(value = "update puntos", notes = "Actualiza la puntuación y las monedas de un jugador al finalizar la partida")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @Path("/puntos")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePuntosAndMonedas(@QueryParam("token") String token, @QueryParam("puntos") int puntos) {
        try{
            this.gm.updateJugador(token,puntos, 1);
        } catch (Exception e){
            return Response.status(500).build();
        }
        return Response.status(200).build();
    }

    //Servicio para comprar objetos

    @POST
    @ApiOperation(value = "comprar objetos", notes = "Añade objetos a inventario")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 400, message = "Not enough coins"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @Path("/compra")
    @Produces(MediaType.APPLICATION_JSON)
    public Response compraObjeto(Inventario objeto) {
        HashMap<Integer,Inventario> objetos;
        Inventario i;
        int idObjeto = objeto.getIdObjeto();
        int cantidad = objeto.getCantidad();
        try {
            int precioObjeto = this.gm.getPrecioObjeto(idObjeto);
            int precioTotal = cantidad*precioObjeto;
            this.gm.updateJugador(objeto.getIdJugador(), precioTotal, 2);
            objetos = this.gm.getObjetosJugador(objeto.getIdJugador());
            if(objetos.containsKey(idObjeto)) {
                i = objetos.get(idObjeto);
                int newCant = i.getCantidad()+cantidad;
                this.gm.updateInventario(objeto.getIdJugador(),i.getIdObjeto(),newCant, 1);
            }
            else
                this.gm.addObjetoJugador(objeto);
        } catch (MonedasInsuficientesException e){
            return Response.status(400).build();
        } catch (Exception e){
            return Response.status(500).build();
        }
        return Response.status(201).build();
    }

    //Servicio que registra el gasto de un objeto

    @POST
    @ApiOperation(value = "usar objeto", notes = "Actualiza la cantidad de un objeto al gastarlo")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "Object not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @Path("/useobject")
    @Produces(MediaType.APPLICATION_JSON)
    public Response gastarObjeto(Inventario objeto) {
        HashMap<Integer,Inventario> objetos;
        Inventario i;
        int idObjeto = objeto.getIdObjeto();
        try {
            objetos = this.gm.getObjetosJugador(objeto.getIdJugador());
            if(objetos.containsKey(idObjeto)) {
                i = objetos.get(idObjeto);
                this.gm.updateInventario(objeto.getIdJugador(),i.getIdObjeto(),0, 2);
            }
            else
                this.gm.addObjetoJugador(objeto);
        } catch (ObjetoNotFoundException e){
            return Response.status(404).build();
        } catch (Exception e){
            return Response.status(500).build();
        }
        return Response.status(201).build();
    }

    //Servicio que obtiene el string de enemigos de un nivel

    @GET
    @ApiOperation(value = "get enemigos", notes = "Obtén el String de enemigos")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = String.class),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @Path("/enemigos")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEnemigos(@QueryParam("idNivel") int idNivel) {

        Nivel enemigos = null;

        enemigos = this.gm.getEnemigos(idNivel);

        if(enemigos==null) return Response.status(500).build();
        return Response.status(200).entity(enemigos).build();
    }

    //Servicio que devuelve una lista con los mapas del juego

    @GET
    @ApiOperation(value = "get mapas", notes = "Obtiene lista con los mapas")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = Mapa.class, responseContainer="List"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @Path("/mapitas")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMapas() {

        HashMap<Integer,Mapa> mapas = null;
        List<Mapa> m = new LinkedList<>();

        mapas = this.gm.getMapas();
        for ( Integer key : mapas.keySet() ) {
            m.add(mapas.get(key));
        }

        GenericEntity<List<Mapa>> entity = new GenericEntity<List<Mapa>>(m) {};

        if(entity==null) return Response.status(500).build();
        return Response.status(200).entity(entity).build();
    }
}