package com.sistemacompras.entidades;

import java.math.BigDecimal;
import java.time.LocalDate;

public class OfertaProveedor {
    private int ofertaID;
    private int proveedorID;
    private int pedidoID;
    private BigDecimal precioUnitario;
    private LocalDate fechaOferta;

    public OfertaProveedor() {}

    public OfertaProveedor(int ofertaID, int proveedorID, int pedidoID,
                           BigDecimal precioUnitario, LocalDate fechaOferta) {
        this.ofertaID = ofertaID;
        this.proveedorID = proveedorID;
        this.pedidoID = pedidoID;
        this.precioUnitario = precioUnitario;
        this.fechaOferta = fechaOferta;
    }

    public int getOfertaID() { return ofertaID; }
    public void setOfertaID(int ofertaID) { this.ofertaID = ofertaID; }
    public int getProveedorID() { return proveedorID; }
    public void setProveedorID(int proveedorID) { this.proveedorID = proveedorID; }
    public int getPedidoID() { return pedidoID; }
    public void setPedidoID(int pedidoID) { this.pedidoID = pedidoID; }
    public BigDecimal getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(BigDecimal precioUnitario) { this.precioUnitario = precioUnitario; }
    public LocalDate getFechaOferta() { return fechaOferta; }
    public void setFechaOferta(LocalDate fechaOferta) { this.fechaOferta = fechaOferta; }
}