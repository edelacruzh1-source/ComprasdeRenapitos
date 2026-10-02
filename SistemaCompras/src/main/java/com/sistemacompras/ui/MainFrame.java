package com.sistemacompras.ui;

import com.sistemacompras.datos.ConexionFactory;
import com.sistemacompras.datos.IConexionBD;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private final IConexionBD conexion;
    private final CardLayout cardLayout;
    private final JPanel contenedor;
    private MenuPrincipalPanel menuPanel; // referencia al menú actual

    public MainFrame(IConexionBD conexion) {
        this.conexion = conexion;

        setTitle("Sistema de Gestión de Adquisiciones y Proveedores");
        setSize(1000, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(900, 600));

        cardLayout = new CardLayout();
        contenedor = new JPanel(cardLayout);

        // Solo el LoginPanel se crea al inicio
        contenedor.add(new LoginPanel(conexion, this), "login");

        add(contenedor);
        cardLayout.show(contenedor, "login");
    }

    /**
     * Muestra un panel. Si es "menu", lo crea en ese momento
     * (con los permisos ya cargados en SesionActual).
     */
    public void mostrarPanel(String nombre) {
        if ("menu".equals(nombre)) {
            // Remover menú anterior si existe
            if (menuPanel != null) {
                contenedor.remove(menuPanel);
            }
            // Crear menú nuevo (con permisos frescos)
            menuPanel = new MenuPrincipalPanel(conexion, this);
            contenedor.add(menuPanel, "menu");
            contenedor.revalidate();
            contenedor.repaint();
        }
        cardLayout.show(contenedor, nombre);
    }

    /**
     * Fuerza la reconstrucción del menú en el próximo mostrarPanel("menu").
     * Útil al cerrar sesión.
     */
    public void reiniciarMenu() {
        if (menuPanel != null) {
            contenedor.remove(menuPanel);
            menuPanel = null;
        }
    }

    public static void main(String[] args) {
        IConexionBD conexion = ConexionFactory.obtenerConexion();
        if (!conexion.probarConexion()) {
            JOptionPane.showMessageDialog(null,
                "No se pudo conectar a " + conexion.getNombreGestor());
            return;
        }
        SwingUtilities.invokeLater(() -> new MainFrame(conexion).setVisible(true));
    }
}