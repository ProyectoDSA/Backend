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
import java.util.List;

//EN VEZ DE HACER CONSULTAS A LA INSTANCIA, AQUI DEBERE CONSULTARLO EN LA BBDD

@Api(value = "/auth", description = "Authentication API for Login and Register")
@Path("/auth")
public class AuthenticationService {

    private UserManager auth;

    public AuthenticationService() {
        this.auth = UserManagerImpl.getInstance();
    }

    @POST
    @ApiOperation(value = "register a new User", notes = "Crea un nuevo usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response=String.class),
            @ApiResponse(code = 400, message = "Password don't match"),
            @ApiResponse(code = 409, message = "User already exists"),
            @ApiResponse(code = 500, message = "Validation Error")

    })

    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response register(RegisterCredentials credentials) {
        TokenStorage token = null;
        if(credentials.getNombre()==null || credentials.getMail()==null || credentials.getPassword()==null)
            return Response.status(500).build();
        try {
            if (credentials.getPassword().equals(credentials.getConfirm())) {
                this.auth.register(credentials);
                LoginCredentials lc = new LoginCredentials(credentials.getNombre(),credentials.getPassword());
                token = this.auth.login(lc);
                System.out.println("PREGUNTAR COMO HACER TOKEN DEL LOGIN!!!");
            }
            else
                return Response.status(400).build();
        } catch (Exception e) {
            return Response.status(409).build();
        }
        return Response.status(201).entity(token).build();
    }

    @POST
    @ApiOperation(value = "login", notes = "Iniciar sesión")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response=String.class),
            @ApiResponse(code = 404, message = "User not found"),
            @ApiResponse(code = 409, message = "Password Not Match"),
            @ApiResponse(code = 500, message = "Authentication error")

    })

    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(LoginCredentials credentials) {
        TokenStorage token = null;
        if(credentials.getNombre()==null || credentials.getPassword()==null)
            return Response.status(500).build();
        try {
            token = this.auth.login(credentials);
        } catch (PasswordDontMatchException e) {
            return Response.status(409).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(404).build();
        }

        return Response.status(200).entity(token).build();
    }

    @DELETE
    @ApiOperation(value = "delete a User", notes = "Elimina un usuario mediante su id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 404, message = "User not found")
    })
    @Path("/delete/{idUser}")
    public Response deleteUser(@PathParam("idUser") String id) {
        User u = null;
        try {
            u = this.auth.getUser(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (u == null) return Response.status(404).build();
        else {
            try {
                this.auth.deleteUser(id);
            } catch (UserNotFoundException e) {
                e.printStackTrace();
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
    @Path("/signout/{token}")
    public Response signOut(@PathParam("token") String token) {
        try {
            this.auth.deleteToken(token);
        } catch (Exception e) {
            return Response.status(500).build();
        }

        return Response.status(200).build();
    }

}