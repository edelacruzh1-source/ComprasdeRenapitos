package com.sistemacompras.repositorios;

import com.sistemacompras.datos.IConexionBD;
import com.sistemacompras.entidades.OfertaProveedor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OfertaProveedorRepositorio {
    private final IConexionBD conexion;

    public OfertaProveedorRepositorio(IConexionBD conexion) { this.conexion = conexion; }

    public List<OfertaProveedor> obtenerTodos() throws Exception {
        List<OfertaProveedor> lista = new ArrayList<>();
        String sql = "SELECT * FROM Oferta_Proveedor ORDER BY OfertaID";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public List<OfertaProveedor> obtenerPorPedido(int pedidoID) throws Exception {
        List<OfertaProveedor> lista = new ArrayList<>();
        String sql = "SELECT * FROM Oferta_Proveedor WHERE PedidoID = ?";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, pedidoID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        }
        return lista;
    }

    public OfertaProveedor obtenerPorId(int id) throws Exception {
        String sql = "SELECT * FROM Oferta_Proveedor WHERE OfertaID = ?";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    public int insertar(OfertaProveedor o) throws Exception {
        String sql = "INSERT INTO Oferta_Proveedor (ProveedorID, PedidoID, precioUnitario, fechaOferta) " +
                     "VALUES (?, ?, ?, ?)";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, o.getProveedorID());
            ps.setInt(2, o.getPedidoID());
            ps.setBigDecimal(3, o.getPrecioUnitario());
            ps.setDate(4, Date.valueOf(o.getFechaOferta()));
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return 0;
    }

    public boolean actualizar(OfertaProveedor o) throws Exception {
        String sql = "UPDATE Oferta_Proveedor SET ProveedorID=?, PedidoID=?, precioUnitario=?, " +
                     "fechaOferta=? WHERE OfertaID=?";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, o.getProveedorID());
            ps.setInt(2, o.getPedidoID());
            ps.setBigDecimal(3, o.getPrecioUnitario());
            ps.setDate(4, Date.valueOf(o.getFechaOferta()));
            ps.setInt(5, o.getOfertaID());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int id) throws Exception {
        String sql = "DELETE FROM Oferta_Proveedor WHERE OfertaID = ?";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private OfertaProveedor mapear(ResultSet rs) throws SQLException {
        return new OfertaProveedor(
            rs.getInt("OfertaID"),
            rs.getInt("ProveedorID"),
            rs.getInt("PedidoID"),
            rs.getBigDecimal("precioUnitario"),
            rs.getDate("fechaOferta").toLocalDate()
        );
    }
}