package com.sistemacompras.ui.reportes;

import com.sistemacompras.datos.IConexionBD;
import com.sistemacompras.entidades.OrdenCompra;
import com.sistemacompras.repositorios.OrdenCompraRepositorio;
import com.sistemacompras.repositorios.reportes.AnalisisOfertasRepositorio;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AnalisisOfertasPanel extends JPanel {

    private final IConexionBD conexion;
    private final AnalisisOfertasRepositorio repo;
    private final OrdenCompraRepositorio ordenRepo;

    private JComboBox<OrdenCompra> cboOrden;
    private JPanel panelReporte;

    public AnalisisOfertasPanel(IConexionBD conexion) {
        this.conexion = conexion;
        this.repo = new AnalisisOfertasRepositorio(conexion);
        this.ordenRepo = new OrdenCompraRepositorio(conexion);
        initComponents();
        cargarOrdenes();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(248, 249, 250));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // ===== PANEL SUPERIOR (título + barra de controles) =====
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new BoxLayout(panelSuperior, BoxLayout.Y_AXIS));
        panelSuperior.setBackground(new Color(248, 249, 250));

        // Título
        JLabel lblTitulo = new JLabel("Análisis de Ofertas por Orden");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(33, 37, 41));
        lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Barra de controles (combo + botón)
        JPanel barraControles = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        barraControles.setBackground(Color.WHITE);
        barraControles.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(222, 226, 230)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        barraControles.setAlignmentX(Component.LEFT_ALIGNMENT);

        barraControles.add(new JLabel("Orden:"));
        cboOrden = new JComboBox<>();
        cboOrden.setPreferredSize(new Dimension(400, 28));
        cboOrden.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        barraControles.add(cboOrden);

        JButton btnGenerar = new JButton("Generar Reporte");
        btnGenerar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnGenerar.setForeground(Color.WHITE);
        btnGenerar.setBackground(new Color(25, 135, 84));
        btnGenerar.setFocusPainted(false);
        btnGenerar.setBorderPainted(false);
        btnGenerar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnGenerar.setPreferredSize(new Dimension(150, 30));
        btnGenerar.addActionListener(e -> generarReporte());
        barraControles.add(btnGenerar);

        // Armar el panel superior
        panelSuperior.add(lblTitulo);
        panelSuperior.add(Box.createVerticalStrut(10));
        panelSuperior.add(barraControles);

        add(panelSuperior, BorderLayout.NORTH);

        // ===== PANEL DEL REPORTE =====
        panelReporte = new JPanel(new BorderLayout());
        panelReporte.setBackground(Color.WHITE);
        panelReporte.setBorder(BorderFactory.createLineBorder(new Color(222, 226, 230)));

        // Mensaje inicial
        JLabel lblMensaje = new JLabel(
            "Selecciona una orden y haz clic en 'Generar Reporte'",
            SwingConstants.CENTER
        );
        lblMensaje.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        lblMensaje.setForeground(new Color(108, 117, 125));
        panelReporte.add(lblMensaje, BorderLayout.CENTER);

        add(panelReporte, BorderLayout.CENTER);
    }

    private void cargarOrdenes() {
        try {
            cboOrden.removeAllItems();
            List<OrdenCompra> ordenes = ordenRepo.obtenerTodos();
            for (OrdenCompra o : ordenes) {
                cboOrden.addItem(o);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error cargando órdenes: " + e.getMessage());
        }
    }

    private void generarReporte() {
        OrdenCompra orden = (OrdenCompra) cboOrden.getSelectedItem();
        if (orden == null) {
            JOptionPane.showMessageDialog(this, "Selecciona una orden");
            return;
        }

        try {
            JasperPrint jasperPrint = repo.generarReporte(orden.getOrdenID());

            panelReporte.removeAll();

            JRViewer viewer = new JRViewer(jasperPrint);
            panelReporte.add(viewer, BorderLayout.CENTER);
            panelReporte.revalidate();
            panelReporte.repaint();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Error al generar reporte: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}