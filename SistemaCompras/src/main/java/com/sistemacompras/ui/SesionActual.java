package com.sistemacompras.ui;

import com.sistemacompras.entidades.Usuario;

public class SesionActual {
    private static Usuario usuario;

    public static void setUsuario(Usuario u) { usuario = u; }
    public static Usuario getUsuario() { return usuario; }

    public static boolean tieneRol(String nombreRol) {
        return usuario != null && usuario.getNombreRol().equals(nombreRol);
    }

    public static void cerrarSesion() { usuario = null; }
}
