package com.sistemacompras.entidades;

public class Rol {
    private int rolID;
    private String nombre;
    private String descripcion;

    public Rol() {}

    public Rol(int rolID, String nombre, String descripcion) {
        this.rolID = rolID;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public int getRolID() { return rolID; }
    public void setRolID(int rolID) { this.rolID = rolID; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    @Override
    public String toString() { return nombre; }
}