package edu.upc.dsa;

import edu.upc.dsa.models.User;

import java.util.List;

public interface UserManager {

    public User addUser(String nombre, String mail);
    public User addUser(User u);
    public User getUser(String id);
    public List<User> findAll();
    public void deleteUser(String id);
    public User updateUser(User u);

    public int size();

}
