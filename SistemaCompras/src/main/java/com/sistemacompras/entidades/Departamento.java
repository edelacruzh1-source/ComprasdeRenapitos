package com.sistemacompras.entidades;

public class Departamento {
	private int departamentoID;
	private int sucursalID;
	private String nombre;
	private String descripcion;
	
	public Departamento() {}

	public Departamento(int departamentoID, int sucursalID, String nombre, String descripcion) {
		super();
		this.departamentoID = departamentoID;
		this.sucursalID = sucursalID;
		this.nombre = nombre;
		this.descripcion = descripcion;
	}
	
	 public int getDepartamentoID() { return departamentoID; }
	    public void setDepartamentoID(int departamentoID) { this.departamentoID = departamentoID; }

	    public int getSucursalID() { return sucursalID; }
	    public void setSucursalID(int sucursalID) { this.sucursalID = sucursalID; }

	    public String getNombre() { return nombre; }
	    public void setNombre(String nombre) { this.nombre = nombre; }

	    public String getDescripcion() { return descripcion; }
	    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

	    @Override
	    public String toString() { return nombre; }

}
