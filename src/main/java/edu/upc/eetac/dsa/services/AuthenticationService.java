package edu.upc.eetac.dsa.services;


import edu.upc.eetac.dsa.exceptions.PasswordDontMatchException;
import edu.upc.eetac.dsa.exceptions.UserAlreadyExistsException;
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
            @ApiResponse(code = 201, message = "Successful", response=User.class),
            @ApiResponse(code = 400, message = "Password don't match"),
            @ApiResponse(code = 409, message = "User already exists"),
            @ApiResponse(code = 500, message = "Validation Error")

    })

    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response register(RegisterCredentials credentials) {
        System.out.println("estoy aqui"+ credentials);
        if(credentials.getNombre()==null || credentials.getMail()==null || credentials.getPassword()==null)
            return Response.status(500).build();
        try {
            if (credentials.getPassword().equals(credentials.getConfirm()))
                this.auth.register(credentials);
            else
                return Response.status(400).build();
        } catch (Exception e) {
            return Response.status(409).build();
        }
        return Response.status(201).entity(credentials).build();
    }

    @POST
    @ApiOperation(value = "login", notes = "Iniciar sesión")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response=User.class),
            @ApiResponse(code = 404, message = "User not found"),
            @ApiResponse(code = 409, message = "Password Not Match"),
            @ApiResponse(code = 500, message = "Authentication error")

    })

    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(LoginCredentials credentials) {
        User user = null;
        System.out.println("estoy aqui"+ credentials);
        if(credentials.getNombre()==null || credentials.getPassword()==null)
            return Response.status(500).build();
        try {
            user = this.auth.login(credentials);
        } catch (PasswordDontMatchException e) {
            return Response.status(409).build();
        } catch (Exception e) {
            return Response.status(404).build();
        }

        return Response.status(201).entity(user).build();
    }

}