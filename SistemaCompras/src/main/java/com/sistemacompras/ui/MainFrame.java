package com.sistemacompras.ui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private final CardLayout cardLayout;
    private final JPanel contenedor;
    private MenuPrincipalPanel menuPanel;

    public MainFrame() {
        setTitle("Sistema de Gestión de Adquisiciones y Proveedores");
        setSize(1000, 680);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(900, 620));

        cardLayout = new CardLayout();
        contenedor = new JPanel(cardLayout);

        // Solo el LoginPanel se crea al inicio
        contenedor.add(new LoginPanel(this), "login");

        add(contenedor);
        cardLayout.show(contenedor, "login");
    }

    /**
     * Muestra un panel. Si es "menu", lo crea en ese momento
     * con la conexión activa en SesionActual.
     */
    public void mostrarPanel(String nombre) {
        if ("menu".equals(nombre)) {
            if (menuPanel != null) {
                contenedor.remove(menuPanel);
            }
            menuPanel = new MenuPrincipalPanel(SesionActual.getConexion(), this);
            contenedor.add(menuPanel, "menu");
            contenedor.revalidate();
            contenedor.repaint();
        }
        cardLayout.show(contenedor, nombre);
    }

    public void reiniciarMenu() {
        if (menuPanel != null) {
            contenedor.remove(menuPanel);
            menuPanel = null;
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}