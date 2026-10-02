package com.sistemacompras.repositorios;

import com.sistemacompras.datos.IConexionBD;
import com.sistemacompras.entidades.AdjudicacionDetalle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdjudicacionDetalleRepositorio {
    private final IConexionBD conexion;

    public AdjudicacionDetalleRepositorio(IConexionBD conexion) { this.conexion = conexion; }

    public List<AdjudicacionDetalle> obtenerTodos() throws Exception {
        List<AdjudicacionDetalle> lista = new ArrayList<>();
        String sql = "SELECT * FROM Adjudicacion_Detalle";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public List<AdjudicacionDetalle> obtenerPorAdjudicacion(int adjudicacionID) throws Exception {
        List<AdjudicacionDetalle> lista = new ArrayList<>();
        String sql = "SELECT * FROM Adjudicacion_Detalle WHERE AdjudicacionID = ?";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, adjudicacionID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        }
        return lista;
    }

    public boolean insertar(AdjudicacionDetalle ad) throws Exception {
        String sql = "INSERT INTO Adjudicacion_Detalle " +
                     "(AdjudicacionID, PedidoID, ProveedorID, cantidadFinal, precioAcordado) " +
                     "VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ad.getAdjudicacionID());
            ps.setInt(2, ad.getPedidoID());
            ps.setInt(3, ad.getProveedorID());
            ps.setInt(4, ad.getCantidadFinal());
            ps.setBigDecimal(5, ad.getPrecioAcordado());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean actualizar(AdjudicacionDetalle ad) throws Exception {
        String sql = "UPDATE Adjudicacion_Detalle SET ProveedorID=?, cantidadFinal=?, " +
                     "precioAcordado=? WHERE AdjudicacionID=? AND PedidoID=?";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ad.getProveedorID());
            ps.setInt(2, ad.getCantidadFinal());
            ps.setBigDecimal(3, ad.getPrecioAcordado());
            ps.setInt(4, ad.getAdjudicacionID());
            ps.setInt(5, ad.getPedidoID());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int adjudicacionID, int pedidoID) throws Exception {
        String sql = "DELETE FROM Adjudicacion_Detalle WHERE AdjudicacionID = ? AND PedidoID = ?";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, adjudicacionID);
            ps.setInt(2, pedidoID);
            return ps.executeUpdate() > 0;
        }
    }

    private AdjudicacionDetalle mapear(ResultSet rs) throws SQLException {
        return new AdjudicacionDetalle(
            rs.getInt("AdjudicacionID"),
            rs.getInt("PedidoID"),
            rs.getInt("ProveedorID"),
            rs.getInt("cantidadFinal"),
            rs.getBigDecimal("precioAcordado")
        );
    }
}