package edu.upc.eetac.dsa.services;


import com.fasterxml.jackson.databind.util.JSONPObject;
import edu.upc.eetac.dsa.exceptions.PasswordDontMatchException;
import edu.upc.eetac.dsa.exceptions.UserNotFoundException;
import edu.upc.eetac.dsa.models.LoginCredentials;
import edu.upc.eetac.dsa.models.RegisterCredentials;
import edu.upc.eetac.dsa.orm.managers.UserManager;
import edu.upc.eetac.dsa.orm.managers.UserManagerImpl;
import edu.upc.eetac.dsa.models.User;
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

@Api(value = "/usermanager", description = "Endpoint to User Service")
@Path("/usermanager")
public class UserService {

    private UserManager um;
    private HashMap<String,User> users;

    public UserService() {
        this.um = UserManagerImpl.getInstance();
        //if (um.size()==0) {
            //this.um.register("Ivan", "k@gmail", "klsdvf");
            //this.um.register("PEP", "pepe@pepito", "erjfdjsf");
            //this.um.addUser("Ivan","ivan@yahoo.es", "jsdjj");
            //this.um.addUser("Manu", "manu@outlook.es", "jdjfj");
       // }
    }

    @GET
    @ApiOperation(value = "get all Users", notes = "Devuelve una lista con todos los usuarios")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = User.class, responseContainer="List"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers() {

        HashMap<String,User> users = null;
        List<User> u = new LinkedList<>();

        users = this.um.getAllUsers();
        for ( String key : users.keySet() ) {
            u.add(users.get(key));
        }

        GenericEntity<List<User>> entity = new GenericEntity<List<User>>(u) {};

        if(entity==null) return Response.status(500).build();
        return Response.status(201).entity(entity).build();
    }

    @GET
    @ApiOperation(value = "get a User", notes = "Devuelve el usuario que tenga el id que quieras buscar")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = User.class),
            @ApiResponse(code = 404, message = "User not found")
    })
    @Path("/user/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("id") String id) { //PORQUE NO VA SI ID = /OSU?N ?????
        User u = null;
        try {
            u = this.um.getUser(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (u == null) return Response.status(404).build();
        else  return Response.status(201).entity(u).build();
    }

    @DELETE
    @ApiOperation(value = "delete a User", notes = "Elimina un usuario mediante su id")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "User not found")
    })
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") String id) {
        User u = null;
        try {
            u = this.um.getUser(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (u == null) return Response.status(404).build();
        else {
            try {
                this.um.deleteUser(id);
            } catch (UserNotFoundException e) {
                e.printStackTrace();
            }
        }
        return Response.status(201).build();
    }

    /*@PUT
    @ApiOperation(value = "update a User", notes = "Actualiza los datos de un usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "User not found")
    })
    @Path("/")
    public Response updateUser(User user) {

        User u = this.um.updateUser(user);

        if (u == null) return Response.status(404).build();

        return Response.status(201).build();
    }*/



    @POST
    @ApiOperation(value = "register a new User", notes = "Crea un nuevo usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response=User.class),
            @ApiResponse(code = 500, message = "Validation Error")

    })

    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response register(RegisterCredentials credentials) throws Exception {

        if(credentials.getNombre()==null || credentials.getMail()==null || credentials.getPassword()==null) return Response.status(500).build();
        this.um.register(credentials);
        User user = null;
        try {
            user = this.um.getUserByNameOrMail(credentials.getNombre());
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
        return Response.status(201).entity(user).build();
    }

    @POST
    @ApiOperation(value = "login", notes = "Iniciar sesión")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response=User.class),
            @ApiResponse(code = 404, message = "User not found"),
            @ApiResponse(code = 401, message = "Password Not Match"),
            @ApiResponse(code = 500, message = "Incorrect password")

    })

    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(LoginCredentials credentials) {
        User user = null;
        try {
            user = this.um.login(credentials);
            //this.um.checkName(user.getId(), nombre);
            //this.um.checkPassword(user.getId(), password);
        } catch (Exception e) {
            return Response.status(500).build();
        }
        return Response.status(201).entity(user).build();
    }

}