package com.sistemacompras.negocio;

import com.sistemacompras.datos.ConexionFactory;
import com.sistemacompras.datos.IConexionBD;
import com.sistemacompras.entidades.Permiso;
import com.sistemacompras.repositorios.PermisoRepositorio;

import java.util.List;

public class TestPermisos {
    public static void main(String[] args) throws Exception {
        IConexionBD conexion = ConexionFactory.obtenerConexion();
        PermisoRepositorio repo = new PermisoRepositorio(conexion);

        System.out.println("=== Todos los permisos ===");
        List<Permiso> todos = repo.obtenerTodos();
        System.out.println("Total: " + todos.size());
        for (Permiso p : todos) {
            System.out.println("Rol=" + p.getRolID() 
                + " Pantalla=" + p.getPantallaID()
                + " leer=" + p.isPermiteLeer());
        }

        System.out.println("\n=== Permisos del Rol 1 ===");
        List<Permiso> rol1 = repo.obtenerPorRol(1);
        System.out.println("Total: " + rol1.size());
        for (Permiso p : rol1) {
            System.out.println("PantallaID=" + p.getPantallaID() 
                + " leer=" + p.isPermiteLeer());
        }
    }
}
