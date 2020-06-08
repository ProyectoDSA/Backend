package edu.upc.eetac.dsa.services;

import edu.upc.eetac.dsa.exceptions.PasswordDontMatchException;
import edu.upc.eetac.dsa.exceptions.UserAlreadyExistsException;
import edu.upc.eetac.dsa.exceptions.UserNotFoundException;
import edu.upc.eetac.dsa.models.*;
import edu.upc.eetac.dsa.orm.managers.UserManager;
import edu.upc.eetac.dsa.orm.managers.UserManagerImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

//EN VEZ DE HACER CONSULTAS A LA INSTANCIA, AQUI DEBERE CONSULTARLO EN LA BBDD

@Api(value = "/user", description = "Authentication API for Login and Register")
@Path("/user")
public class UserService {

    private UserManager auth;

    public UserService() {
        this.auth = UserManagerImpl.getInstance();
    }

    @DELETE
    @ApiOperation(value = "delete a User", notes = "Elimina un usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 404, message = "User not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @Path("/delete")
    public Response deleteUser(@QueryParam("token") String token) {
        User u = null;
        try {
            u = this.auth.getUser(token);
        } catch (Exception e) {
            return Response.status(404).build();
        }
        if (u != null) {
            try {
                this.auth.deleteUser(token);
            } catch (UserNotFoundException e) {
                return Response.status(500).build();
            }
        }
        return Response.status(200).build();
    }

    @DELETE
    @ApiOperation(value = "Sign out", notes = "Cierra sesion y elimina el token correspondiente")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 500, message = "Server error")
    })
    @Path("/signout")
    public Response signOut(@QueryParam("token") String token) {
        try {
            this.auth.deleteToken(token);
        } catch (Exception e) {
            return Response.status(500).build();
        }

        return Response.status(200).build();
    }

    @POST
    @ApiOperation(value = "Crear comentario", notes = "Cierra sesion y elimina el token correspondiente")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 500, message = "Server error")
    })
    @Path("/newcomment")
    public Response addComment(@QueryParam("token") String token, Comentario comentario) {
        try {
            this.auth.addComment(token,comentario);
        } catch (Exception e) {
            return Response.status(500).build();
        }

        return Response.status(200).build();
    }

    @GET
    @ApiOperation(value = "get comentarios foro", notes = "Obtén los comentarios del foro")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = Foro.class, responseContainer="List"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @Path("/comments")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getComentarios() {

        HashMap<String,Foro> comentarios = null;
        List<Foro> c = new LinkedList<>();

        comentarios = this.auth.getComments();
        for ( String key : comentarios.keySet() ) {
            c.add(comentarios.get(key));
        }

        GenericEntity<List<Foro>> entity = new GenericEntity<List<Foro>>(c) {};

        if(entity==null) return Response.status(500).build();
        return Response.status(200).entity(entity).build();
    }

    @GET
    @ApiOperation(value = "get user", notes = "Obtén los datos de un usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = User.class),
            @ApiResponse(code = 404, message = "User not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @Path("/user")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@QueryParam("token") String token) {

        User u = null;

        try {

            u = this.auth.getUser(token);
        } catch (Exception e) {
            return Response.status(404).build();
        }

        if(u==null) return Response.status(500).build();
        return Response.status(200).entity(u).build();
    }

    @POST
    @ApiOperation(value = "update user", notes = "Actualiza los parametros de un usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 404, message = "User not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @Path("/update")
    public Response updateUser(User user){
        if(user==null) return Response.status(500).build();
        try {
            this.auth.updateUser(user);
        } catch (UserNotFoundException e) {
            return Response.status(404).build();
        }
        return Response.status(200).build();
    }
}
