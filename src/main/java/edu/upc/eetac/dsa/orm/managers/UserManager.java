package edu.upc.eetac.dsa.orm.managers;

import edu.upc.eetac.dsa.exceptions.PasswordDontMatchException;
import edu.upc.eetac.dsa.exceptions.UserNotFoundException;
import edu.upc.eetac.dsa.models.User;

import java.util.List;

//Interfaz donde definimos las operaciones CRUD que queremos realizar al usuario
public interface UserManager {

    public String getIdUser(String name);
    public User getUser(String id); //Devuelve un usuario con cierto ID
    public User getUserByName(String name);
    public String addUser(String name, String mail, String password); //Añade un usuario en la tabla User
    public void updateUser(String id, String nombre, String mail, String password); //Actualiza los datos de un usuario
    public User updateUser(User user);
    public void deleteUser(String id); //Elimina usuario de la tabla User
    public List<User> getAllUsers(); //Devuelve una lista con todos los usuarios de la tabla
    public List<User> getUsersConnected();
    public void register(String name, String mail, String password);
    public User login(String name, String password);
    public boolean checkName(String id, String name) throws UserNotFoundException;
    public boolean checkPassword(String name, String password) throws PasswordDontMatchException, UserNotFoundException;


    public int size();
}
