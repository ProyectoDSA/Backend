package edu.upc.eetac.dsa.orm.session;

import edu.upc.eetac.dsa.orm.util.ObjectHelper;
import edu.upc.eetac.dsa.orm.util.QueryHelper;

import java.sql.*;
import java.util.ArrayList;
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
            String idValue = (String) ObjectHelper.getter(entity, "id");
            pstm = this.conn.prepareStatement(insertQuery);
            pstm.setObject(1, idValue);
            int i = 2;

            for (String field: ObjectHelper.getFields(entity)) {
                if(!field.equals("id")) pstm.setObject(i++, ObjectHelper.getter(entity, field));
            }

            //Ejecutamos consulta
            pstm.executeQuery();
            System.out.println(pstm);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //Devuelve todos los objetos
    public List<Object> findAll(Class theClass) {
        List<Object> res = new ArrayList<>();
        ResultSet rs;
        Object object;

        //SELECT * FROM Class
        String selectQuery = QueryHelper.createQuerySELECTALL(theClass);
        System.out.println(selectQuery);

        Statement statement = null;

        try {
            object = theClass.newInstance();
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
                    res.add(rs.getObject(i)); //Añadimos objeto a la lista/tabla

                } System.out.println("Added : " + object.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
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

    //Funcion que actualiza los datos de un objeto con los atributos
    //del objeto que le enviamos como parametro
    public void update(Object object) {
        String updateQuery = QueryHelper.createQueryUPDATE(object);
        PreparedStatement statement = null;
        try{
            statement = conn.prepareStatement(updateQuery);
            statement.setObject(1, ObjectHelper.getter(object, "id"));
            statement.setObject(2, ObjectHelper.getter(object, "nombre"));
            statement.setObject(3, ObjectHelper.getter(object, "mail"));
            System.out.println(statement);
            statement.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
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
            String idValue = (String) ObjectHelper.getter(object,"id");
            statement.setObject(1, ObjectHelper.getter(object, "id"));
            statement.executeQuery();
            System.out.println(statement);
            System.out.println("Object with ID = "+idValue+" deleted");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            close();
        }
    }

    public void close() {
    }
}
