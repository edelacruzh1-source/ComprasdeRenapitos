package com.sistemacompras.repositorios.reportes;

import com.sistemacompras.datos.IConexionBD;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;

public class EficienciaCompraRepositorio {

    private final IConexionBD conexion;

    public EficienciaCompraRepositorio(IConexionBD conexion) {
        this.conexion = conexion;
    }

    public JasperPrint generarReporte() throws Exception {
        InputStream jrxmlStream = getClass()
            .getClassLoader()
            .getResourceAsStream("reportes/Eficiencia_Proceso_Compra.jrxml");

        if (jrxmlStream == null) {
            throw new RuntimeException("No se encontró reportes/Eficiencia_Proceso_Compra.jrxml");
        }

        JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlStream);

        try (Connection conn = conexion.crearConexion()) {
            return JasperFillManager.fillReport(jasperReport, new HashMap<>(), conn);
        }
    }
}
