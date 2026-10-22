package com.sistemacompras.repositorios.reportes;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import com.sistemacompras.datos.IConexionBD;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

public class RankingProveedoresRepositorio {
    private final IConexionBD conexion;

    public RankingProveedoresRepositorio(IConexionBD conexion) {
        this.conexion = conexion;
    }

    public JasperPrint generarReporte(java.time.LocalDate fechaInicio,
                                      java.time.LocalDate fechaFin) throws Exception {
        InputStream jrxmlStream = getClass()
            .getClassLoader()
            .getResourceAsStream("reportes/Top5_Proveedores.jrxml");

        if (jrxmlStream == null) {
            throw new RuntimeException("No se encontró reportes/Top5_Proveedores.jrxml");
        }

        JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlStream);

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("FechaInicio", Date.valueOf(fechaInicio));
        parametros.put("FechaFin", Date.valueOf(fechaFin));

        try (Connection conn = conexion.crearConexion()) {
            return JasperFillManager.fillReport(jasperReport, parametros, conn);
        }
    }

}
