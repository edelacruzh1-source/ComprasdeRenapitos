package com.sistemacompras.repositorios.reportes;

import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;

import com.sistemacompras.datos.IConexionBD;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

public class PedidosPendientesRepositorio {
    private final IConexionBD conexion;

    public PedidosPendientesRepositorio(IConexionBD conexion) {
        this.conexion = conexion;
    }

    public JasperPrint generarReporte() throws Exception {
        InputStream jrxmlStream = getClass()
            .getClassLoader()
            .getResourceAsStream("reportes/Pedidos_Pendientes.jrxml");

        if (jrxmlStream == null) {
            throw new RuntimeException("No se encontró reportes/Pedidos_Pendientes.jrxml");
        }

        JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlStream);

        try (Connection conn = conexion.crearConexion()) {
            return JasperFillManager.fillReport(jasperReport, new HashMap<>(), conn);
        }
    }

}
