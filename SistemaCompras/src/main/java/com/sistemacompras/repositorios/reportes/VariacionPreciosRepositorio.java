package com.sistemacompras.repositorios.reportes;

import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import com.sistemacompras.datos.IConexionBD;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

public class VariacionPreciosRepositorio {
	
    private final IConexionBD conexion;

    public VariacionPreciosRepositorio(IConexionBD conexion) {
        this.conexion = conexion;
    }

    public JasperPrint generarReporte(int articuloID, int proveedorID) throws Exception {
        InputStream jrxmlStream = getClass()
            .getClassLoader()
            .getResourceAsStream("reportes/Variacion_Precio.jrxml");

        if (jrxmlStream == null) {
            throw new RuntimeException("No se encontró reportes/Variacion_Precio.jrxml");
        }

        JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlStream);

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("ArticuloID", articuloID);
        parametros.put("ProveedorID", proveedorID);

        try (Connection conn = conexion.crearConexion()) {
            return JasperFillManager.fillReport(jasperReport, parametros, conn);
        }
    }

}
