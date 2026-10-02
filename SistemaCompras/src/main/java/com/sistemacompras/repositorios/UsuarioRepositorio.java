package com.sistemacompras.repositorios;

import com.sistemacompras.datos.IConexionBD;
import com.sistemacompras.entidades.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepositorio {
    private final IConexionBD conexion;

    public UsuarioRepositorio(IConexionBD conexion) { this.conexion = conexion; }

    public List<Usuario> obtenerTodos() throws Exception {
        List<Usuario> lista = new ArrayList<>();
        String sql = """
            SELECT u.UsuarioID, u.nombreUsuario, u.passwordHash,
                   u.RolID, u.activo, r.nombre AS nombreRol
            FROM Usuario u
            INNER JOIN Rol r ON u.RolID = r.RolID
            ORDER BY u.nombreUsuario
        """;
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public Usuario obtenerPorId(int id) throws Exception {
        String sql = """
            SELECT u.UsuarioID, u.nombreUsuario, u.passwordHash,
                   u.RolID, u.activo, r.nombre AS nombreRol
            FROM Usuario u
            INNER JOIN Rol r ON u.RolID = r.RolID
            WHERE u.UsuarioID = ?
        """;
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    public Usuario obtenerPorNombre(String nombreUsuario) throws Exception {
        String sql = """
            SELECT u.UsuarioID, u.nombreUsuario, u.passwordHash,
                   u.RolID, u.activo, r.nombre AS nombreRol
            FROM Usuario u
            INNER JOIN Rol r ON u.RolID = r.RolID
            WHERE u.nombreUsuario = ?
        """;
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nombreUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    /**
     * Inserta un usuario. El campo passwordHash debe venir YA hasheado.
     * Usa UsuarioService.crear() para que el hash se genere automáticamente.
     */
    public int insertar(Usuario u) throws Exception {
        String sql = "INSERT INTO Usuario (nombreUsuario, passwordHash, RolID, activo) VALUES (?, ?, ?, ?)";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, u.getNombreUsuario());
            ps.setString(2, u.getPasswordHash());
            ps.setInt(3, u.getRolID());
            ps.setBoolean(4, u.isActivo());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return 0;
    }

    /**
     * Actualiza datos del usuario. Si passwordHash viene null o vacío,
     * NO se actualiza la contraseña (se mantiene la actual).
     */
    public boolean actualizar(Usuario u) throws Exception {
        boolean cambiarPassword = u.getPasswordHash() != null && !u.getPasswordHash().isEmpty();

        String sql = cambiarPassword
            ? "UPDATE Usuario SET nombreUsuario=?, passwordHash=?, RolID=?, activo=? WHERE UsuarioID=?"
            : "UPDATE Usuario SET nombreUsuario=?, RolID=?, activo=? WHERE UsuarioID=?";

        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, u.getNombreUsuario());
            if (cambiarPassword) {
                ps.setString(2, u.getPasswordHash());
                ps.setInt(3, u.getRolID());
                ps.setBoolean(4, u.isActivo());
                ps.setInt(5, u.getUsuarioID());
            } else {
                ps.setInt(2, u.getRolID());
                ps.setBoolean(3, u.isActivo());
                ps.setInt(4, u.getUsuarioID());
            }
            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int id) throws Exception {
        String sql = "DELETE FROM Usuario WHERE UsuarioID = ?";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private Usuario mapear(ResultSet rs) throws SQLException {
        Usuario u = new Usuario(
            rs.getInt("UsuarioID"),
            rs.getString("nombreUsuario"),
            rs.getString("passwordHash"),
            rs.getInt("RolID"),
            rs.getBoolean("activo")
        );
        u.setNombreRol(rs.getString("nombreRol"));
        return u;
    }
}