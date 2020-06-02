package edu.upc.eetac.dsa.orm.managers;

import edu.upc.eetac.dsa.exceptions.PasswordDontMatchException;
import edu.upc.eetac.dsa.exceptions.UserAlreadyExistsException;
import edu.upc.eetac.dsa.exceptions.UserNotFoundException;
import edu.upc.eetac.dsa.models.*;

import java.util.HashMap;
import java.util.List;

//Interfaz donde definimos las operaciones CRUD que queremos realizar al usuario
public interface UserManager {

    //PASAR ESTAS FUNCIONES A SESSION PARA HACERLO EN GENERAL
    public User getUser(String id) throws Exception; //Devuelve un usuario con cierto ID
    public String getIdUser(String name) throws Exception;
    public User getUserByNameOrMail(String name) throws Exception;
    public HashMap<String,User> getAllUsers(); //Devuelve una lista con todos los usuarios de la tabla
    //**************************************************************************************

    //public void updateUser(String id, String nombre, String mail, String password); //Actualiza los datos de un usuario
    //public User updateUser(User user);

    public void deleteUser(String id) throws UserNotFoundException; //Elimina usuario de la tabla User

    public void register(RegisterCredentials rc) throws Exception;
    public TokenStorage login(LoginCredentials lc) throws Exception;
    public String createToken(User user);
    public void deleteToken(String token);

    public void createJugador(String id);

    public int size();
}
