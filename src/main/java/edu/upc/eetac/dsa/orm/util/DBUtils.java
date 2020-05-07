package edu.upc.eetac.dsa.orm.util;

import java.io.File;
import java.io.FileReader;

import java.sql.*;
import java.util.Properties;
import java.sql.Connection;
import java.sql.DriverManager;

//Clase que lee el fichero de propiedades para iniciar la conexion con la BBDD

public class DBUtils {

    //Metodo que incia y devuelve la conexion con la BBDD
    public static Connection getConnection() throws SQLException {
        Connection connection = null;

        try{
            Properties p = new Properties(); //Crea fichero de propiedades
            //Seleccionamos nuestro fichero donde guardamos las propiedades
            File dbPathPropertiesFile = new File("src/main/resources/db.properties");
            FileReader r = new FileReader(dbPathPropertiesFile);
            //Insertamos nuestro fichero en las propiedades
            p.load(r);
            //Asignamos los distintos parametros
            String host = p.getProperty("db.host");
            String port = p.getProperty("db.port");
            String user = p.getProperty("db.username");
            String db = p.getProperty("db.database");
            String pswd = p.getProperty("db.password");

            //Obtenemos la conexion
            connection = DriverManager.getConnection("jdbc:mariadb://"+host+":"+port+"/"
                    +db+"?user="+user+"&password="+pswd);

        } catch (Exception e){
            e.printStackTrace();
        }

        return connection; //Devolvemos la conexion
    }
}
