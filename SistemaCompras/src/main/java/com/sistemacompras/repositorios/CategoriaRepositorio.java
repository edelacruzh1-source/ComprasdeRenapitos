package com.sistemacompras.repositorios;

import com.sistemacompras.datos.IConexionBD;
import com.sistemacompras.entidades.Categoria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaRepositorio {
    private final IConexionBD conexion;

    public CategoriaRepositorio(IConexionBD conexion) { this.conexion = conexion; }

    public List<Categoria> obtenerTodos() throws Exception {
        List<Categoria> lista = new ArrayList<>();
        String sql = "SELECT * FROM Categoria ORDER BY nombre";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public Categoria obtenerPorId(int id) throws Exception {
        String sql = "SELECT * FROM Categoria WHERE CategoriaID = ?";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    public int insertar(Categoria c) throws Exception {
        String sql = "INSERT INTO Categoria (nombre, descripcion) VALUES (?, ?)";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getDescripcion());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return 0;
    }

    public boolean actualizar(Categoria c) throws Exception {
        String sql = "UPDATE Categoria SET nombre=?, descripcion=? WHERE CategoriaID=?";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getDescripcion());
            ps.setInt(3, c.getCategoriaID());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int id) throws Exception {
        String sql = "DELETE FROM Categoria WHERE CategoriaID = ?";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private Categoria mapear(ResultSet rs) throws SQLException {
        return new Categoria(
            rs.getInt("CategoriaID"),
            rs.getString("nombre"),
            rs.getString("descripcion")
        );
    }
}