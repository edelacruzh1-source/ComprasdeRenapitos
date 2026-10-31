package com.sistemacompras.repositorios.reportes;

import com.sistemacompras.datos.IConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DashboardRepositorio {

    private final IConexionBD conexion;

    public DashboardRepositorio(IConexionBD conexion) {
        this.conexion = conexion;
    }

    // 1. Top 5 proveedores por monto adjudicado
    public List<Object[]> obtenerTop5Proveedores() throws Exception {
        List<Object[]> lista = new ArrayList<>();
        String sql = "SELECT TOP 5 p.nombreComercial, " +
                     "SUM(ad.cantidadFinal * ad.precioAcordado) AS Monto " +
                     "FROM Adjudicacion_Detalle ad " +
                     "INNER JOIN Proveedor p ON ad.ProveedorID = p.ProveedorID " +
                     "GROUP BY p.nombreComercial ORDER BY Monto DESC";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Object[]{ rs.getString(1), rs.getDouble(2) });
            }
        }
        return lista;
    }

    // 2. Gasto por departamento
    public List<Object[]> obtenerGastoDepartamental() throws Exception {
        List<Object[]> lista = new ArrayList<>();
        String sql = "SELECT d.nombre, " +
                     "SUM(ad.cantidadFinal * ad.precioAcordado) AS Gasto " +
                     "FROM Adjudicacion_Detalle ad " +
                     "INNER JOIN Pedido_Interno pi ON ad.PedidoID = pi.PedidoID " +
                     "INNER JOIN Departamento d ON pi.DepartamentoID = d.DepartamentoID " +
                     "GROUP BY d.nombre ORDER BY Gasto DESC";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Object[]{ rs.getString(1), rs.getDouble(2) });
            }
        }
        return lista;
    }

    // 3. Eficiencia: días por orden
    public List<Object[]> obtenerEficiencia() throws Exception {
        List<Object[]> lista = new ArrayList<>();
        String sql = "SELECT o.OrdenID, " +
                     "DATEDIFF(DAY, o.fechaCreacion, adj.fechaResolucion) AS Dias " +
                     "FROM Orden_Compra o " +
                     "INNER JOIN Adjudicacion adj ON o.OrdenID = adj.OrdenID " +
                     "ORDER BY o.OrdenID";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Object[]{ rs.getInt(1), rs.getInt(2) });
            }
        }
        return lista;
    }

    // 4. Pedidos pendientes por sucursal
    public List<Object[]> obtenerPedidosPendientesPorSucursal() throws Exception {
        List<Object[]> lista = new ArrayList<>();
        String sql = "SELECT s.Codigo, COUNT(*) AS Pendientes " +
                     "FROM Pedido_Interno p " +
                     "LEFT JOIN Orden_Pedido op ON p.PedidoID = op.PedidoID " +
                     "INNER JOIN Departamento d ON p.DepartamentoID = d.DepartamentoID " +
                     "INNER JOIN Sucursal s ON d.SucursalID = s.SucursalID " +
                     "WHERE op.PedidoID IS NULL " +
                     "GROUP BY s.Codigo ORDER BY Pendientes DESC";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Object[]{ rs.getString(1), rs.getInt(2) });
            }
        }
        return lista;
    }

    // 5. Órdenes activas vs adjudicadas
    public List<Object[]> obtenerEstadoOrdenes() throws Exception {
        List<Object[]> lista = new ArrayList<>();
        String sql = "SELECT 'Activas' AS Estado, COUNT(*) AS Total " +
                     "FROM Orden_Compra o " +
                     "WHERE NOT EXISTS (SELECT 1 FROM Adjudicacion a WHERE a.OrdenID = o.OrdenID) " +
                     "UNION ALL " +
                     "SELECT 'Adjudicadas', COUNT(*) FROM Adjudicacion";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Object[]{ rs.getString(1), rs.getInt(2) });
            }
        }
        return lista;
    }
}