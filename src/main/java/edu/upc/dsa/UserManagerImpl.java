package edu.upc.dsa;

import edu.upc.dsa.models.User;
import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.List;

public class UserManagerImpl implements UserManager{

    private static UserManager instance;
    protected List<User> users;
    final static Logger logger = Logger.getLogger(UserManagerImpl.class);

    private UserManagerImpl() {
        this.users = new LinkedList<>();
    }

    public static UserManager getInstance() {
        if (instance==null) instance = new UserManagerImpl();
        return instance;
    }

    @Override
    public User addUser(String nombre, String mail) {
        return this.addUser(new User(nombre,mail));
    }

    @Override
    public User addUser(User u) {
        logger.info("new User " + u);

        this.users.add (u);
        logger.info("new User added");
        return u;
    }

    @Override
    public User getUser(String id) {
        logger.info("getUser("+id+")");

        for (User u: this.users) {
            if (u.getId().equals(id)) {
                logger.info("getUser("+id+"): "+u);

                return u;
            }
        }

        logger.warn("not found " + id);
        return null;
    }

    @Override
    public List<User> findAll() {
        return this.users;
    }

    @Override
    public void deleteUser(String id) {
        User u = this.getUser(id);
        if (u==null) {
            logger.warn("not found " + u);
        }
        else logger.info(u+" deleted ");

        this.users.remove(u);
    }

    @Override
    public User updateUser(User u) {
        User user = this.getUser(u.getId());

        if (user!=null) {
            logger.info(u+" rebut!!!! ");

            user.setNombre(u.getNombre());
            user.setMail(u.getMail());

            logger.info(user+" updated ");
        }
        else {
            logger.warn("not found "+u);
        }

        return user;
    }

    @Override
    public int size() {
        int ret = this.users.size();
        logger.info("size " + ret);

        return ret;
    }
}
