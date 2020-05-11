package edu.upc.eetac.dsa.orm.managers;

import edu.upc.eetac.dsa.exceptions.PasswordDontMatchException;
import edu.upc.eetac.dsa.exceptions.UserNotFoundException;
import edu.upc.eetac.dsa.orm.session.FactorySession;
import edu.upc.eetac.dsa.orm.session.Session;
import edu.upc.eetac.dsa.models.User;

import java.util.HashMap;
import java.util.List;

public class UserManagerImpl implements UserManager{

    private static UserManager instance;
    private HashMap<String, User> users;

    private UserManagerImpl(){
        this.users = new HashMap<>();
    }

    public static UserManager getInstance(){
        if (instance==null) instance = new UserManagerImpl();
        return instance;
    }

    //Funcion que obtiene un usuario por su ID
    @Override
    public User getUser(String id){
        Session session = null;
        User u = null;
        try{
            session = FactorySession.openSession();
            u = (User) session.findByID(User.class, id);
        } catch (Exception e){
            e.printStackTrace();
        }
        return u;
    }

    @Override
    public User getUserByName(String name){
        Session session = null;
        User u = null;
        try{
            session = FactorySession.openSession();
            u = (User) session.findByName(User.class, name);
        } catch (Exception e){
            e.printStackTrace();
        }
        return u;
    }

    //Funcion que crea un usuario y lo añade en la tabla con el nombre
    //y el mail que le enviamos como parametros al hacer el registro
    @Override
    public String addUser(String name, String mail, String password) {
        Session session = null;
        String userID = null;
        try {
            session = FactorySession.openSession();
            User user = new User(name, mail, password);
            userID = user.getId();
            session.save(user);
        }
        catch (Exception e) {
            // LOG
            e.printStackTrace();
        }
        finally {
            session.close();
        }

        return userID;
    }

    //Funcion que actualiza los datos de un usuario
    //NO PERMITE ACTUALIZAR SU ID!! Es la forma de buscarlo
    @Override
    public void updateUser(String id, String nombre, String mail, String password) {
        Session session = null;
        try {
            session = FactorySession.openSession();
            User u = (User) session.findByID(User.class, id);
            if(u!=null) {
                u.setNombre(nombre);
                u.setMail(mail);
                u.setPassword(password);
                session.update(u);
            }
        }
        catch (Exception e) {
            // LOG
        }
        finally {
            session.close();
        }
    }

    @Override
    public User updateUser(User user) {
        this.updateUser(user.getId(),user.getNombre(),user.getMail(), user.getPassword());
        return user;
    }

    //Funcion que elimina al usuario con el ID que le pasamos
    @Override
    public void deleteUser(String id) {
        User user = this.getUser(id);
        Session session = null;
        try {
            session = FactorySession.openSession();
            session.delete(user);
        }
        catch (Exception e) {
            // LOG
            e.printStackTrace();
        }
        finally {
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        Session session = null;
        List<User> usersList=null;
        try {
            session = FactorySession.openSession();
            usersList = session.findAll(User.class);
        }
        catch (Exception e) {
            // LOG
            e.printStackTrace();
        }
        finally {
            session.close();
        }

        /*for(User u : usersList)
            System.out.println(u.toString());*/
        return usersList;
    }

    @Override
    public List<User> getUsersConnected() {
        Session session = null;
        List<User> usersList=null;
        for(User u : users.values()){
            usersList.add(u);
        }
        return usersList;
    }

    @Override
    public void register(String name, String mail, String pswd){
        Session session = null;
        User u = null;
        try {
            session = FactorySession.openSession();
            u = new User(name, mail, pswd);
            session.save(u);
            this.login(u.getNombre(), u.getPassword());
        } finally {
            session.close();
        }
        System.out.println("User "+u.toString()+" registered");
    }

    @Override
    public String getIdUser(String name) {
        String id = null;
        User u = this.getUserByName(name);
        if(u!=null)
            id = u.getId();
        return id;
    }

    @Override
    public User login(String name, String pswd){
        Session session=null;
        User u = null;
        boolean logged = false;
        try{
            session = FactorySession.openSession();
            u = this.getUser(getIdUser(name));
            if(checkName(u.getId(),name)) {
                if (checkPassword(u.getId(), pswd)){
                    this.users.put(u.getId(), u);
                    logged=true;
                }
            }
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        } catch (PasswordDontMatchException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return u;
    }

    @Override
    public boolean checkName(String id, String name) throws UserNotFoundException {
        User u = getUser(id);
        if(u.getNombre().equals(name) || u.getMail().equals(name)) {
            return true;
        }
        else throw new UserNotFoundException();
    }

    @Override
    public boolean checkPassword(String id, String pswd) throws PasswordDontMatchException {
        User u = getUser(id);
        if(!u.getPassword().equals(pswd)) throw new PasswordDontMatchException();
        else {
            return true;
        }
    }

    @Override
    public int size() {
        return this.getAllUsers().size();
    }
}
