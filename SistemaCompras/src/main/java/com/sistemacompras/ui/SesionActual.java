package com.sistemacompras.ui;

import com.sistemacompras.datos.IConexionBD;
import com.sistemacompras.entidades.Permiso;
import com.sistemacompras.entidades.Usuario;
import com.sistemacompras.repositorios.PermisoRepositorio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SesionActual {

    private static Usuario usuario;
    private static Map<Integer, Permiso> permisosPorPantalla = new HashMap<>();

    public static void setUsuario(Usuario u) { usuario = u; }
    public static Usuario getUsuario() { return usuario; }

    /** Carga los permisos del rol del usuario logueado */
    public static void cargarPermisos(IConexionBD conexion) throws Exception {
        permisosPorPantalla.clear();
        if (usuario == null) return;

        PermisoRepositorio repo = new PermisoRepositorio(conexion);
        List<Permiso> lista = repo.obtenerPorRol(usuario.getRolID());
        for (Permiso p : lista) {
            permisosPorPantalla.put(p.getPantallaID(), p);
        }
    }

    /** Devuelve el permiso para una pantalla, o null si no tiene acceso */
    public static Permiso getPermiso(int pantallaID) {
        return permisosPorPantalla.get(pantallaID);
    }

    public static boolean puedeLeer(int pantallaID) {
        Permiso p = permisosPorPantalla.get(pantallaID);
        return p != null && p.isPermiteLeer();
    }

    public static boolean puedeCrear(int pantallaID) {
        Permiso p = permisosPorPantalla.get(pantallaID);
        return p != null && p.isPermiteCrear();
    }

    public static boolean puedeActualizar(int pantallaID) {
        Permiso p = permisosPorPantalla.get(pantallaID);
        return p != null && p.isPermiteActualizar();
    }

    public static boolean puedeBorrar(int pantallaID) {
        Permiso p = permisosPorPantalla.get(pantallaID);
        return p != null && p.isPermiteBorrar();
    }

    public static boolean tieneRol(String nombreRol) {
        return usuario != null && usuario.getNombreRol().equals(nombreRol);
    }

    public static void cerrarSesion() {
        usuario = null;
        permisosPorPantalla.clear();
    }
}