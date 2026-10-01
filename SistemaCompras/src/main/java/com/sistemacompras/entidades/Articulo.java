package com.sistemacompras.entidades;

public class Articulo {
	private int articuloID;
	private String codigo;
	private String nombre;
	private String descripcion;
	
	public Articulo() {
		
	}

	public Articulo(int articuloID, String codigo, String nombre, String descripcion) {
		super();
		this.articuloID = articuloID;
		this.codigo = codigo;
		this.nombre = nombre;
		this.descripcion = descripcion;
	}
	public int getArticuloID() { return articuloID; }
    public void setArticuloID(int articuloID) { this.articuloID = articuloID; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    @Override
    public String toString() { return codigo + " - " + nombre; }

}
