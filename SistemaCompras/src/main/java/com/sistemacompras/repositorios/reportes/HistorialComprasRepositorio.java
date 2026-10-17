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

public class HistorialComprasRepositorio {

    private final IConexionBD conexion;

    public HistorialComprasRepositorio(IConexionBD conexion) {
        this.conexion = conexion;
    }

    /**
     * Genera el reporte. El parámetro ArticuloID es String en el .jrxml,
     * así que lo pasamos como String.
     */
    public JasperPrint generarReporte(int articuloID) throws Exception {
        InputStream jrxmlStream = getClass()
            .getClassLoader()
            .getResourceAsStream("reportes/Historial_Compras_por_Articulo.jrxml");

        if (jrxmlStream == null) {
            throw new RuntimeException(
                "No se encontró reportes/Historial_Compras_por_Articulo.jrxml");
        }

        JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlStream);

        Map<String, Object> parametros = new HashMap<>();
        // ⚠️ El parámetro es String en el .jrxml
        parametros.put("ArticuloID", String.valueOf(articuloID));

        try (Connection conn = conexion.crearConexion()) {
            return JasperFillManager.fillReport(jasperReport, parametros, conn);
        }
    }
}