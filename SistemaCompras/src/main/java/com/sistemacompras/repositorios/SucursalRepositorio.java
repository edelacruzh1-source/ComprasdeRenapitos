package com.sistemacompras.repositorios;
import com.sistemacompras.datos.IConexionBD;
import com.sistemacompras.entidades.Sucursal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SucursalRepositorio {
	private final IConexionBD conexion;
	
	public SucursalRepositorio(IConexionBD conexion) {
		this.conexion = conexion;
	}
	
	public List<Sucursal> obtenerTodos() throws Exception{
		List<Sucursal> lista = new ArrayList<>();
		String sql = "SELECT SucursalID, Codigo, Direccion, Ciudad, Departamento FROM Sucursal";
		try (Connection conn= conexion.crearConexion();
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps. executeQuery()){
			while(rs.next()) {
				lista.add(mapear(rs));
			}
		}
		return lista;
		
	}
	
	public Sucursal obtenerPorId(int id) throws Exception{
		String sql = "SELECT Sucursal ID, Codigo, Direccion, Ciudad, Departamento" +
					 "FROM sucursal WHERE SucursalID=?";
		try (Connection conn=conexion.crearConexion();
				PreparedStatement ps=conn.prepareStatement(sql)
				){
			ps.setInt(1, id);
			try(ResultSet rs=ps.executeQuery()){
				if(rs.next()) return mapear(rs);
			}
		}
		return null;
	}
	
	public int insertar(Sucursal s) throws Exception {
		String sql="INSERT INTO Sucursal (Codigo, Direccion, Ciudad, Departamento)" +
					"VALUES (?,?,?,?)";
		try (Connection conn=conexion.crearConexion();
				PreparedStatement ps=conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
				){
			ps.setString(1, s.getCodigo());
			ps.setString(2, s.getDireccion());
			ps.setString(3, s.getCiudad());
			ps.setString(4, s.getDepartamento());
			ps.executeUpdate();
			
			try(ResultSet rs=ps.getGeneratedKeys()){
				if(rs.next()) return rs.getInt(1);
			}
		}
		return 0;
	}
	public boolean actualizar(Sucursal s) throws Exception {
        String sql = "UPDATE Sucursal SET Codigo=?, Direccion=?, Ciudad=?, Departamento=? " +
                     "WHERE SucursalID=?";

        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getCodigo());
            ps.setString(2, s.getDireccion());
            ps.setString(3, s.getCiudad());
            ps.setString(4, s.getDepartamento());
            ps.setInt(5, s.getSucursalID());
            return ps.executeUpdate() > 0;
        }
    }
	public boolean eliminar(int id) throws Exception {
        String sql = "DELETE FROM Sucursal WHERE SucursalID = ?";

        try (Connection conn = conexion.crearConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
	
	private Sucursal mapear(ResultSet rs) throws SQLException{
		return new Sucursal(
				rs.getInt("SucursalID"),
				rs.getString("Codigo"),
				rs.getString("Direccion"),
				rs.getString("Ciudad"),
				rs.getString("Departamento")
				);
				
	}

}
