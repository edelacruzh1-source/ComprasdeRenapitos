package com.sistemacompras.entidades;

public class ProveedorCategoria {
    private int proveedorID;
    private int categoriaID;

    public ProveedorCategoria() {}

    public ProveedorCategoria(int proveedorID, int categoriaID) {
        this.proveedorID = proveedorID;
        this.categoriaID = categoriaID;
    }

    public int getProveedorID() { return proveedorID; }
    public void setProveedorID(int proveedorID) { this.proveedorID = proveedorID; }
    public int getCategoriaID() { return categoriaID; }
    public void setCategoriaID(int categoriaID) { this.categoriaID = categoriaID; }
}