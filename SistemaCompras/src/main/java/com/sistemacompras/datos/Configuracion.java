package com.sistemacompras.datos;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configuracion {
	private static final Properties props = new Properties();
	static {
		try(InputStream is = Configuracion.class
				.getClassLoader()
				.getResourceAsStream("config.properties")){
			if(is==null) {
				throw new RuntimeException("No se encontro config.properties");
			}
			props.load(is);
		} catch (Exception e) {
			throw new RuntimeException("Error cargando configuración", e);
		}
	}
	
	public static String get(String clave) {
		return props.getProperty(clave);
	}
	public static String get(String clave, String defecto) {
		return props.getProperty(clave, defecto);
	}

}
