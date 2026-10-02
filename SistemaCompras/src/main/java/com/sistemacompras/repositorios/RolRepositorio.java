package com.sistemacompras.repositorios;

import com.sistemacompras.datos.IConexionBD;
import com.sistemacompras.entidades.Rol;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RolRepositorio {
    private final IConexionBD conexion;

    public RolRepositorio(IConexionBD conexion) { this.conexion = conexion; }

    public List<Rol> obtenerTodos() throws Exception {
        List<Rol> lista = new ArrayList<>();
        String sql = "SELECT * FROM Rol ORDER BY nombre";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public Rol obtenerPorId(int id) throws Exception {
        String sql = "SELECT * FROM Rol WHERE RolID = ?";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    public int insertar(Rol r) throws Exception {
        String sql = "INSERT INTO Rol (nombre, descripcion) VALUES (?, ?)";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, r.getNombre());
            ps.setString(2, r.getDescripcion());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return 0;
    }

    public boolean actualizar(Rol r) throws Exception {
        String sql = "UPDATE Rol SET nombre=?, descripcion=? WHERE RolID=?";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, r.getNombre());
            ps.setString(2, r.getDescripcion());
            ps.setInt(3, r.getRolID());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int id) throws Exception {
        String sql = "DELETE FROM Rol WHERE RolID = ?";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private Rol mapear(ResultSet rs) throws SQLException {
        return new Rol(
            rs.getInt("RolID"),
            rs.getString("nombre"),
            rs.getString("descripcion")
        );
    }
}