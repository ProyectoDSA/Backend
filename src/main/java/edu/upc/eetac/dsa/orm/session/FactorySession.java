package edu.upc.eetac.dsa.orm.session;


import edu.upc.eetac.dsa.orm.util.DBUtils;

import java.sql.Connection;

public class FactorySession {

    //Funcion que abre la conexion para realizar una consulta
    public static Session openSession() {

        Connection conn = getConnection();
        Session session = new SessionImpl(conn);
        return session;
    }


    //Obtenemos la conexion desde la clase DBUtils, que es la que nos lee
    //la configuracion del fichero db.properties
    private static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DBUtils.getConnection();
        } catch (Exception e){
            e.printStackTrace();
        }
        return connection;
    }
}
