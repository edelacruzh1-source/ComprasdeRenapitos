package com.sistemacompras.entidades;

import java.math.BigDecimal;

public class AdjudicacionDetalle {
    private int adjudicacionID;
    private int pedidoID;
    private int proveedorID;
    private int cantidadFinal;
    private BigDecimal precioAcordado;

    public AdjudicacionDetalle() {}

    public AdjudicacionDetalle(int adjudicacionID, int pedidoID, int proveedorID,
                               int cantidadFinal, BigDecimal precioAcordado) {
        this.adjudicacionID = adjudicacionID;
        this.pedidoID = pedidoID;
        this.proveedorID = proveedorID;
        this.cantidadFinal = cantidadFinal;
        this.precioAcordado = precioAcordado;
    }

    public int getAdjudicacionID() { return adjudicacionID; }
    public void setAdjudicacionID(int adjudicacionID) { this.adjudicacionID = adjudicacionID; }
    public int getPedidoID() { return pedidoID; }
    public void setPedidoID(int pedidoID) { this.pedidoID = pedidoID; }
    public int getProveedorID() { return proveedorID; }
    public void setProveedorID(int proveedorID) { this.proveedorID = proveedorID; }
    public int getCantidadFinal() { return cantidadFinal; }
    public void setCantidadFinal(int cantidadFinal) { this.cantidadFinal = cantidadFinal; }
    public BigDecimal getPrecioAcordado() { return precioAcordado; }
    public void setPrecioAcordado(BigDecimal precioAcordado) { this.precioAcordado = precioAcordado; }
}
