package edu.upc.eetac.dsa.orm.util;

import java.lang.reflect.Field;

//CLASE AUXILIAR QUE NOS PERMITE CREAR LAS CONSULTAS SQL DE UNA MANERA MÁS COMODA
//ASIGNAMOS LOS ? MEDIANTE SQLInjection

public class QueryHelper {

    //Crea consulta INSERT INTO User(id,nombre,mail) values(?,?,?)
    public static String createQueryINSERT(Object entity) {

        //Creamos String de la consulta
        StringBuffer sb = new StringBuffer("INSERT INTO ");
        sb.append(entity.getClass().getSimpleName()).append(" ");
        sb.append("(");

        //Obtiene los atributos de User
        String [] fields = ObjectHelper.getFields(entity);

        //Coloca las comas
        sb.append("id"+entity.getClass().getSimpleName());
        for (String field: fields) {
            if(!field.equals("id"+entity.getClass().getSimpleName())) sb.append(", ").append(field);
        }

        //Añadimos los parametros
        sb.append(") VALUES (?");

        for (String field: fields) {
            if(!field.equals("id"+entity.getClass().getSimpleName())) sb.append(", ?");
        }

        sb.append(")");

        //Devolvemos la consulta
        return sb.toString();
    }

    //Crea consulta SELECT * FROM Class WHERE ID=?
    public static String createQuerySELECTbyID(Class theClass) {
        StringBuffer sb = new StringBuffer("SELECT * FROM ");
        sb.append(theClass.getSimpleName());
        sb.append(" WHERE id"+theClass.getSimpleName()+"=?");
        return sb.toString();
    }

    public static String createQuerySELECTbyNameOrMail(Class theClass) {
        StringBuffer sb = new StringBuffer("SELECT * FROM ");
        sb.append(theClass.getSimpleName());
        sb.append(" WHERE NOMBRE=? OR MAIL=?");
        return sb.toString();
    }

    //SELECT * FROM Class
    public static String createQuerySELECTALL(Class theClass) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT * FROM ").append(theClass.getSimpleName());

        return sb.toString();
    }

    //SELECT * FROM Jugador ORDER BY puntos DESC LIMIT 5
    public static String createQuerySELECTRanking(Class theClass) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT * FROM ").append(theClass.getSimpleName());
        sb.append(" ORDER BY puntos DESC LIMIT 5");

        return sb.toString();
    }

    //UPDATE User SET id = ?, nombre = ?, mail = ? WHERE ID = 'user.getId()'
    public static String createQueryUPDATE(Object entity) {
        StringBuffer sb = new StringBuffer("UPDATE ");
        sb.append(entity.getClass().getSimpleName());
        sb.append(" SET ");

        String [] fields = ObjectHelper.getFields(entity);

        sb.append("id"+entity.getClass().getSimpleName()+" = ?");
        for (String field: fields) {
            if(!field.startsWith("id")) sb.append(", ").append(field).append(" = ?");
        }
        sb.append(" WHERE id"+entity.getClass().getSimpleName()+" = '" +
                ObjectHelper.getter(entity, "id"+entity.getClass().getSimpleName())+"'");

        return sb.toString();
    }

    //DELETE FROM Class WHERE ID = ?
    public static String createQueryDELETE(Object entity){
        StringBuffer sb = new StringBuffer();
        sb.append("UPDATE "+entity.getClass().getSimpleName()+" SET status='inactive'");
        sb.append(" WHERE id"+entity.getClass().getSimpleName()+" = ?");
        return sb.toString();
    }

    public static String createQueryGetID(Object entity){
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT id"+entity.getClass().getSimpleName()+" FROM ").append(entity.getClass().getSimpleName());
        sb.append(" WHERE nombre = ?");
        return sb.toString();
    }

    public static String createQuerySelectClassFromJugador(Class theClass){
        StringBuffer sb = new StringBuffer("SELECT * FROM ");
        sb.append(theClass.getSimpleName());
        sb.append(" WHERE idJugador=?");
        return sb.toString();
    }

}
