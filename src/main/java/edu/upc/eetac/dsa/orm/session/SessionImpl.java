package edu.upc.eetac.dsa.orm.session;

import edu.upc.eetac.dsa.exceptions.EmptyUserListException;
import edu.upc.eetac.dsa.exceptions.UserAlreadyExistsException;
import edu.upc.eetac.dsa.exceptions.UserNotFoundException;
import edu.upc.eetac.dsa.models.*;
import edu.upc.eetac.dsa.orm.util.ObjectHelper;
import edu.upc.eetac.dsa.orm.util.QueryHelper;
import io.swagger.models.auth.In;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SessionImpl implements Session {
    private final Connection conn;

    public SessionImpl(Connection conn) {
        this.conn = conn;
    }

    //Guardar objeto en la tabla
    public void save(Object entity) {

        //Llamamos a la consulta INSERT y preparamos statement para leer parametros
        String insertQuery = QueryHelper.createQueryINSERT(entity);
        PreparedStatement pstm = null;

        try {
            pstm = this.conn.prepareStatement(insertQuery);
            int i = 1;

            for (String field : ObjectHelper.getFields(entity)) {
                pstm.setObject(i, ObjectHelper.getter(entity, field));
                i++;
            }

            //Ejecutamos consulta
            pstm.executeQuery();
            System.out.println(pstm);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Devuelve todos los objetos
    public HashMap<String,Object> findAll(Class theClass) {
        //List<Object> res = new ArrayList<>();
        HashMap<String, Object> res = new HashMap<>();
        ResultSet rs;
        Object object;
        String id = null;

        //SELECT * FROM Class
        String selectQuery = QueryHelper.createQuerySELECTALL(theClass);
        System.out.println(selectQuery);

        Statement statement = null;

        try {
            object = theClass.getDeclaredConstructor().newInstance();
            statement = this.conn.createStatement();
            statement.execute(selectQuery);
            rs = statement.getResultSet();

            //Obtenemos los objetos y leemos las columnas con metadata
            //para ir guardando en cada objeto sus datos correspondientes
            while(rs.next()) {
                ResultSetMetaData rsmd = rs.getMetaData();
                for (int i=1; i<=rsmd.getColumnCount(); i++) {
                    String field = rsmd.getColumnName(i);
                    ObjectHelper.setter(object, field, rs.getObject(i));
                    if(i==1) {
                        if(rs.getObject(i).getClass() == Integer.class)
                            id = String.valueOf(rs.getObject(i));
                        else
                            id = (String) rs.getObject(i);
                    }
                }
                res.put(id,object);
                object = theClass.getDeclaredConstructor().newInstance();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //Devolvemos la lista con los objetos encontrados
        return res;
    }

    //Funcion similar a la anterior, pero esta vez devuelve solo 1 objeto
    //si coincide con el ID que le pasamos como parametro
    public Object findByID(Class theClass, String id) {
        ResultSet rs;
        Object object = null;

        String selectByIdQuery = QueryHelper.createQuerySELECTbyID(theClass);

        PreparedStatement pstm;

        try {
            object = theClass.newInstance();
            pstm = this.conn.prepareStatement(selectByIdQuery);
            pstm.setObject(1, id);
            rs = pstm.executeQuery();
            System.out.println(pstm);
            while(rs.next()) {
                ResultSetMetaData rsmd = rs.getMetaData();
                for(int i=1; i<=rsmd.getColumnCount();i++){
                    String field = rsmd.getColumnName(i);
                    ObjectHelper.setter(object,field,rs.getObject(i));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Object founded: "+object);
        return object;
    }

    public String findIDByToken(String token) {
        ResultSet rs;
        String id = null;

        String selectByTokenQuery = "SELECT id FROM Token WHERE token=?";

        PreparedStatement pstm;

        try {
            pstm = this.conn.prepareStatement(selectByTokenQuery);
            pstm.setObject(1, token);
            rs = pstm.executeQuery();
            System.out.println(pstm);
            while(rs.next()) {
                id = (String) rs.getObject(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return id;
    }

    @Override
    public Object findByNameOrMail(Class theClass, String name) {
        ResultSet rs;
        Object object = null;

        String selectByIdQuery = QueryHelper.createQuerySELECTbyNameOrMail(theClass);

        PreparedStatement pstm;

        try {
            object = theClass.newInstance();
            pstm = this.conn.prepareStatement(selectByIdQuery);
            pstm.setObject(1, name);
            pstm.setObject(2, name);
            rs = pstm.executeQuery();
            System.out.println(pstm);
            while(rs.next()) {
                ResultSetMetaData rsmd = rs.getMetaData();
                for(int i=1; i<=rsmd.getColumnCount();i++){
                    String field = rsmd.getColumnName(i);
                    ObjectHelper.setter(object,field,rs.getObject(i));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Object founded: "+object);
        return object;
    }

    public HashMap<Integer, Inventario> getObjetosJugador(Class theClass, String idJugador) throws UserNotFoundException {
        String objetosQuery = "SELECT * FROM Inventario WHERE idJugador='"+idJugador+"'";
        HashMap<Integer, Inventario> res = new HashMap<>();
        ResultSet rs;
        Object object;
        Integer id = null;
        Statement statement = null;

        try {
            object = theClass.getDeclaredConstructor().newInstance();
            statement = this.conn.createStatement();
            statement.execute(objetosQuery);
            rs = statement.getResultSet();

            //Obtenemos los objetos y leemos las columnas con metadata
            //para ir guardando en cada objeto sus datos correspondientes
            while(rs.next()) {
                ResultSetMetaData rsmd = rs.getMetaData();
                for (int i=1; i<=rsmd.getColumnCount(); i++) {
                    String field = rsmd.getColumnName(i);
                    ObjectHelper.setter(object, field, rs.getObject(i));
                    if(i==1) id = (Integer) rs.getObject(i);
                }
                res.put(id, (Inventario) object);
                object = theClass.getDeclaredConstructor().newInstance();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //Devolvemos la lista con los objetos encontrados
        return res;
    }

    //Funcion que actualiza los datos de un objeto con los atributos
    //del objeto que le enviamos como parametro
    public void update(Object object) {
        String updateQuery = QueryHelper.createQueryUPDATE(object);
        PreparedStatement statement = null;
        int i = 1;
        try{
            statement = conn.prepareStatement(updateQuery);
            String[] fields = ObjectHelper.getFields(object);
            for(String field : fields){
                if(object.getClass()== Inventario.class) {
                    switch (field) {
                        case "cantidad":
                            statement.setObject(1, ObjectHelper.getter(object, field));
                            break;
                        case "idObjeto":
                            statement.setObject(2, ObjectHelper.getter(object, field));
                            break;
                        case "idJugador":
                            statement.setObject(3, ObjectHelper.getter(object, field));
                            break;
                    }
                }
                else{
                    statement.setObject(i, ObjectHelper.getter(object, field));
                    i++;
                }
            }
            System.out.println(statement);
            statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        System.out.println("Object updated : "+object.toString());
    }

    //Funcion que busca a un objeto y lo elimina
    public void delete(Object object) {
        String deleteQuery = QueryHelper.createQueryDELETE(object);
        PreparedStatement statement = null;
        String idValue = null;
        try{
            if(object.getClass()==Inventario.class){
                statement = conn.prepareStatement("DELETE FROM Inventario WHERE idObjeto=? AND idJugador=?");
                statement.setObject(1, ObjectHelper.getter(object, "idObjeto"));
                statement.setObject(2, ObjectHelper.getter(object, "idJugador"));
            } else {
                statement = conn.prepareStatement(deleteQuery);
                idValue = (String) ObjectHelper.getter(object, "id" + object.getClass().getSimpleName());
                statement.setObject(1, ObjectHelper.getter(object, "id" + object.getClass().getSimpleName()));
            }
            statement.executeQuery();
            System.out.println(statement);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    public void deleteToken(String token){
        String deleteQuery = "DELETE FROM Token WHERE token=?";
        PreparedStatement statement = null;
        try{
            statement = conn.prepareStatement(deleteQuery);
            statement.setObject(1, token);
            statement.executeQuery();
            System.out.println(statement);
            System.out.println("Token "+token+" deleted");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    public void restaurar(Object object){
        String deleteQuery = QueryHelper.createQueryRESTAURAR(object);
        PreparedStatement statement = null;
        try{
            statement = conn.prepareStatement(deleteQuery);
            String idValue = (String) ObjectHelper.getter(object,"id"+object.getClass().getSimpleName());
            statement.setObject(1, ObjectHelper.getter(object, "id"+object.getClass().getSimpleName()));
            statement.executeQuery();
            System.out.println(statement);
            System.out.println("User with ID = "+idValue+" restaured");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    @Override
    public String getID(Class theClass, String nombre) {
        ResultSet rs;
        Object object = null;

        String selectID = QueryHelper.createQueryGetID(theClass);

        PreparedStatement pstm;

        try {
            object = theClass.newInstance();
            pstm = this.conn.prepareStatement(selectID);
            pstm.setObject(1, nombre);
            rs = pstm.executeQuery();
            System.out.println(pstm);
            while(rs.next()) {
                ResultSetMetaData rsmd = rs.getMetaData();
                for(int i=1; i<=rsmd.getColumnCount();i++){
                    String field = rsmd.getColumnName(i);
                    ObjectHelper.setter(object,field,rs.getObject(i));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Object founded: "+object);
        return null;
    }

    public HashMap<Integer,Object> findRanking(Class theClass) {
        //List<Object> res = new ArrayList<>();
        HashMap<Integer, Object> res = new HashMap<>();
        ResultSet rs;
        Object object;
        int id = 1;

        //SELECT * FROM Jugador ORDER BY puntos DESC LIMIT 5
        String selectQuery = QueryHelper.createQuerySELECTRanking(theClass);
        System.out.println(selectQuery);

        Statement statement = null;

        try {
            object = theClass.getDeclaredConstructor().newInstance();
            statement = this.conn.createStatement();
            statement.execute(selectQuery);
            rs = statement.getResultSet();

            //Obtenemos los objetos y leemos las columnas con metadata
            //para ir guardando en cada objeto sus datos correspondientes
            while(rs.next()) {
                ResultSetMetaData rsmd = rs.getMetaData();
                for (int i=1; i<=rsmd.getColumnCount(); i++) {
                    String field = rsmd.getColumnName(i);
                    ObjectHelper.setter(object, field, rs.getObject(i));
                }
                res.put(id,object);
                object = theClass.getDeclaredConstructor().newInstance();
                id++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //Devolvemos la lista con los objetos encontrados
        return res;
    }

    public int getPrecioObjeto(int idObjeto){
        ResultSet rs;
        int precio = 0;
        Statement statement;
        String selectQuery = QueryHelper.selectPrecioObjeto(idObjeto);
        try{
            statement = this.conn.createStatement();
            statement.execute(selectQuery);
            rs = statement.getResultSet();
            if (rs.next()) {
                precio = (int) rs.getObject(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return precio;
    }

    public void close() {
    }
}
