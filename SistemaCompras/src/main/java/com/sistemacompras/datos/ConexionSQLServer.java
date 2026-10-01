package com.sistemacompras.datos;
import java.sql.Connection;
import java.sql.DriverManager;


public class ConexionSQLServer implements IConexionBD{
	private final String url;
	private final String user;
	private final String password;
	
	public ConexionSQLServer(String url,String user, String password) {
		this.url=url;
		this.user=user;
		this.password=password;
	}
	@Override
	public Connection crearConexion() throws Exception {
		return DriverManager.getConnection(url,user,password);
	}
	@Override
	public String getNombreGestor() {
		return "SQL Server";
	}
	@Override
	public boolean probarConexion() {
		try(Connection conn=crearConexion()){
			return conn != null && !conn.isClosed();
		}catch(Exception e) {
			 System.err.println("Error coexion SQL Server: " + e.getMessage());
			 return false;
		}
	}
}
