package com.sistemacompras.entidades;

import java.time.LocalDate;

public class OrdenCompra {
    private int ordenID;
    private String descripcion;
    private LocalDate fechaCreacion;
    private LocalDate fechaLimite;
    private String tipoOrden;
    private String subtipoOrden;

    public OrdenCompra() {}

    public OrdenCompra(int ordenID, String descripcion, LocalDate fechaCreacion,
                       LocalDate fechaLimite, String tipoOrden, String subtipoOrden) {
        this.ordenID = ordenID;
        this.descripcion = descripcion;
        this.fechaCreacion = fechaCreacion;
        this.fechaLimite = fechaLimite;
        this.tipoOrden = tipoOrden;
        this.subtipoOrden = subtipoOrden;
    }

    public int getOrdenID() { return ordenID; }
    public void setOrdenID(int ordenID) { this.ordenID = ordenID; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public LocalDate getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDate fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    public LocalDate getFechaLimite() { return fechaLimite; }
    public void setFechaLimite(LocalDate fechaLimite) { this.fechaLimite = fechaLimite; }
    public String getTipoOrden() { return tipoOrden; }
    public void setTipoOrden(String tipoOrden) { this.tipoOrden = tipoOrden; }
    public String getSubtipoOrden() { return subtipoOrden; }
    public void setSubtipoOrden(String subtipoOrden) { this.subtipoOrden = subtipoOrden; }

    @Override
    public String toString() { return "Orden #" + ordenID + " - " + descripcion; }
}