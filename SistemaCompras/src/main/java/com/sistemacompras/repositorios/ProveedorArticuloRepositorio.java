package com.sistemacompras.repositorios;

import com.sistemacompras.datos.IConexionBD;
import com.sistemacompras.entidades.ProveedorArticulo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProveedorArticuloRepositorio {
    private final IConexionBD conexion;

    public ProveedorArticuloRepositorio(IConexionBD conexion) { this.conexion = conexion; }

    public List<ProveedorArticulo> obtenerTodos() throws Exception {
        List<ProveedorArticulo> lista = new ArrayList<>();
        String sql = "SELECT * FROM Proveedor_Articulo";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public List<ProveedorArticulo> obtenerPorProveedor(int proveedorID) throws Exception {
        List<ProveedorArticulo> lista = new ArrayList<>();
        String sql = "SELECT * FROM Proveedor_Articulo WHERE ProveedorID = ?";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, proveedorID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        }
        return lista;
    }

    public boolean insertar(ProveedorArticulo pa) throws Exception {
        String sql = "INSERT INTO Proveedor_Articulo (ProveedorID, ArticuloID, precio) VALUES (?, ?, ?)";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, pa.getProveedorID());
            ps.setInt(2, pa.getArticuloID());
            ps.setBigDecimal(3, pa.getPrecio());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean actualizar(ProveedorArticulo pa) throws Exception {
        String sql = "UPDATE Proveedor_Articulo SET precio=? WHERE ProveedorID=? AND ArticuloID=?";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBigDecimal(1, pa.getPrecio());
            ps.setInt(2, pa.getProveedorID());
            ps.setInt(3, pa.getArticuloID());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int proveedorID, int articuloID) throws Exception {
        String sql = "DELETE FROM Proveedor_Articulo WHERE ProveedorID = ? AND ArticuloID = ?";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, proveedorID);
            ps.setInt(2, articuloID);
            return ps.executeUpdate() > 0;
        }
    }

    private ProveedorArticulo mapear(ResultSet rs) throws SQLException {
        return new ProveedorArticulo(
            rs.getInt("ProveedorID"),
            rs.getInt("ArticuloID"),
            rs.getBigDecimal("precio")
        );
    }
}