package com.sistemacompras.datos;
import java.sql.Connection;

public interface IConexionBD {
	Connection crearConexion() throws Exception;
	String getNombreGestor();
	boolean probarConexion();

}
