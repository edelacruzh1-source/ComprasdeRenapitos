package com.sistemacompras.entidades;

public class Categoria {
    private int categoriaID;
    private String nombre;
    private String descripcion;

    public Categoria() {}

    public Categoria(int categoriaID, String nombre, String descripcion) {
        this.categoriaID = categoriaID;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public int getCategoriaID() { return categoriaID; }
    public void setCategoriaID(int categoriaID) { this.categoriaID = categoriaID; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    @Override
    public String toString() { return nombre; }
}