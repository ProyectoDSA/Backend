package edu.upc.eetac.dsa.orm.managers;

import edu.upc.eetac.dsa.exceptions.PasswordDontMatchException;
import edu.upc.eetac.dsa.exceptions.UserAlreadyExistsException;
import edu.upc.eetac.dsa.exceptions.UserNotFoundException;
import edu.upc.eetac.dsa.orm.session.FactorySession;
import edu.upc.eetac.dsa.orm.session.Session;
import edu.upc.eetac.dsa.models.User;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public class UserManagerImpl implements UserManager{

    private static UserManager instance;

    final static Logger log = Logger.getLogger(UserManagerImpl.class.getName());

    private UserManagerImpl(){}

    public static UserManager getInstance(){
        if (instance==null) instance = new UserManagerImpl();
        return instance;
    }



    //Funcion que obtiene un usuario por su ID
    @Override
    public User getUser(String id) throws UserNotFoundException {
        Session session = null;
        User u = null;
        try {
            session = FactorySession.openSession();
            u = (User) session.findByID(User.class, id);
        } finally {
            session.close();
        }
        if(u!=null)
            return u;
        else
            throw new UserNotFoundException();
    }

    @Override
    public String getIdUser(String name) throws UserNotFoundException {
        String id = null;
        User u = this.getUserByName(name);
        if(u!=null)
            id = u.getId();
        else
            throw new UserNotFoundException();
        return id;
    }

    @Override
    public User getUserByName(String name) throws UserNotFoundException {
        Session session = null;
        User u = null;
        try{
            session = FactorySession.openSession();
            u = (User) session.findByName(User.class, name);
        } finally {
            session.close();
        }

        if(u!=null)
            return u;
        else
            throw new UserNotFoundException();
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
            log.warning("Error");
        }
        finally {
            session.close();
        }

        /*for(User u : usersList)
            System.out.println(u.toString());*/
        return usersList;
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
            log.warning("Error al actualizar");
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
        User user = null;
        Session session = null;
        try {
            user = this.getUser(id);
            session = FactorySession.openSession();
            session.delete(user);
        }
        catch (UserNotFoundException e) {
            log.warning("User not found");
        }
        finally {
            session.close();
        }
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
    public User login(String name, String pswd){
        User u = null;
        try {
            u = this.getUserByName(name);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
        return u;
    }

    public boolean checkNameLogin(String name) throws UserNotFoundException {
        User u = getUserByName(name);
        if(u.getNombre().equals(name) || u.getMail().equals(name)) {
            return true;
        }
        else throw new UserNotFoundException();
    }

    public boolean checkPassword(String id, String pswd) throws PasswordDontMatchException {
        User u = null;
        try {
            u = getUser(id);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
        if(!u.getPassword().equals(pswd)) throw new PasswordDontMatchException();
        else {
            return true;
        }
    }

    public boolean checkNameRegister(String name) throws UserAlreadyExistsException {
        User u = null;
        try {
            u = getUserByName(name);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }

        if(u==null)
            throw new UserAlreadyExistsException();
        else
            return true;
    }

    public boolean checkMailRegister(String mail) throws UserAlreadyExistsException{
        User u = null;
        try {
            u = getUserByName(mail);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }

        if(u==null)
            throw new UserAlreadyExistsException();
        else
            return true;
    }

    @Override
    public int size() {
        return this.getAllUsers().size();
    }
}
