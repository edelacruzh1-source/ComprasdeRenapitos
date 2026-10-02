package com.sistemacompras.negocio;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    /** Genera un hash BCrypt a partir de una contraseña en texto plano */
    public static String hash(String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede estar vacía");
        }
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    /** Verifica si una contraseña en texto plano coincide con el hash */
    public static boolean verificar(String password, String hash) {
        if (password == null || hash == null) return false;
        try {
            return BCrypt.checkpw(password, hash);
        } catch (Exception e) {
            return false;
        }
    }
}