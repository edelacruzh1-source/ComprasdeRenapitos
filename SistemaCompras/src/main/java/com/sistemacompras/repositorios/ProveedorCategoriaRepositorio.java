package com.sistemacompras.repositorios;

import com.sistemacompras.datos.IConexionBD;
import com.sistemacompras.entidades.ProveedorCategoria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProveedorCategoriaRepositorio {
    private final IConexionBD conexion;

    public ProveedorCategoriaRepositorio(IConexionBD conexion) { this.conexion = conexion; }

    public List<ProveedorCategoria> obtenerTodos() throws Exception {
        List<ProveedorCategoria> lista = new ArrayList<>();
        String sql = "SELECT * FROM Proveedor_Categoria";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public List<ProveedorCategoria> obtenerPorProveedor(int proveedorID) throws Exception {
        List<ProveedorCategoria> lista = new ArrayList<>();
        String sql = "SELECT * FROM Proveedor_Categoria WHERE ProveedorID = ?";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, proveedorID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        }
        return lista;
    }

    public boolean insertar(ProveedorCategoria pc) throws Exception {
        String sql = "INSERT INTO Proveedor_Categoria (ProveedorID, CategoriaID) VALUES (?, ?)";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, pc.getProveedorID());
            ps.setInt(2, pc.getCategoriaID());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int proveedorID, int categoriaID) throws Exception {
        String sql = "DELETE FROM Proveedor_Categoria WHERE ProveedorID = ? AND CategoriaID = ?";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, proveedorID);
            ps.setInt(2, categoriaID);
            return ps.executeUpdate() > 0;
        }
    }

    private ProveedorCategoria mapear(ResultSet rs) throws SQLException {
        return new ProveedorCategoria(rs.getInt("ProveedorID"), rs.getInt("CategoriaID"));
    }
}