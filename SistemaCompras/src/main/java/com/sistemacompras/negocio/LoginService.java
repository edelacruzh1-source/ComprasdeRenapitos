package com.sistemacompras.negocio;

import com.sistemacompras.datos.IConexionBD;
import com.sistemacompras.entidades.Usuario;
import com.sistemacompras.repositorios.UsuarioRepositorio;

public class LoginService {
    private final UsuarioRepositorio repo;

    public LoginService(IConexionBD conexion) {
        this.repo = new UsuarioRepositorio(conexion);
    }

    /**
     * Autentica un usuario.
     * @return el Usuario si las credenciales son válidas, null si no
     */
    public Usuario autenticar(String nombreUsuario, String passwordTextoPlano) throws Exception {
        if (nombreUsuario == null || passwordTextoPlano == null) return null;

        Usuario u = repo.obtenerPorNombre(nombreUsuario);
        if (u == null) return null;
        if (!u.isActivo()) return null;

        // Verifica el hash
        if (!PasswordUtil.verificar(passwordTextoPlano, u.getPasswordHash())) {
            return null;
        }

        return u;
    }
}