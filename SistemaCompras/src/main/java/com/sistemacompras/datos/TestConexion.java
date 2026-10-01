package com.sistemacompras.datos;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;

public class TestConexion {
	public static void main(String[] args) {
		IConexionBD conexion=ConexionFactory.obtenerConexion();
		System.out.println("Gestor activo: "+conexion.getNombreGestor());
		if (!conexion.probarConexion()) {
			System.out.println("Error al conectar");
			return;

		}
		System.out.println("Conexion exitosa");
		try (Connection conn = conexion.crearConexion()) {
            DatabaseMetaData meta = conn.getMetaData();
            ResultSet rs = meta.getTables(null, null, "%", new String[]{"TABLE"});

            System.out.println("\nTablas encontradas:");
            int count = 0;
            while (rs.next()) {
                String tabla = rs.getString("TABLE_NAME");
                if (!tabla.startsWith("sys")) {
                    System.out.println("  - " + tabla);
                    count++;
                }
            }
            System.out.println("\nTotal: " + count + " tablas");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	}

