package edu.upc.eetac.dsa.orm.session;

import edu.upc.eetac.dsa.exceptions.EmptyUserListException;
import edu.upc.eetac.dsa.exceptions.UserAlreadyExistsException;
import edu.upc.eetac.dsa.exceptions.UserNotFoundException;
import edu.upc.eetac.dsa.models.Objeto;
import edu.upc.eetac.dsa.models.User;

import java.util.HashMap;
import java.util.List;

public interface Session<E> {

    //OPERACIONES CRUD
    void save(Object entity); //Añade un nuevo objeto en la tabla correspondiente
    String getID(Class theClass, String nombre); //Devuelve el ID del objeto con el nombre especificado
    HashMap<String,Object> findAll(Class theClass); //Lee una tabla y devuelve todos los objetos
    Object findByID(Class theClass, String id); //Lee una tabla y devuelve el objecto con el ID especificado
    public String findIDByToken(String token);
    public String findMapa(int id);
    public String findEnemigos(int id);
    void update(Object object); //Actualiza un objeto de la tabla
    void delete(Object object); //Elimina un objeto de la tabla
    void deleteToken(String token);

    //CONSULTA PARA USER
    Object findByNameOrMail(Class theClass, String name);

    //CONSULTAS PARA JUGADOR
    HashMap<String,Object> findRanking(Class theClass, String id);
    HashMap<Integer, Object> getObjetosJugador(Class theClass, String idJugador) throws UserNotFoundException;
    int getMonedasJugador(String idJugador);
    int getPrecioObjeto(int idObjeto);

    void close();
}

