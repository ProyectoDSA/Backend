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
        User u = this.getUserByNameOrMail(name);
        if(u!=null)
            id = u.getId();
        else
            throw new UserNotFoundException();
        return id;
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
    public void register(RegisterCredentials rc) throws Exception {
        Session session = null;
        User u = null;
        try {
            if (checkNameRegister(rc.getNombre()) || checkMailRegister(rc.getNombre())) {
                session = FactorySession.openSession();
                u = new User(rc.getNombre(), rc.getMail(), rc.getPassword());
                session.save(u);
                LoginCredentials lc = new LoginCredentials(u.getNombre(), u.getPassword());
                this.login(lc);
            } else
                throw new PasswordDontMatchException();
        } catch (Exception e){
            throw new UserAlreadyExistsException();
        }finally {
            session.close();
        }
        System.out.println("User "+u.toString()+" registered");
    }

    @Override
    public User login(LoginCredentials lc) throws Exception {
        User u = null;

        if (checkNameLogin(lc.getNombre())) {
            if (checkPassword(getIdUser(lc.getNombre()), lc.getPassword())) {
                u = getUserByNameOrMail(lc.getNombre());
            } else
                throw new PasswordDontMatchException();
        }

        if(u==null) throw new UserNotFoundException();
        return u;
    }

    private boolean checkNameLogin(String name) throws Exception {
        User u = getUserByNameOrMail(name);
        if (u!=null) return true;
        else throw new UserNotFoundException();
    }

    private boolean checkPassword(String id, String pswd) throws Exception {
        User u =  getUser(id);

        if(!u.getPassword().equals(pswd)) throw new PasswordDontMatchException();
        else {
            return true;
        }
    }

    private boolean checkNameRegister(String name) throws Exception {
        User u = getUserByNameOrMail(name);

        if(u==null)
            throw new UserAlreadyExistsException();
        else
            return true;
    }

    private boolean checkMailRegister(String mail) throws Exception{

        User u = getUserByNameOrMail(mail);

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
