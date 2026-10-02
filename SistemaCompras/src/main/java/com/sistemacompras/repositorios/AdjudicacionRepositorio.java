package com.sistemacompras.repositorios;

import com.sistemacompras.datos.IConexionBD;
import com.sistemacompras.entidades.Adjudicacion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdjudicacionRepositorio {
    private final IConexionBD conexion;

    public AdjudicacionRepositorio(IConexionBD conexion) { this.conexion = conexion; }

    public List<Adjudicacion> obtenerTodos() throws Exception {
        List<Adjudicacion> lista = new ArrayList<>();
        String sql = "SELECT * FROM Adjudicacion ORDER BY AdjudicacionID";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public Adjudicacion obtenerPorId(int id) throws Exception {
        String sql = "SELECT * FROM Adjudicacion WHERE AdjudicacionID = ?";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    public Adjudicacion obtenerPorOrden(int ordenID) throws Exception {
        String sql = "SELECT * FROM Adjudicacion WHERE OrdenID = ?";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ordenID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    public int insertar(Adjudicacion a) throws Exception {
        String sql = "INSERT INTO Adjudicacion (OrdenID, fechaResolucion) VALUES (?, ?)";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, a.getOrdenID());
            ps.setDate(2, Date.valueOf(a.getFechaResolucion()));
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return 0;
    }

    public boolean actualizar(Adjudicacion a) throws Exception {
        String sql = "UPDATE Adjudicacion SET OrdenID=?, fechaResolucion=? WHERE AdjudicacionID=?";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, a.getOrdenID());
            ps.setDate(2, Date.valueOf(a.getFechaResolucion()));
            ps.setInt(3, a.getAdjudicacionID());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int id) throws Exception {
        String sql = "DELETE FROM Adjudicacion WHERE AdjudicacionID = ?";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private Adjudicacion mapear(ResultSet rs) throws SQLException {
        return new Adjudicacion(
            rs.getInt("AdjudicacionID"),
            rs.getInt("OrdenID"),
            rs.getDate("fechaResolucion").toLocalDate()
        );
    }
}