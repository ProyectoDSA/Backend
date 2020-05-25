package edu.upc.eetac.dsa.orm.managers;

import edu.upc.eetac.dsa.exceptions.PasswordDontMatchException;
import edu.upc.eetac.dsa.exceptions.UserAlreadyExistsException;
import edu.upc.eetac.dsa.exceptions.UserNotFoundException;
import edu.upc.eetac.dsa.models.LoginCredentials;
import edu.upc.eetac.dsa.models.RegisterCredentials;
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
    public User getUser(String idUser) throws UserNotFoundException {
        Session session = null;
        User u = null;
        try {
            session = FactorySession.openSession();
            u = (User) session.findByID(User.class, idUser);
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
        String idUser = null;
        User u = this.getUserByNameOrMail(name);
        if(u!=null)
            idUser = u.getIdUser();
        else
            throw new UserNotFoundException();
        return idUser;
    }

    @Override
    public User getUserByNameOrMail(String name) throws UserNotFoundException {
        Session session = null;
        User u = null;
        try{
            session = FactorySession.openSession();
            u = (User) session.findByNameOrMail(User.class, name);
        } finally {
            session.close();
        }

        if(u!=null)
            return u;
        else
            throw new UserNotFoundException();
    }

    @Override
    public HashMap<String,User> getAllUsers() {
        Session session = null;
        HashMap<String,User> usersList=null;
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
    public void updateUser(String idUser, String nombre, String mail, String password) {
        Session session = null;
        try {
            session = FactorySession.openSession();
            User u = (User) session.findByID(User.class, idUser);
            if(u!=null) {
                u.setNombre(nombre);
                u.setMail(mail);
                u.setPassword(password);
                session.update(u);
            }
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            session.close();
        }
    }

    @Override
    public User updateUser(User user) {
        this.updateUser(user.getIdUser(),user.getNombre(),user.getMail(), user.getPassword());
        return user;
    }

    //Funcion que elimina al usuario con el ID que le pasamos
    @Override
    public void deleteUser(String idUser) throws UserNotFoundException {
        User user = null;
        Session session = null;
        try {
            user = this.getUser(idUser);
            session = FactorySession.openSession();
            session.delete(user);
        }
        catch (UserNotFoundException e) {
            log.warning("User not found");
            throw e;
        }
        finally {
            session.close();
        }
    }

    @Override
    public void register(RegisterCredentials rc) throws Exception {
        Session session = null;
        User u = null;
        try {
            if (checkNameRegister(rc.getNombre())) {
                session = FactorySession.openSession();
                u = new User(rc.getNombre(), rc.getMail(), rc.getPassword());
                session.save(u);
            } else
                throw new UserAlreadyExistsException();
        } catch (Exception e){
            throw e;
        }finally {
            session.close();
        }
        System.out.println("User "+u.toString()+" registered");
    }

    private boolean checkNameRegister(String name) {

        User u = null;
        try {
            u = getUserByNameOrMail(name);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }

        if(u==null)
            return false;
        else
            return true;
    }

    @Override
    public User login(LoginCredentials lc) throws UserNotFoundException, PasswordDontMatchException {
        User u = null;

        try{
            if (checkNameLogin(lc.getNombre())) {
                if (checkPswdLogin(getIdUser(lc.getNombre()), lc.getPassword())) {
                    u = getUserByNameOrMail(lc.getNombre());
                }
            }
        } catch (UserNotFoundException e) {
            throw e;
        } catch (PasswordDontMatchException e) {
            throw e;
        }

        return u;
    }

    private boolean checkNameLogin(String name) throws UserNotFoundException {

        try {
            if(getUserByNameOrMail(name)!=null) return true;
        } catch (UserNotFoundException e) {
            throw e;
        }
        return false;
    }

    private boolean checkPswdLogin(String idUser, String pswd) throws PasswordDontMatchException {
        User u = null;

        try {
            u = getUser(idUser);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }

        if(u.getPassword().equals(pswd)) return true;
        else throw new PasswordDontMatchException();
    }

    @Override
    public int size() {
        Session session;
        HashMap<String,User> users = null;
        try{
            session = FactorySession.openSession();
            users = session.findAll(User.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users.size();
    }
}
