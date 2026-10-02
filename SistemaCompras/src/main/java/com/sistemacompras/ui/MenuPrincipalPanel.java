package com.sistemacompras.ui;

import com.sistemacompras.datos.IConexionBD;
import com.sistemacompras.entidades.Usuario;

import javax.swing.*;
import java.awt.*;

public class MenuPrincipalPanel extends JPanel {

    private final IConexionBD conexion;
    private final MainFrame frame;
    private final JPanel contenido;

    public MenuPrincipalPanel(IConexionBD conexion, MainFrame frame) {
        this.conexion = conexion;
        this.frame = frame;
        this.contenido = new JPanel(new BorderLayout());
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Barra superior
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(33, 37, 41));
        topBar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel lblTitulo = new JLabel("Sistema de Gestión de Adquisiciones");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));

        JLabel lblUsuario = new JLabel();
        lblUsuario.setForeground(Color.WHITE);
        lblUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        topBar.add(lblTitulo, BorderLayout.WEST);
        topBar.add(lblUsuario, BorderLayout.EAST);

        // Menú lateral
        JPanel menuLateral = new JPanel();
        menuLateral.setLayout(new BoxLayout(menuLateral, BoxLayout.Y_AXIS));
        menuLateral.setBackground(new Color(52, 58, 64));
        menuLateral.setPreferredSize(new Dimension(200, 0));
        menuLateral.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        // ===== Botones según permisos =====
        agregarBotonMenu(menuLateral, "Inicio", () -> mostrarInicio());

        if (SesionActual.puedeLeer(Pantallas.SUCURSALES)) {
            agregarBotonMenu(menuLateral, "Sucursales", () -> abrirPanel("sucursales"));
        }
        if (SesionActual.puedeLeer(Pantallas.DEPARTAMENTOS)) {
            agregarBotonMenu(menuLateral, "Departamentos", () -> abrirPanel("departamentos"));
        }
        if (SesionActual.puedeLeer(Pantallas.ARTICULOS)) {
            agregarBotonMenu(menuLateral, "Artículos", () -> abrirPanel("articulos"));
        }
        if (SesionActual.puedeLeer(Pantallas.PROVEEDORES)) {
            agregarBotonMenu(menuLateral, "Proveedores", () -> abrirPanel("proveedores"));
        }
        if (SesionActual.puedeLeer(Pantallas.PEDIDOS)) {
            agregarBotonMenu(menuLateral, "Pedidos Internos", () -> abrirPanel("pedidos"));
        }
        if (SesionActual.puedeLeer(Pantallas.ORDENES)) {
            agregarBotonMenu(menuLateral, "Órdenes de Compra", () -> abrirPanel("ordenes"));
        }
        if (SesionActual.puedeLeer(Pantallas.ADJUDICACIONES)) {
            agregarBotonMenu(menuLateral, "Adjudicaciones", () -> abrirPanel("adjudicaciones"));
        }
        if (SesionActual.puedeLeer(Pantallas.USUARIOS)) {
            agregarBotonMenu(menuLateral, "Usuarios", () -> abrirPanel("usuarios"));
        }

        menuLateral.add(Box.createVerticalGlue());
        agregarBotonMenu(menuLateral, "Cerrar Sesión", this::cerrarSesion);

        // Contenido central
        contenido.setBackground(new Color(248, 249, 250));
        mostrarInicio();

        add(topBar, BorderLayout.NORTH);
        add(menuLateral, BorderLayout.WEST);
        add(contenido, BorderLayout.CENTER);

        Usuario u = SesionActual.getUsuario();
        if (u != null) {
            lblUsuario.setText(u.getNombreUsuario() + "  |  " + u.getNombreRol());
        }
    }

    private void agregarBotonMenu(JPanel panel, String texto, Runnable accion) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        boton.setForeground(Color.WHITE);
        boton.setBackground(new Color(52, 58, 64));
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setHorizontalAlignment(SwingConstants.LEFT);
        boton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        // Hover
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                boton.setBackground(new Color(73, 80, 87));
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                boton.setBackground(new Color(52, 58, 64));
            }
        });

        boton.addActionListener(e -> accion.run());
        panel.add(boton);
        panel.add(Box.createVerticalStrut(5));
    }

    private void mostrarInicio() {
        contenido.removeAll();

        JPanel inicio = new JPanel(new GridBagLayout());
        inicio.setBackground(new Color(248, 249, 250));

        JPanel tarjeta = new JPanel();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(222, 226, 230)),
            BorderFactory.createEmptyBorder(40, 60, 40, 60)
        ));

        Usuario u = SesionActual.getUsuario();
        JLabel lblBienvenida = new JLabel("Bienvenido, " + (u != null ? u.getNombreUsuario() : ""));
        lblBienvenida.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblBienvenida.setForeground(new Color(33, 37, 41));
        lblBienvenida.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblRol = new JLabel("Rol: " + (u != null ? u.getNombreRol() : ""));
        lblRol.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblRol.setForeground(new Color(108, 117, 125));
        lblRol.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblInstruccion = new JLabel(
            "<html><center>Selecciona una opción del menú lateral<br>" +
            "para comenzar a trabajar.</center></html>");
        lblInstruccion.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblInstruccion.setForeground(new Color(108, 117, 125));
        lblInstruccion.setAlignmentX(Component.CENTER_ALIGNMENT);

        tarjeta.add(lblBienvenida);
        tarjeta.add(Box.createVerticalStrut(10));
        tarjeta.add(lblRol);
        tarjeta.add(Box.createVerticalStrut(30));
        tarjeta.add(lblInstruccion);

        inicio.add(tarjeta);
        contenido.add(inicio, BorderLayout.CENTER);
        contenido.revalidate();
        contenido.repaint();
    }

    private void abrirPanel(String nombre) {
        contenido.removeAll();

        JPanel panel = switch (nombre) {
            case "sucursales" -> new SucursalPanel(conexion);
            case "departamentos" -> new DepartamentoPanel(conexion);
            case "articulos" -> new ArticuloPanel(conexion);
            case "proveedores" -> new ProveedorPanel(conexion);
            case "pedidos" -> new PedidoInternoPanel(conexion);
            case "ordenes" -> new OrdenCompraPanel(conexion);
            case "adjudicaciones" -> new AdjudicacionPanel(conexion);
            case "usuarios" -> new UsuarioPanel(conexion);
            default -> new JPanel();
        };

        contenido.add(panel, BorderLayout.CENTER);
        contenido.revalidate();
        contenido.repaint();
    }

    private void cerrarSesion() {
        int confirm = JOptionPane.showConfirmDialog(this,
            "¿Cerrar sesión?", "Confirmar",
            JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            SesionActual.cerrarSesion();
            frame.mostrarPanel("login");
        }
    }
}