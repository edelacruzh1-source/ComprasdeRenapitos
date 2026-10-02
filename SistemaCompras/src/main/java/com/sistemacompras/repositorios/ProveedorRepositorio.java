package com.sistemacompras.repositorios;

import com.sistemacompras.datos.IConexionBD;
import com.sistemacompras.entidades.Proveedor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProveedorRepositorio {
    private final IConexionBD conexion;

    public ProveedorRepositorio(IConexionBD conexion) { this.conexion = conexion; }

    public List<Proveedor> obtenerTodos() throws Exception {
        List<Proveedor> lista = new ArrayList<>();
        String sql = "SELECT * FROM Proveedor ORDER BY nombreComercial";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public Proveedor obtenerPorId(int id) throws Exception {
        String sql = "SELECT * FROM Proveedor WHERE ProveedorID = ?";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    public int insertar(Proveedor p) throws Exception {
        String sql = "INSERT INTO Proveedor (nit, nombreComercial, Direccion, telefono) VALUES (?, ?, ?, ?)";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, p.getNit());
            ps.setString(2, p.getNombreComercial());
            ps.setString(3, p.getDireccion());
            ps.setString(4, p.getTelefono());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return 0;
    }

    public boolean actualizar(Proveedor p) throws Exception {
        String sql = "UPDATE Proveedor SET nit=?, nombreComercial=?, Direccion=?, telefono=? WHERE ProveedorID=?";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getNit());
            ps.setString(2, p.getNombreComercial());
            ps.setString(3, p.getDireccion());
            ps.setString(4, p.getTelefono());
            ps.setInt(5, p.getProveedorID());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int id) throws Exception {
        String sql = "DELETE FROM Proveedor WHERE ProveedorID = ?";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private Proveedor mapear(ResultSet rs) throws SQLException {
        return new Proveedor(
            rs.getInt("ProveedorID"),
            rs.getString("nit"),
            rs.getString("nombreComercial"),
            rs.getString("Direccion"),
            rs.getString("telefono")
        );
    }
}