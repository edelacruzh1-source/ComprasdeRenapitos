package com.sistemacompras.repositorios;

import com.sistemacompras.datos.IConexionBD;
import com.sistemacompras.entidades.Departamento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartamentoRepositorio {
    private final IConexionBD conexion;

    public DepartamentoRepositorio(IConexionBD conexion) { this.conexion = conexion; }

    public List<Departamento> obtenerTodos() throws Exception {
        List<Departamento> lista = new ArrayList<>();
        String sql = "SELECT * FROM Departamento ORDER BY DepartamentoID";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public List<Departamento> obtenerPorSucursal(int sucursalID) throws Exception {
        List<Departamento> lista = new ArrayList<>();
        String sql = "SELECT * FROM Departamento WHERE SucursalID = ? ORDER BY nombre";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, sucursalID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        }
        return lista;
    }

    public Departamento obtenerPorId(int id) throws Exception {
        String sql = "SELECT * FROM Departamento WHERE DepartamentoID = ?";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    public int insertar(Departamento d) throws Exception {
        String sql = "INSERT INTO Departamento (SucursalID, nombre, descripcion) VALUES (?, ?, ?)";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, d.getSucursalID());
            ps.setString(2, d.getNombre());
            ps.setString(3, d.getDescripcion());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return 0;
    }

    public boolean actualizar(Departamento d) throws Exception {
        String sql = "UPDATE Departamento SET SucursalID=?, nombre=?, descripcion=? WHERE DepartamentoID=?";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, d.getSucursalID());
            ps.setString(2, d.getNombre());
            ps.setString(3, d.getDescripcion());
            ps.setInt(4, d.getDepartamentoID());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int id) throws Exception {
        String sql = "DELETE FROM Departamento WHERE DepartamentoID = ?";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private Departamento mapear(ResultSet rs) throws SQLException {
        return new Departamento(
            rs.getInt("DepartamentoID"),
            rs.getInt("SucursalID"),
            rs.getString("nombre"),
            rs.getString("descripcion")
        );
    }
}