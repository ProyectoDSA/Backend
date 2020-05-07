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
        sb.append("id");
        for (String field: fields) {
            if(!field.equals("id")) sb.append(", ").append(field);
        }

        //Añadimos los parametros
        sb.append(") VALUES (?");

        for (String field: fields) {
            if(!field.equals("id")) sb.append(", ?");
        }

        sb.append(")");

        //Devolvemos la consulta
        return sb.toString();
    }

    //Crea consulta SELECT * FROM Class WHERE ID=?
    public static String createQuerySELECTbyID(Class theClass) {
        StringBuffer sb = new StringBuffer("SELECT * FROM ");
        sb.append(theClass.getSimpleName());
        sb.append(" WHERE ID=?");
        return sb.toString();
    }

    //SELECT * FROM Class
    public static String createQuerySELECTALL(Class theClass) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT * FROM ").append(theClass.getSimpleName());

        return sb.toString();
    }

    //UPDATE User SET id = ?, nombre = ?, mail = ? WHERE ID = 'user.getId()'
    public static String createQueryUPDATE(Object entity) {
        StringBuffer sb = new StringBuffer("UPDATE ");
        sb.append(entity.getClass().getSimpleName());
        sb.append(" SET ");

        String [] fields = ObjectHelper.getFields(entity);

        sb.append("id = ?");
        for (String field: fields) {
            if(!field.equals("id")) sb.append(", ").append(field).append(" = ?");
        }
        sb.append(" WHERE ID = '"+ObjectHelper.getter(entity, "id")).append("'");

        return sb.toString();
    }

    //DELETE FROM Class WHERE ID = ?
    public static String createQueryDELETE(Object entity){
        StringBuffer sb = new StringBuffer();
        sb.append("DELETE FROM ").append(entity.getClass().getSimpleName());
        sb.append(" WHERE ID = ?");
        return sb.toString();
    }

}
