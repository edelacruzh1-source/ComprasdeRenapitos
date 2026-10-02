package com.sistemacompras.entidades;

public class SucursalTelefono {
    private int sucursalID;
    private String telefono;

    public SucursalTelefono() {}

    public SucursalTelefono(int sucursalID, String telefono) {
        this.sucursalID = sucursalID;
        this.telefono = telefono;
    }

    public int getSucursalID() { return sucursalID; }
    public void setSucursalID(int sucursalID) { this.sucursalID = sucursalID; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
}