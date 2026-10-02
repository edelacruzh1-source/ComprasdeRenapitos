package com.sistemacompras.repositorios;

import com.sistemacompras.datos.IConexionBD;
import com.sistemacompras.entidades.Pantalla;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PantallaRepositorio {
    private final IConexionBD conexion;

    public PantallaRepositorio(IConexionBD conexion) { this.conexion = conexion; }

    public List<Pantalla> obtenerTodos() throws Exception {
        List<Pantalla> lista = new ArrayList<>();
        String sql = "SELECT * FROM Pantalla ORDER BY nombre";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public Pantalla obtenerPorId(int id) throws Exception {
        String sql = "SELECT * FROM Pantalla WHERE PantallaID = ?";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    public int insertar(Pantalla p) throws Exception {
        String sql = "INSERT INTO Pantalla (nombre, descripcion) VALUES (?, ?)";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getDescripcion());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return 0;
    }

    public boolean actualizar(Pantalla p) throws Exception {
        String sql = "UPDATE Pantalla SET nombre=?, descripcion=? WHERE PantallaID=?";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getDescripcion());
            ps.setInt(3, p.getPantallaID());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int id) throws Exception {
        String sql = "DELETE FROM Pantalla WHERE PantallaID = ?";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private Pantalla mapear(ResultSet rs) throws SQLException {
        return new Pantalla(
            rs.getInt("PantallaID"),
            rs.getString("nombre"),
            rs.getString("descripcion")
        );
    }
}