package com.sistemacompras.repositorios;

import com.sistemacompras.datos.IConexionBD;
import com.sistemacompras.entidades.PedidoInterno;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoInternoRepositorio {
    private final IConexionBD conexion;

    public PedidoInternoRepositorio(IConexionBD conexion) { this.conexion = conexion; }

    public List<PedidoInterno> obtenerTodos() throws Exception {
        List<PedidoInterno> lista = new ArrayList<>();
        String sql = "SELECT * FROM Pedido_Interno ORDER BY PedidoID";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public PedidoInterno obtenerPorId(int id) throws Exception {
        String sql = "SELECT * FROM Pedido_Interno WHERE PedidoID = ?";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    public int insertar(PedidoInterno p) throws Exception {
        String sql = "INSERT INTO Pedido_Interno (DepartamentoID, ArticuloID, cantidad, fechaSolicitud, fechaNecesidad) " +
                     "VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, p.getDepartamentoID());
            ps.setInt(2, p.getArticuloID());
            ps.setInt(3, p.getCantidad());
            ps.setDate(4, Date.valueOf(p.getFechaSolicitud()));
            ps.setDate(5, Date.valueOf(p.getFechaNecesidad()));
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return 0;
    }

    public boolean actualizar(PedidoInterno p) throws Exception {
        String sql = "UPDATE Pedido_Interno SET DepartamentoID=?, ArticuloID=?, cantidad=?, " +
                     "fechaSolicitud=?, fechaNecesidad=? WHERE PedidoID=?";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, p.getDepartamentoID());
            ps.setInt(2, p.getArticuloID());
            ps.setInt(3, p.getCantidad());
            ps.setDate(4, Date.valueOf(p.getFechaSolicitud()));
            ps.setDate(5, Date.valueOf(p.getFechaNecesidad()));
            ps.setInt(6, p.getPedidoID());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int id) throws Exception {
        String sql = "DELETE FROM Pedido_Interno WHERE PedidoID = ?";
        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private PedidoInterno mapear(ResultSet rs) throws SQLException {
        return new PedidoInterno(
            rs.getInt("PedidoID"),
            rs.getInt("DepartamentoID"),
            rs.getInt("ArticuloID"),
            rs.getInt("cantidad"),
            rs.getDate("fechaSolicitud").toLocalDate(),
            rs.getDate("fechaNecesidad").toLocalDate()
        );
    }
}
