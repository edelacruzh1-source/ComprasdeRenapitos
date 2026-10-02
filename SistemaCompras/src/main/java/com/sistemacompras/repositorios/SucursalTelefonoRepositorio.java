package com.sistemacompras.repositorios;

import com.sistemacompras.datos.IConexionBD;
import com.sistemacompras.entidades.SucursalTelefono;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SucursalTelefonoRepositorio {
    private final IConexionBD conexion;

    public SucursalTelefonoRepositorio(IConexionBD conexion) { this.conexion = conexion; }

    public List<SucursalTelefono> obtenerTodos() throws Exception {
        List<SucursalTelefono> lista = new ArrayList<>();
        String sql = "SELECT * FROM Sucursal_Telefono ORDER BY SucursalID";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public List<SucursalTelefono> obtenerPorSucursal(int sucursalID) throws Exception {
        List<SucursalTelefono> lista = new ArrayList<>();
        String sql = "SELECT * FROM Sucursal_Telefono WHERE SucursalID = ?";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, sucursalID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        }
        return lista;
    }

    public boolean insertar(SucursalTelefono st) throws Exception {
        String sql = "INSERT INTO Sucursal_Telefono (SucursalID, telefono) VALUES (?, ?)";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, st.getSucursalID());
            ps.setString(2, st.getTelefono());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int sucursalID, String telefono) throws Exception {
        String sql = "DELETE FROM Sucursal_Telefono WHERE SucursalID = ? AND telefono = ?";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, sucursalID);
            ps.setString(2, telefono);
            return ps.executeUpdate() > 0;
        }
    }

    private SucursalTelefono mapear(ResultSet rs) throws SQLException {
        return new SucursalTelefono(rs.getInt("SucursalID"), rs.getString("telefono"));
    }
}