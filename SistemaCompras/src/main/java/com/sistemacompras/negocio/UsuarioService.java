package com.sistemacompras.negocio;

import com.sistemacompras.datos.IConexionBD;
import com.sistemacompras.entidades.Usuario;
import com.sistemacompras.repositorios.UsuarioRepositorio;

import java.util.List;

public class UsuarioService {
    private final UsuarioRepositorio repo;

    public UsuarioService(IConexionBD conexion) {
        this.repo = new UsuarioRepositorio(conexion);
    }

    public List<Usuario> obtenerTodos() throws Exception {
        return repo.obtenerTodos();
    }

    public Usuario obtenerPorId(int id) throws Exception {
        return repo.obtenerPorId(id);
    }

    /**
     * Crea un usuario generando el hash automáticamente.
     * @param passwordTextoPlano la contraseña sin hashear
     */
    public int crear(String nombreUsuario, String passwordTextoPlano,
                     int rolID, boolean activo) throws Exception {
        validarUsuario(nombreUsuario, passwordTextoPlano);

        if (repo.obtenerPorNombre(nombreUsuario) != null) {
            throw new IllegalArgumentException("El nombre de usuario ya existe");
        }

        Usuario u = new Usuario();
        u.setNombreUsuario(nombreUsuario);
        u.setPasswordHash(PasswordUtil.hash(passwordTextoPlano)); // ← HASH AUTOMÁTICO
        u.setRolID(rolID);
        u.setActivo(activo);

        return repo.insertar(u);
    }

    /**
     * Actualiza un usuario. Si passwordTextoPlano es null o vacío,
     * la contraseña NO se cambia.
     */
    public boolean actualizar(int usuarioID, String nombreUsuario,
                              String passwordTextoPlano, int rolID, boolean activo) throws Exception {
        if (nombreUsuario == null || nombreUsuario.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de usuario es obligatorio");
        }

        Usuario u = new Usuario();
        u.setUsuarioID(usuarioID);
        u.setNombreUsuario(nombreUsuario);
        u.setRolID(rolID);
        u.setActivo(activo);

        if (passwordTextoPlano != null && !passwordTextoPlano.isEmpty()) {
            u.setPasswordHash(PasswordUtil.hash(passwordTextoPlano)); // ← HASH AUTOMÁTICO
        } else {
            u.setPasswordHash(null); // no cambiar
        }

        return repo.actualizar(u);
    }

    public boolean eliminar(int id) throws Exception {
        return repo.eliminar(id);
    }

    private void validarUsuario(String nombreUsuario, String password) {
        if (nombreUsuario == null || nombreUsuario.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de usuario es obligatorio");
        }
        if (password == null || password.length() < 6) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 6 caracteres");
        }
    }
}