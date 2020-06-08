package edu.upc.eetac.dsa.orm.managers;

import edu.upc.eetac.dsa.exceptions.PasswordDontMatchException;
import edu.upc.eetac.dsa.exceptions.UserAlreadyExistsException;
import edu.upc.eetac.dsa.exceptions.UserNotFoundException;
import edu.upc.eetac.dsa.models.*;
import edu.upc.eetac.dsa.orm.session.FactorySession;
import edu.upc.eetac.dsa.orm.session.Session;
import org.apache.commons.codec.digest.DigestUtils;
import org.joda.time.LocalDate;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
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
        String id;
        try {
            session = FactorySession.openSession();
            if(idUser.length()>6)
                id = session.findIDByToken(idUser);
            else id = idUser;
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

        if(u==null)
            throw new UserNotFoundException();
        else
            return u;
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

        return usersList;
    }

    @Override
    public void updateUser(String token, String nombre, String mail, String password) throws UserNotFoundException {
        Session session = null;
        User u = null;
        try {
            session = FactorySession.openSession();
            String id = session.findIDByToken(token);
            u = (User) session.findByID(User.class, id);
            u.setNombre(nombre);
            u.setMail(mail);
            if(password!=null) //Si no la cambia, el parametro es null y no lo cambiamos
                u.setPassword(code(password));
            session.update(u);
        } catch(Exception e) {
            throw new UserNotFoundException();
        }finally {
            session.close();
        }
    }

    @Override
    public void updateUser(User user) throws UserNotFoundException {
        this.updateUser(user.getIdUser(), user.getNombre(), user.getMail(), user.getPassword());
    }

    //Funcion que elimina al usuario con el ID que le pasamos
    @Override
    public void deleteUser(String token) throws UserNotFoundException {
        User user = null;
        Jugador jugador = null;
        Session session = null;
        try {
            session = FactorySession.openSession();
            String idUser = session.findIDByToken(token);
            user = this.getUser(idUser);
            jugador = (Jugador) session.findByID(Jugador.class, idUser);
            session.delete(user);
            session.delete(jugador);
        }
        catch (UserNotFoundException e) {
            log.warning("User not found");
            throw e;
        }
        finally {
            session.close();
        }
    }

    //SELECT * FROM User WHERE (NOMBRE=? OR MAIL=?) AND status="active";
    //SELECT * FROM usuario, * FROM jugador WHERE usuario.idUser = jugador.idJugador AND usuario.status='active';

    @Override
    public void register(RegisterCredentials rc) throws Exception {
        Session session = null;
        User u = null;
        try {
            if (checkNameRegister(rc.getNombre())) {
                session = FactorySession.openSession();
                String pswd = code(rc.getPassword());
                u = new User(rc.getNombre(), rc.getMail(), pswd);
                session.save(u);
                this.createJugador(u.getIdUser());
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

    //Funcion que codifica la contraseña para que no se muestre en claro
    private String code(String pswd) {
        return DigestUtils.md5Hex(pswd);
    }

    @Override
    public TokenStorage login(LoginCredentials lc) throws UserNotFoundException, PasswordDontMatchException {
        User u;
        String token = null;

        try{
            if (checkNameLogin(lc.getNombre())) {
                if (checkPswdLogin(getIdUser(lc.getNombre()), lc.getPassword())) {
                    u = getUserByNameOrMail(lc.getNombre());
                    token = this.createToken(u);
                }
            }
        } catch (UserNotFoundException e) {
            throw e;
        } catch (PasswordDontMatchException e) {
            throw e;
        }

        TokenStorage t = new TokenStorage(token);
        return t;
    }

    public String createToken(User user) {
        Session session = null;
        Token t = null;
        HashMap<String, Token> tokens;
        try {
            session = FactorySession.openSession();
            tokens = session.findAll(Token.class);
            if (tokens.containsKey(user.getIdUser())){
                t = tokens.get(user.getIdUser());
                session.deleteToken(t.getToken());
                t = new Token(user.getIdUser());
                session.save(t);
            }
            else {
                t = new Token(user.getIdUser());
                session.save(t);
            }
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
        return t.getToken();
    }

    @Override
    public void deleteToken(String token) {
        Session session = null;

        try {
            session = FactorySession.openSession();
            session.deleteToken(token);
        } finally {
            session.close();
        }
    }

    @Override
    public HashMap<String,Foro> getComments() {
        Session session = null;
        HashMap<String,Foro> comentarios=null;
        try {
            session = FactorySession.openSession();
            comentarios = session.findAll(Foro.class);
        }
        catch (Exception e) {
            log.warning("Error");
        }
        finally {
            session.close();
        }

        return comentarios;
    }

    @Override
    public void addComment(String token, Comentario comentario) {
        Session session = null;
        Foro newComment = null;
        try{
            session = FactorySession.openSession();
            String id = session.findIDByToken(token);
            User u = (User) session.findByID(User.class,id);
            String nombre = u.getNombre();
            String fecha = String.valueOf(LocalDate.now());
            newComment = new Foro(0, nombre, comentario.getComentario(), fecha);
            session.save(newComment);
        } finally {
            session.close();
        }
    }

    @Override
    public void createJugador(String id) {
        Session session = null;
        Jugador j = null;

        try {
            session = FactorySession.openSession();
             j = new Jugador(id,0,100);
            session.save(j);
        } catch (Exception e){
            throw e;
        }finally {
            session.close();
        }
        System.out.println("Jugador creado con ID = "+id);
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
        String pswdDB = null;
        String p = DigestUtils.md5Hex(pswd);

        try {
            u = getUser(idUser);
            pswdDB = u.getPassword();
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }

        if(pswdDB.equals(p)) return true;
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
