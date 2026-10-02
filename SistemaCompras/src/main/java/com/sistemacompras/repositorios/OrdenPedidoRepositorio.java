package com.sistemacompras.repositorios;

import com.sistemacompras.datos.IConexionBD;
import com.sistemacompras.entidades.OrdenPedido;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrdenPedidoRepositorio {
    private final IConexionBD conexion;

    public OrdenPedidoRepositorio(IConexionBD conexion) { this.conexion = conexion; }

    public List<OrdenPedido> obtenerTodos() throws Exception {
        List<OrdenPedido> lista = new ArrayList<>();
        String sql = "SELECT * FROM Orden_Pedido";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public List<OrdenPedido> obtenerPorOrden(int ordenID) throws Exception {
        List<OrdenPedido> lista = new ArrayList<>();
        String sql = "SELECT * FROM Orden_Pedido WHERE OrdenID = ?";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ordenID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        }
        return lista;
    }

    public boolean insertar(OrdenPedido op) throws Exception {
        String sql = "INSERT INTO Orden_Pedido (OrdenID, PedidoID) VALUES (?, ?)";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, op.getOrdenID());
            ps.setInt(2, op.getPedidoID());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int ordenID, int pedidoID) throws Exception {
        String sql = "DELETE FROM Orden_Pedido WHERE OrdenID = ? AND PedidoID = ?";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ordenID);
            ps.setInt(2, pedidoID);
            return ps.executeUpdate() > 0;
        }
    }

    private OrdenPedido mapear(ResultSet rs) throws SQLException {
        return new OrdenPedido(rs.getInt("OrdenID"), rs.getInt("PedidoID"));
    }
}