package com.sistemacompras.repositorios;

import com.sistemacompras.datos.IConexionBD;
import com.sistemacompras.entidades.Articulo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArticuloRepositorio {
    private final IConexionBD conexion;

    public ArticuloRepositorio(IConexionBD conexion) { this.conexion = conexion; }

    public List<Articulo> obtenerTodos() throws Exception {
        List<Articulo> lista = new ArrayList<>();
        String sql = "SELECT * FROM Articulo ORDER BY nombre";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public Articulo obtenerPorId(int id) throws Exception {
        String sql = "SELECT * FROM Articulo WHERE ArticuloID = ?";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    public int insertar(Articulo a) throws Exception {
        String sql = "INSERT INTO Articulo (codigo, nombre, descripcion) VALUES (?, ?, ?)";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, a.getCodigo());
            ps.setString(2, a.getNombre());
            ps.setString(3, a.getDescripcion());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return 0;
    }

    public boolean actualizar(Articulo a) throws Exception {
        String sql = "UPDATE Articulo SET codigo=?, nombre=?, descripcion=? WHERE ArticuloID=?";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, a.getCodigo());
            ps.setString(2, a.getNombre());
            ps.setString(3, a.getDescripcion());
            ps.setInt(4, a.getArticuloID());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int id) throws Exception {
        String sql = "DELETE FROM Articulo WHERE ArticuloID = ?";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private Articulo mapear(ResultSet rs) throws SQLException {
        return new Articulo(
            rs.getInt("ArticuloID"),
            rs.getString("codigo"),
            rs.getString("nombre"),
            rs.getString("descripcion")
        );
    }
}