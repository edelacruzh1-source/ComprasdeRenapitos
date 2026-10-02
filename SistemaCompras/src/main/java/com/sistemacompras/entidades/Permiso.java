package com.sistemacompras.entidades;

public class Permiso {
    private int rolID;
    private int pantallaID;
    private boolean permiteCrear;
    private boolean permiteLeer;
    private boolean permiteActualizar;
    private boolean permiteBorrar;

    public Permiso() {}

    public Permiso(int rolID, int pantallaID, boolean permiteCrear,
                   boolean permiteLeer, boolean permiteActualizar, boolean permiteBorrar) {
        this.rolID = rolID;
        this.pantallaID = pantallaID;
        this.permiteCrear = permiteCrear;
        this.permiteLeer = permiteLeer;
        this.permiteActualizar = permiteActualizar;
        this.permiteBorrar = permiteBorrar;
    }

    public int getRolID() { return rolID; }
    public void setRolID(int rolID) { this.rolID = rolID; }
    public int getPantallaID() { return pantallaID; }
    public void setPantallaID(int pantallaID) { this.pantallaID = pantallaID; }
    public boolean isPermiteCrear() { return permiteCrear; }
    public void setPermiteCrear(boolean permiteCrear) { this.permiteCrear = permiteCrear; }
    public boolean isPermiteLeer() { return permiteLeer; }
    public void setPermiteLeer(boolean permiteLeer) { this.permiteLeer = permiteLeer; }
    public boolean isPermiteActualizar() { return permiteActualizar; }
    public void setPermiteActualizar(boolean permiteActualizar) { this.permiteActualizar = permiteActualizar; }
    public boolean isPermiteBorrar() { return permiteBorrar; }
    public void setPermiteBorrar(boolean permiteBorrar) { this.permiteBorrar = permiteBorrar; }
}