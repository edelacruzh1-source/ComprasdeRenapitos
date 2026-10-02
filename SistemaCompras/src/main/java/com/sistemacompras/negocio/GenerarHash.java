package com.sistemacompras.negocio;

public class GenerarHash {
    public static void main(String[] args) {
        String password = "admin123";
        String hash = PasswordUtil.hash(password);
        System.out.println("Hash para '" + password + "':");
        System.out.println(hash);
        System.out.println("\nSQL:");
        System.out.println("INSERT INTO Usuario (nombreUsuario, passwordHash, RolID, activo) " +
                           "VALUES ('admin', '" + hash + "', 1, 1);");
    }
}