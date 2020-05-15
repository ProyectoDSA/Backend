package edu.upc.eetac.dsa.orm.managers;

import edu.upc.eetac.dsa.exceptions.PasswordDontMatchException;
import edu.upc.eetac.dsa.exceptions.UserAlreadyExistsException;
import edu.upc.eetac.dsa.exceptions.UserNotFoundException;
import edu.upc.eetac.dsa.models.User;

import java.util.List;

//Interfaz donde definimos las operaciones CRUD que queremos realizar al usuario
public interface UserManager {

    public User getUser(String id) throws UserNotFoundException; //Devuelve un usuario con cierto ID
    public String getIdUser(String name) throws UserNotFoundException;
    public User getUserByName(String name) throws UserNotFoundException;
    public List<User> getAllUsers(); //Devuelve una lista con todos los usuarios de la tabla

    public void updateUser(String id, String nombre, String mail, String password); //Actualiza los datos de un usuario
    public User updateUser(User user);

    public void deleteUser(String id); //Elimina usuario de la tabla User

    public void register(String name, String mail, String password);

    public User login(String name, String password);

    public boolean checkNameLogin(String name) throws UserNotFoundException;
    public boolean checkPassword(String id, String pswd) throws PasswordDontMatchException;
    public boolean checkNameRegister(String name) throws UserAlreadyExistsException;
    public boolean checkMailRegister(String mail) throws UserAlreadyExistsException;

    public int size();
}
