package com.sistemacompras.repositorios;

import com.sistemacompras.datos.ConexionFactory;
import com.sistemacompras.datos.IConexionBD;
import com.sistemacompras.entidades.Sucursal;

public class TestSucursal {
    public static void main(String[] args) {
        try {
            IConexionBD conexion = ConexionFactory.obtenerConexion();
            SucursalRepositorio repo = new SucursalRepositorio(conexion);

            // 1. Insertar
            Sucursal nueva = new Sucursal(0, "SUC-001",
                "5ta Avenida 10-20", "Guatemala", "Guatemala");
            int id = repo.insertar(nueva);
            System.out.println("✅ Insertada con ID: " + id);

            // 2. Listar
            System.out.println("\n📋 Sucursales:");
            for (Sucursal s : repo.obtenerTodos()) {
                System.out.println("  " + s);
            }

            // 3. Actualizar
            nueva.setSucursalID(id);
            nueva.setDireccion("6ta Avenida 11-30");
            repo.actualizar(nueva);
            System.out.println("\n✅ Actualizada");

            // 4. Eliminar (descomenta si quieres probar)
            // repo.eliminar(id);
            // System.out.println("✅ Eliminada");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
