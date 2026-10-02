package com.sistemacompras.ui;

import com.sistemacompras.datos.ConexionFactory;
import com.sistemacompras.datos.IConexionBD;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private final IConexionBD conexion;
    private final CardLayout cardLayout;
    private final JPanel contenedor;

    public MainFrame(IConexionBD conexion) {
        this.conexion = conexion;

        setTitle("Sistema de Gestión de Adquisiciones y Proveedores");
        setSize(1000, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(900, 600));

        cardLayout = new CardLayout();
        contenedor = new JPanel(cardLayout);

        // Registrar paneles
        contenedor.add(new LoginPanel(conexion, this), "login");
        contenedor.add(new MenuPrincipalPanel(conexion, this), "menu");

        add(contenedor);
        cardLayout.show(contenedor, "login");
    }

    public void mostrarPanel(String nombre) {
        cardLayout.show(contenedor, nombre);
    }

    public void agregarPanel(JPanel panel, String nombre) {
        contenedor.add(panel, nombre);
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
