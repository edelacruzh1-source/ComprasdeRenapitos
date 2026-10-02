package com.sistemacompras.entidades;

import java.time.LocalDate;

public class Adjudicacion {
    private int adjudicacionID;
    private int ordenID;
    private LocalDate fechaResolucion;

    public Adjudicacion() {}

    public Adjudicacion(int adjudicacionID, int ordenID, LocalDate fechaResolucion) {
        this.adjudicacionID = adjudicacionID;
        this.ordenID = ordenID;
        this.fechaResolucion = fechaResolucion;
    }

    public int getAdjudicacionID() { return adjudicacionID; }
    public void setAdjudicacionID(int adjudicacionID) { this.adjudicacionID = adjudicacionID; }
    public int getOrdenID() { return ordenID; }
    public void setOrdenID(int ordenID) { this.ordenID = ordenID; }
    public LocalDate getFechaResolucion() { return fechaResolucion; }
    public void setFechaResolucion(LocalDate fechaResolucion) { this.fechaResolucion = fechaResolucion; }
}
