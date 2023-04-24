package com.nisum.ms.usermanagement.util;

import java.util.UUID;

import com.google.gson.Gson;

/**
 * Clase UserManagementUtil.
 * @author Ronald Baron.
 * @version 1.0
 */
public class UserManagementUtil {
    
    private UserManagementUtil() {
    	
    }
    
    /**
     * Metodo que genera un codigo unico UUID.
     * @author Ronald Baron.
     * @version 1.0
     */
    public static String generarUuid() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
    
    /**
     * Convierte un objeto origen a un destino con campos con el mismo nombre.
     * @param destino la clase destino a convertir
     * @param origen objeto origen a transformar
     * @return el objeto de destino de la clase <code>destino</code>
     */
    public static <D> D mapperClass(Class<D> destino, Object origen) {
        try {
            if (origen == null) {
                return null;
            }
            Gson gson = new Gson();
            return gson.fromJson(gson.toJson(origen), destino);
        } catch (Exception e) {
            
        }
        return null;
    }
    
    /**
     * Metodo que valida si los datos son nulos o son vacios.
     * @author Ronald Baron
     * @version 1.0
     */
    public static boolean validarStringEmpty(String ... stringArray) {
        boolean flagStringEmpty = false;
        if (stringArray != null) {
            for (String string : stringArray) {
                if (string == null || UserManagementConstantes.CADENA_VACIA.equals(trimMS(string))) {
                    flagStringEmpty = true;
                } else {
                    flagStringEmpty = false;
                    break;
                }
            }
        }
        return flagStringEmpty;
    }
    
    /**
     * Metodo que realiza el trim.
     * @author Ronald Barón
     * @version 1.0
     */
    public static String trimMS(String str) {
        if (str != null) {
            return str.trim();
        } else {
            return str;
        }
    }
    
}