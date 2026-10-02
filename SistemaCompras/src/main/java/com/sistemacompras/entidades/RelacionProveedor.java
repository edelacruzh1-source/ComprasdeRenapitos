package com.sistemacompras.entidades;

public class RelacionProveedor {
    private int proveedorID1;
    private int proveedorID2;
    private String tipoRelacion;

    public RelacionProveedor() {}

    public RelacionProveedor(int proveedorID1, int proveedorID2, String tipoRelacion) {
        this.proveedorID1 = proveedorID1;
        this.proveedorID2 = proveedorID2;
        this.tipoRelacion = tipoRelacion;
    }

    public int getProveedorID1() { return proveedorID1; }
    public void setProveedorID1(int proveedorID1) { this.proveedorID1 = proveedorID1; }
    public int getProveedorID2() { return proveedorID2; }
    public void setProveedorID2(int proveedorID2) { this.proveedorID2 = proveedorID2; }
    public String getTipoRelacion() { return tipoRelacion; }
    public void setTipoRelacion(String tipoRelacion) { this.tipoRelacion = tipoRelacion; }
}