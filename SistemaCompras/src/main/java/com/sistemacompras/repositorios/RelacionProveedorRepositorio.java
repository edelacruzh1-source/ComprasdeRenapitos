package com.sistemacompras.repositorios;

import com.sistemacompras.datos.IConexionBD;
import com.sistemacompras.entidades.RelacionProveedor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RelacionProveedorRepositorio {
    private final IConexionBD conexion;

    public RelacionProveedorRepositorio(IConexionBD conexion) { this.conexion = conexion; }

    public List<RelacionProveedor> obtenerTodos() throws Exception {
        List<RelacionProveedor> lista = new ArrayList<>();
        String sql = "SELECT * FROM Relacion_Proveedor";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public boolean insertar(RelacionProveedor rp) throws Exception {
        String sql = "INSERT INTO Relacion_Proveedor (ProveedorID1, ProveedorID2, tipoRelacion) VALUES (?, ?, ?)";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, rp.getProveedorID1());
            ps.setInt(2, rp.getProveedorID2());
            ps.setString(3, rp.getTipoRelacion());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int proveedorID1, int proveedorID2) throws Exception {
        String sql = "DELETE FROM Relacion_Proveedor WHERE ProveedorID1 = ? AND ProveedorID2 = ?";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, proveedorID1);
            ps.setInt(2, proveedorID2);
            return ps.executeUpdate() > 0;
        }
    }

    private RelacionProveedor mapear(ResultSet rs) throws SQLException {
        return new RelacionProveedor(
            rs.getInt("ProveedorID1"),
            rs.getInt("ProveedorID2"),
            rs.getString("tipoRelacion")
        );
    }
}
