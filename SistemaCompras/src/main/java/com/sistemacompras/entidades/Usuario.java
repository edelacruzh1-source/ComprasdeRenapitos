package com.sistemacompras.entidades;

public class Usuario {
    private int usuarioID;
    private String nombreUsuario;
    private String passwordHash;
    private int rolID;
    private boolean activo;

    // Campo auxiliar para mostrar el nombre del rol
    private String nombreRol;

    public Usuario() {}

    public Usuario(int usuarioID, String nombreUsuario, String passwordHash,
                   int rolID, boolean activo) {
        this.usuarioID = usuarioID;
        this.nombreUsuario = nombreUsuario;
        this.passwordHash = passwordHash;
        this.rolID = rolID;
        this.activo = activo;
    }

    public int getUsuarioID() { return usuarioID; }
    public void setUsuarioID(int usuarioID) { this.usuarioID = usuarioID; }
    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public int getRolID() { return rolID; }
    public void setRolID(int rolID) { this.rolID = rolID; }
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
    public String getNombreRol() { return nombreRol; }
    public void setNombreRol(String nombreRol) { this.nombreRol = nombreRol; }
}