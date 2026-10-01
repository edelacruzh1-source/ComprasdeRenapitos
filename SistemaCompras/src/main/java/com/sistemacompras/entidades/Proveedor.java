package com.sistemacompras.entidades;

public class Proveedor {
	private int proveedorID;
	private String nit;
	private String nombreComercial;
	private String direccion;
	private String telefono;
	
	public Proveedor() {
		
	}

	public Proveedor(int proveedorID, String nit, String nombreComercial, String direccion, String telefono) {
		super();
		this.proveedorID = proveedorID;
		this.nit = nit;
		this.nombreComercial = nombreComercial;
		this.direccion = direccion;
		this.telefono = telefono;
	}
	public int getProveedorID() { return proveedorID; }
    public void setProveedorID(int proveedorID) { this.proveedorID = proveedorID; }

    public String getNit() { return nit; }
    public void setNit(String nit) { this.nit = nit; }

    public String getNombreComercial() { return nombreComercial; }
    public void setNombreComercial(String nombreComercial) { this.nombreComercial = nombreComercial; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    @Override
    public String toString() { return nombreComercial; }

}
