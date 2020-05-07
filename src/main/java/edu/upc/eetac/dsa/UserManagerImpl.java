package edu.upc.eetac.dsa;

import edu.upc.eetac.dsa.orm.session.FactorySession;
import edu.upc.eetac.dsa.orm.session.Session;
import edu.upc.eetac.dsa.models.User;

import java.util.List;

public class UserManagerImpl implements UserManager{

    private static UserManager instance;

    private UserManagerImpl(){
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

    //Funcion que crea un usuario y lo añade en la tabla con el nombre
    //y el mail que le enviamos como parametros al hacer el registro
    @Override
    public String addUser(String name, String mail) {
        Session session = null;
        String userID = null;
        try {
            session = FactorySession.openSession();
            User user = new User(name, mail);
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
    public User updateUser(String id, String nombre, String mail) {
        Session session = null;
        try {
            session = FactorySession.openSession();
            User u = (User) session.findByID(User.class, id);
            if(u!=null) {
                u.setNombre(nombre);
                u.setMail(mail);
                session.update(u);
                return u;
            }
        }
        catch (Exception e) {
            // LOG
        }
        finally {
            session.close();
        }
        return null;
    }

    @Override
    public User updateUser(User user) {
        this.updateUser(user.getId(),user.getNombre(),user.getMail());
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
    public int size(){
        return this.getAllUsers().size();
    }
}
