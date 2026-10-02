package com.sistemacompras.entidades;

public class Pantalla {
    private int pantallaID;
    private String nombre;
    private String descripcion;

    public Pantalla() {}

    public Pantalla(int pantallaID, String nombre, String descripcion) {
        this.pantallaID = pantallaID;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public int getPantallaID() { return pantallaID; }
    public void setPantallaID(int pantallaID) { this.pantallaID = pantallaID; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    @Override
    public String toString() { return nombre; }
}