package edu.upc.eetac.dsa.services;


import edu.upc.eetac.dsa.models.Jugador;
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

        HashMap<String, Jugador> jugadores = null;
        List<Jugador> j = new LinkedList<>();
        List<Jugador> ranking = new LinkedList<>();

        jugadores = this.gm.getRanking();
        for ( String key : jugadores.keySet() ) {
            j.add(jugadores.get(key));
        }

        GenericEntity<List<Jugador>> entity = new GenericEntity<List<Jugador>>(j) {};

        if(entity==null) return Response.status(500).build();
        return Response.status(201).entity(entity).build();
    }

}