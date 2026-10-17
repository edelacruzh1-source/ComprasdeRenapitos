package com.sistemacompras.repositorios.reportes;

import com.sistemacompras.datos.IConexionBD;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GastoDepartamentalRepositorio {

    private final IConexionBD conexion;

    public GastoDepartamentalRepositorio(IConexionBD conexion) {
        this.conexion = conexion;
    }

    public JasperPrint generarReporte(int sucursalID, int anio) throws Exception {
        InputStream jrxmlStream = getClass()
            .getClassLoader()
            .getResourceAsStream("reportes/Gasto_Departamental.jrxml");

        if (jrxmlStream == null) {
            throw new RuntimeException("No se encontró reportes/Gasto_Departamental.jrxml");
        }

        JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlStream);

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("SucursalID", sucursalID);
        parametros.put("Anio", anio);

        try (Connection conn = conexion.crearConexion()) {
            return JasperFillManager.fillReport(jasperReport, parametros, conn);
        }
    }

    /**
     * Devuelve los años que tienen adjudicaciones registradas.
     */
    public List<Integer> obtenerAniosConDatos() throws Exception {
        List<Integer> anios = new ArrayList<>();
        String sql = "SELECT DISTINCT YEAR(fechaResolucion) AS anio " +
                     "FROM Adjudicacion ORDER BY anio DESC";

        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                anios.add(rs.getInt("anio"));
            }
        }
        return anios;
    }
}