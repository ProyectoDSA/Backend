package edu.upc.eetac.dsa.orm.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Date;

//CLASE AUXILIAR PARA MANEJAR LOS GETTERS Y SETTERS DE UN OBJETO DE UNA MANERA MÁS CÓMODA
public class ObjectHelper {

    //Funcion que obtiene los atributos declarados de cada modelo, que es el equivalente
    //a las distintas columnas que tenemos en la bbdd (en el caso de user: id, name, mail)
    public static String[] getFields(Object entity) {

        Class theClass = entity.getClass();

        Field[] fields = theClass.getDeclaredFields();

        String[] sFields = new String[fields.length];
        int i=0;

        for (Field f: fields) sFields[i++]=f.getName();

        return sFields;

    }

    //Funcion auxiliar que pone solo la primera letra de property en mayuscula para obtener el metodo
    //EJEMPLO: property = "id", respuesta de la funcion = setId()
    private static String getSetter(String property){
        String res;
        res="set"+property.substring(0,1).toUpperCase();
        if(property.equals("id")) //PARA LA CLASE TOKEN
            res+=property.substring(1).toLowerCase();
        else if(property.startsWith("id")) //PARA LOS ID's DE LAS DEMAS CLASES
            res+=property.substring(1,2).toLowerCase()+property.substring(2,3).toUpperCase()+property.substring(3).toLowerCase();
        else //PARA LO QUE NO SON ID's
            res+=property.substring(1).toLowerCase();
        return res;
    }

    // Metodo que llama a un metodo set (se usa para guardar un objeto que leemos de la bbdd en una variable Object)
    // object = objeto donde guardaremos la respuesta
    // property = atributo que queremos modificar
    // value = objeto para que lea los parametros
    public static void setter(Object object, String property, Object value) {
        // Method // invoke
        Method method = null;

        try{
            if(value.getClass() == Integer.class)
                method = object.getClass().getDeclaredMethod(getSetter(property),int.class);
            else if(value.getClass() == Date.class)
                method = object.getClass().getDeclaredMethod(getSetter(property), Date.class);
            else
                method = object.getClass().getDeclaredMethod(getSetter(property),value.getClass());

            method.invoke(object,value);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //System.out.println(res);
    }

    //Funcion auxiliar que pone solo la primera letra de property en mayuscula para obtener el metodo
    //EJEMPLO: property = "id", respuesta de la funcion = getId()
    private static String getGetter(String property){
        String res;
        res="get"+property.substring(0,1).toUpperCase();
        if(property.equals("id")) //PARA LA CLASE TOKEN
            res+=property.substring(1).toLowerCase();
        else if(property.startsWith("id")) //PARA LOS ID's DE LAS DEMAS CLASES
            res+=property.substring(1,2).toLowerCase()+property.substring(2,3).toUpperCase()+property.substring(3).toLowerCase();
        else //PARA LO QUE NO SON ID's
            res+=property.substring(1).toLowerCase();
        return res;
    }

    // Metodo que llama a un metodo get.
    // object = objeto que queremos consultar
    // property = atributo que queremos conseguir
    public static Object getter(Object object, String property) {
        Method method = null;
        Object res = null;
        try {
            /*if(value.getClass() == Integer.class)
                method = object.getClass().getDeclaredMethod(getSetter(property),int.class);
            else
                method = object.getClass().getDeclaredMethod(getSetter(property),value.getClass());*/
            method = object.getClass().getDeclaredMethod(getGetter(property), null); //User.getIdUser()
            res = method.invoke(object); //res = id
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return res;
    }
}
