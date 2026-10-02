package com.sistemacompras.repositorios;

import com.sistemacompras.datos.IConexionBD;
import com.sistemacompras.entidades.OrdenCompra;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrdenCompraRepositorio {
    private final IConexionBD conexion;

    public OrdenCompraRepositorio(IConexionBD conexion) { this.conexion = conexion; }

    public List<OrdenCompra> obtenerTodos() throws Exception {
        List<OrdenCompra> lista = new ArrayList<>();
        String sql = "SELECT * FROM Orden_Compra ORDER BY OrdenID";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public OrdenCompra obtenerPorId(int id) throws Exception {
        String sql = "SELECT * FROM Orden_Compra WHERE OrdenID = ?";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    public int insertar(OrdenCompra o) throws Exception {
        String sql = "INSERT INTO Orden_Compra (descripcion, fechaCreacion, fechaLimite, tipoOrden, subtipoOrden) " +
                     "VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, o.getDescripcion());
            ps.setDate(2, Date.valueOf(o.getFechaCreacion()));
            ps.setDate(3, Date.valueOf(o.getFechaLimite()));
            ps.setString(4, o.getTipoOrden());
            if (o.getSubtipoOrden() == null) {
                ps.setNull(5, Types.VARCHAR);
            } else {
                ps.setString(5, o.getSubtipoOrden());
            }
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return 0;
    }

    public boolean actualizar(OrdenCompra o) throws Exception {
        String sql = "UPDATE Orden_Compra SET descripcion=?, fechaCreacion=?, fechaLimite=?, " +
                     "tipoOrden=?, subtipoOrden=? WHERE OrdenID=?";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, o.getDescripcion());
            ps.setDate(2, Date.valueOf(o.getFechaCreacion()));
            ps.setDate(3, Date.valueOf(o.getFechaLimite()));
            ps.setString(4, o.getTipoOrden());
            if (o.getSubtipoOrden() == null) {
                ps.setNull(5, Types.VARCHAR);
            } else {
                ps.setString(5, o.getSubtipoOrden());
            }
            ps.setInt(6, o.getOrdenID());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int id) throws Exception {
        String sql = "DELETE FROM Orden_Compra WHERE OrdenID = ?";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private OrdenCompra mapear(ResultSet rs) throws SQLException {
        return new OrdenCompra(
            rs.getInt("OrdenID"),
            rs.getString("descripcion"),
            rs.getDate("fechaCreacion").toLocalDate(),
            rs.getDate("fechaLimite").toLocalDate(),
            rs.getString("tipoOrden"),
            rs.getString("subtipoOrden")
        );
    }
}