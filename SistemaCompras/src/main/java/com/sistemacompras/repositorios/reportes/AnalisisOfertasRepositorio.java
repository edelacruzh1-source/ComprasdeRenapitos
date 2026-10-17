package com.sistemacompras.repositorios.reportes;

import com.sistemacompras.datos.IConexionBD;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class AnalisisOfertasRepositorio {
	private final IConexionBD conexion;
	
	public AnalisisOfertasRepositorio(IConexionBD conexion) {
		this.conexion=conexion;
	}
	
	public JasperPrint generarReporte(int ordenID) throws Exception {
		InputStream jrxmlStream=getClass()
				.getClassLoader()
				.getResourceAsStream("reportes/Analisis_Oferta_Por_Orden.jrxml");
		if(jrxmlStream==null) {
			throw new RuntimeException("No se encontró el archivo .jrxml en resources/reportes/");
		}
		
		JasperReport jasperReport=JasperCompileManager.compileReport(jrxmlStream);
		Map<String,Object> parametros = new HashMap<>();
		parametros.put("OrdenID", ordenID);
		
		try (Connection conn=conexion.crearConexion()){
			return JasperFillManager.fillReport(jasperReport, parametros,conn);
		}
	}
	
}
