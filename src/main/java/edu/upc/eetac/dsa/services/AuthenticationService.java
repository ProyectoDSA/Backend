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
import java.util.List;

//EN VEZ DE HACER CONSULTAS A LA INSTANCIA, AQUI DEBERE CONSULTARLO EN LA BBDD

@Api(value = "/auth", description = "Endpoint to User Service")
@Path("/auth")
public class AuthenticationService {

    private UserManager auth;

    public AuthenticationService() {
        this.auth = UserManagerImpl.getInstance();
        if (auth.size()==0) {
            this.auth.register("Ivan", "k@gmail", "klsdvf");
            this.auth.register("PEP", "pepe@pepito", "erjfdjsf");
            //this.um.addUser("Ivan","ivan@yahoo.es", "jsdjj");
            //this.um.addUser("Manu", "manu@outlook.es", "jdjfj");
        }
    }

    @POST
    @ApiOperation(value = "register a new User", notes = "Crea un nuevo usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response=User.class),
            @ApiResponse(code = 401, message = "User already exists"),
            @ApiResponse(code = 409, message = "Mail already exists"),
            @ApiResponse(code = 500, message = "Validation Error")

    })

    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response register(RegisterCredentials credentials) {

        if(credentials.getNombre()==null || credentials.getMail()==null || credentials.getPassword()==null) return Response.status(500).build();
        User user = null;
        try {
            user = this.auth.getUserByName(credentials.getNombre());
            this.auth.checkNameRegister(credentials.getNombre());
            this.auth.checkMailRegister(credentials.getMail());
            this.auth.register(credentials.getNombre(), credentials.getMail(), credentials.getPassword());
        } catch (UserNotFoundException e) {
            return Response.status(401).build();
        } catch (UserAlreadyExistsException e) {
            return Response.status(409).build();
        }
        return Response.status(201).entity(user).build();
    }

    @POST
    @ApiOperation(value = "login", notes = "Iniciar sesión")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response=User.class),
            @ApiResponse(code = 404, message = "User not found"),
            @ApiResponse(code = 409, message = "Password Not Match"),
            @ApiResponse(code = 500, message = "Incorrect password")

    })

    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(LoginCredentials credentials) {
        User user = null;
        try {
            if(this.auth.checkNameLogin(credentials.getNombre())){
                if(this.auth.checkPassword(this.auth.getIdUser(credentials.getNombre()), credentials.getPassword())){
                    user = this.auth.login(credentials.getNombre(), credentials.getPassword());
                }
            }
        } catch (UserNotFoundException e) {
            return Response.status(404).build();
        } catch (PasswordDontMatchException e) {
            return Response.status(409).build();
        } catch (Exception e) {
            return Response.status(500).build();
        }

        return Response.status(201).entity(user).build();
    }

}