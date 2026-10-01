package com.sistemacompras.entidades;

public class Sucursal {
	private int sucursalID;
	private String codigo;
	private String direccion;
	private String ciudad;
	private String departamento;
	
	public Sucursal () {
		
	}
	
	public Sucursal(int sucursalID, String codigo, String direccion, String ciudad, String departamento) {
		super();
		this.sucursalID = sucursalID;
		this.codigo = codigo;
		this.direccion = direccion;
		this.ciudad = ciudad;
		this.departamento = departamento;
	}

	 public int getSucursalID() { return sucursalID; }
	    public void setSucursalID(int sucursalID) { this.sucursalID = sucursalID; }

	    public String getCodigo() { return codigo; }
	    public void setCodigo(String codigo) { this.codigo = codigo; }

	    public String getDireccion() { return direccion; }
	    public void setDireccion(String direccion) { this.direccion = direccion; }

	    public String getCiudad() { return ciudad; }
	    public void setCiudad(String ciudad) { this.ciudad = ciudad; }

	    public String getDepartamento() { return departamento; }
	    public void setDepartamento(String departamento) { this.departamento = departamento; }

	    @Override
	    public String toString() {
	        return codigo + " - " + direccion + ", " + ciudad;
	    }
	}