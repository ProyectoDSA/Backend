package edu.upc.eetac.dsa.orm.session;

import edu.upc.eetac.dsa.exceptions.EmptyUserListException;
import edu.upc.eetac.dsa.exceptions.UserAlreadyExistsException;
import edu.upc.eetac.dsa.exceptions.UserNotFoundException;
import edu.upc.eetac.dsa.models.Mapa;
import edu.upc.eetac.dsa.models.Objeto;
import edu.upc.eetac.dsa.models.User;

import java.util.HashMap;
import java.util.List;

public interface Session<E> {

    /*********CRUD*******/
    void save(Object entity); //Añade un nuevo objeto en la tabla correspondiente
    HashMap<String,Object> findAll(Class theClass); //Lee una tabla y devuelve todos los objetos
    Object findByID(Class theClass, String id); //Lee una tabla y devuelve el objecto con el ID especificado
    void update(Object object); //Actualiza un objeto de la tabla
    void delete(Object object); //Elimina un objeto de la tabla

    /*******USER********/
    String getID(Class theClass, String nombre); //Devuelve el ID del objeto con el nombre especificado
    String findIDByToken(String token); //Devuelve el ID asociado al token
    void deleteToken(String token); //Elimina el token
    Object findByNameOrMail(Class theClass, String name); //Encuenta usuario por nombre o correo

    /*******MAPAS********/
    HashMap<Integer, Mapa> getMapas(Class theClass); //Obtiene los mapas
    String findEnemigos(int id); //Obtiene el string de enemigos

    /*******JUGADOR*****/
    HashMap<String,Object> findRanking(Class theClass, String id); //Obtiene las 5 primeras posiciones
    //Encuentra los objetos de un jugador
    HashMap<Integer, Object> getObjetosJugador(Class theClass, String idJugador) throws UserNotFoundException;
    int getMonedasJugador(String idJugador); //Devuelve las monedas de un jugador
    int getPrecioObjeto(int idObjeto); //Devuelve el precio de un objeto

    //Cierra la sesión
    void close();
}

