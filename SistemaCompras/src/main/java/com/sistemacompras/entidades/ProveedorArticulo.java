package com.sistemacompras.entidades;

import java.math.BigDecimal;

public class ProveedorArticulo {
    private int proveedorID;
    private int articuloID;
    private BigDecimal precio;

    public ProveedorArticulo() {}

    public ProveedorArticulo(int proveedorID, int articuloID, BigDecimal precio) {
        this.proveedorID = proveedorID;
        this.articuloID = articuloID;
        this.precio = precio;
    }

    public int getProveedorID() { return proveedorID; }
    public void setProveedorID(int proveedorID) { this.proveedorID = proveedorID; }
    public int getArticuloID() { return articuloID; }
    public void setArticuloID(int articuloID) { this.articuloID = articuloID; }
    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }
}