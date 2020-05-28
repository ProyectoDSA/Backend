package edu.upc.eetac.dsa.orm.session;

import edu.upc.eetac.dsa.exceptions.EmptyUserListException;
import edu.upc.eetac.dsa.exceptions.UserAlreadyExistsException;
import edu.upc.eetac.dsa.exceptions.UserNotFoundException;
import edu.upc.eetac.dsa.orm.util.ObjectHelper;
import edu.upc.eetac.dsa.orm.util.QueryHelper;

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
            //Leemos los parametros y los guardamos en la consulta
            //El id como se genera aleatorio al inicializar un objeto, lo leemos y lo guardamos
            String idValue = (String) ObjectHelper.getter(entity, "id"+entity.getClass().getSimpleName());
            pstm = this.conn.prepareStatement(insertQuery);
            pstm.setObject(1, idValue);
            int i = 2;

            for (String field: ObjectHelper.getFields(entity)) {
                if(!field.startsWith("id")) pstm.setObject(i++, ObjectHelper.getter(entity, field));
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
                    if(i==1) id = (String) rs.getObject(i);
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
                statement.setObject(i, ObjectHelper.getter(object, field));
                i++;
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
        try{
            statement = conn.prepareStatement(deleteQuery);
            String idValue = (String) ObjectHelper.getter(object,"id"+object.getClass().getSimpleName());
            statement.setObject(1, ObjectHelper.getter(object, "id"+object.getClass().getSimpleName()));
            statement.executeQuery();
            System.out.println(statement);
            System.out.println("Object with ID = "+idValue+" deleted");
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

    public HashMap<String,Object> findRanking(Class theClass) {
        //List<Object> res = new ArrayList<>();
        HashMap<String, Object> res = new HashMap<>();
        ResultSet rs;
        Object object;
        String id = null;

        //SELECT * FROM Class
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
                    if(i==1) id = (String) rs.getObject(i);
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

    public void close() {
    }
}
