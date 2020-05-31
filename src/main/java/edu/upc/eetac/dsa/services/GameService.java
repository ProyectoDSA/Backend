package edu.upc.eetac.dsa.services;


import edu.upc.eetac.dsa.exceptions.MonedasInsuficientesException;
import edu.upc.eetac.dsa.models.Inventario;
import edu.upc.eetac.dsa.models.Jugador;
import edu.upc.eetac.dsa.models.Objeto;
import edu.upc.eetac.dsa.models.User;
import edu.upc.eetac.dsa.orm.managers.GameManager;
import edu.upc.eetac.dsa.orm.managers.GameManagerImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

//EN VEZ DE HACER CONSULTAS A LA INSTANCIA, AQUI DEBERE CONSULTARLO EN LA BBDD

@Api(value = "/game", description = "Authentication API for Login and Register")
@Path("/game")
public class GameService {

    private GameManager gm;

    public GameService() {
        this.gm = GameManagerImpl.getInstance();
    }

    @GET
    @ApiOperation(value = "get Ranking", notes = "Obtén el ranking de jugadores")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Jugador.class, responseContainer="List"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @Path("/ranking")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRanking() {

        HashMap<Integer, Jugador> jugadores = null;
        List<Jugador> j = new LinkedList<>();

        jugadores = this.gm.getRanking();
        for ( int key : jugadores.keySet() ) {
            j.add(jugadores.get(key));
        }

        GenericEntity<List<Jugador>> entity = new GenericEntity<List<Jugador>>(j) {};

        if(entity==null) return Response.status(500).build();
        return Response.status(201).entity(entity).build();
    }

    @GET
    @ApiOperation(value = "get objetos jugador", notes = "Obtén los objetos de un jugador")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Inventario.class, responseContainer="List"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @Path("/objetos/{idJugador}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getObjetosJugador(@PathParam("idJugador") String idJugador) {

        HashMap<Integer,Inventario> objetos = null;
        List<Inventario> o = new LinkedList<>();

        objetos = this.gm.getObjetosJugador(idJugador);
        for ( Integer key : objetos.keySet() ) {
            o.add(objetos.get(key));
        }

        GenericEntity<List<Inventario>> entity = new GenericEntity<List<Inventario>>(o) {};

        if(entity==null) return Response.status(500).build();
        return Response.status(201).entity(entity).build();
    }

    @PUT
    @ApiOperation(value = "update puntos", notes = "Actualiza la puntuación y las monedas de un jugador al finalizar la partida")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @Path("/puntos/{idJugador}/{puntos}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePuntosAndMonedas(@PathParam("idJugador") String idJugador, @PathParam("puntos") int puntos) {
        try{
            this.gm.updateJugador(idJugador,puntos, 1);
        } catch (Exception e){
            return Response.status(500).build();
        }
        return Response.status(200).build();
    }

    @PUT
    @ApiOperation(value = "pagar objetos", notes = "Actualiza las monedas de un jugador al comprar un objeto")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 400, message = "Not enough coins"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @Path("/compra/{cantidad}/{precio}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response compraObjeto(Inventario objeto, @PathParam("precio") int precio) {
        HashMap<Integer,Inventario> objetos;
        Inventario i;
        try {
            this.gm.updateJugador(objeto.getIdJugador(), precio, 2);
            objetos = this.gm.getObjetosJugador(objeto.getIdJugador());
            if(objetos.containsKey(objeto.getIdObjeto())) {
                i = objetos.get(objeto.getIdObjeto());
                int cant = objeto.getCantidad();
                int newCant = i.getCantidad()+cant;
                this.gm.updateCantidadObjetoJugador(i.getIdJugador(),i.getIdObjeto(),newCant);
            }
            //else
                //this.gm.addObjetoJugador(idJugador,objeto,cantidad);
        } catch (MonedasInsuficientesException e){
            return Response.status(400).build();
        } catch (Exception e){
            return Response.status(500).build();
        }
        return Response.status(200).build();
    }



}