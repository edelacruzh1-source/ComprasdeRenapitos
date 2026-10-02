package com.sistemacompras.entidades;

public class OrdenPedido {
    private int ordenID;
    private int pedidoID;

    public OrdenPedido() {}

    public OrdenPedido(int ordenID, int pedidoID) {
        this.ordenID = ordenID;
        this.pedidoID = pedidoID;
    }

    public int getOrdenID() { return ordenID; }
    public void setOrdenID(int ordenID) { this.ordenID = ordenID; }
    public int getPedidoID() { return pedidoID; }
    public void setPedidoID(int pedidoID) { this.pedidoID = pedidoID; }
}