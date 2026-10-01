package com.sistemacompras.datos;

public class ConexionFactory {
	public enum TipoGestor {SQL_SERVER,MYSQL}
	public static IConexionBD obtenerConexion() {
		String gestorActivo=Configuracion.get("gestor.activo", "SQL_SERVER");
		return obtenerConexion(TipoGestor.valueOf(gestorActivo));
	}
	public static IConexionBD obtenerConexion(TipoGestor tipo) {
		return switch (tipo) {
		case SQL_SERVER -> new ConexionSQLServer(
				Configuracion.get("sqlserver.url"),
				Configuracion.get("sqlserver.user"),
				Configuracion.get("sqlserver.password")
				);
		case MYSQL -> new ConexionMySQL(
                Configuracion.get("mysql.url"),
                Configuracion.get("mysql.user"),
                Configuracion.get("mysql.password")
            );
		
		};
	}
	
}
