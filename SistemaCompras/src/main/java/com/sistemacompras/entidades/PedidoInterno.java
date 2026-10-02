package com.sistemacompras.entidades;

import java.time.LocalDate;

public class PedidoInterno {
    private int pedidoID;
    private int departamentoID;
    private int articuloID;
    private int cantidad;
    private LocalDate fechaSolicitud;
    private LocalDate fechaNecesidad;

    public PedidoInterno() {}

    public PedidoInterno(int pedidoID, int departamentoID, int articuloID,
                         int cantidad, LocalDate fechaSolicitud, LocalDate fechaNecesidad) {
        this.pedidoID = pedidoID;
        this.departamentoID = departamentoID;
        this.articuloID = articuloID;
        this.cantidad = cantidad;
        this.fechaSolicitud = fechaSolicitud;
        this.fechaNecesidad = fechaNecesidad;
    }

    public int getPedidoID() { return pedidoID; }
    public void setPedidoID(int pedidoID) { this.pedidoID = pedidoID; }
    public int getDepartamentoID() { return departamentoID; }
    public void setDepartamentoID(int departamentoID) { this.departamentoID = departamentoID; }
    public int getArticuloID() { return articuloID; }
    public void setArticuloID(int articuloID) { this.articuloID = articuloID; }
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public LocalDate getFechaSolicitud() { return fechaSolicitud; }
    public void setFechaSolicitud(LocalDate fechaSolicitud) { this.fechaSolicitud = fechaSolicitud; }
    public LocalDate getFechaNecesidad() { return fechaNecesidad; }
    public void setFechaNecesidad(LocalDate fechaNecesidad) { this.fechaNecesidad = fechaNecesidad; }
}