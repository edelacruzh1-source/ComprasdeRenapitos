package com.sistemacompras.repositorios;

import com.sistemacompras.datos.IConexionBD;
import com.sistemacompras.entidades.Permiso;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PermisoRepositorio {
    private final IConexionBD conexion;

    public PermisoRepositorio(IConexionBD conexion) { this.conexion = conexion; }

    public List<Permiso> obtenerTodos() throws Exception {
        List<Permiso> lista = new ArrayList<>();
        String sql = "SELECT * FROM Permiso";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public List<Permiso> obtenerPorRol(int rolID) throws Exception {
        List<Permiso> lista = new ArrayList<>();
        String sql = "SELECT * FROM Permiso WHERE RolID = ?";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, rolID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        }
        return lista;
    }

    public Permiso obtener(int rolID, int pantallaID) throws Exception {
        String sql = "SELECT * FROM Permiso WHERE RolID = ? AND PantallaID = ?";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, rolID);
            ps.setInt(2, pantallaID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    public boolean insertar(Permiso p) throws Exception {
        String sql = "INSERT INTO Permiso (RolID, PantallaID, permiteCrear, permiteLeer, " +
                     "permiteActualizar, permiteBorrar) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, p.getRolID());
            ps.setInt(2, p.getPantallaID());
            ps.setBoolean(3, p.isPermiteCrear());
            ps.setBoolean(4, p.isPermiteLeer());
            ps.setBoolean(5, p.isPermiteActualizar());
            ps.setBoolean(6, p.isPermiteBorrar());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean actualizar(Permiso p) throws Exception {
        String sql = "UPDATE Permiso SET permiteCrear=?, permiteLeer=?, " +
                     "permiteActualizar=?, permiteBorrar=? WHERE RolID=? AND PantallaID=?";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, p.isPermiteCrear());
            ps.setBoolean(2, p.isPermiteLeer());
            ps.setBoolean(3, p.isPermiteActualizar());
            ps.setBoolean(4, p.isPermiteBorrar());
            ps.setInt(5, p.getRolID());
            ps.setInt(6, p.getPantallaID());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int rolID, int pantallaID) throws Exception {
        String sql = "DELETE FROM Permiso WHERE RolID = ? AND PantallaID = ?";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, rolID);
            ps.setInt(2, pantallaID);
            return ps.executeUpdate() > 0;
        }
    }

    private Permiso mapear(ResultSet rs) throws SQLException {
        return new Permiso(
            rs.getInt("RolID"),
            rs.getInt("PantallaID"),
            rs.getBoolean("permiteCrear"),
            rs.getBoolean("permiteLeer"),
            rs.getBoolean("permiteActualizar"),
            rs.getBoolean("permiteBorrar")
        );
    }
}