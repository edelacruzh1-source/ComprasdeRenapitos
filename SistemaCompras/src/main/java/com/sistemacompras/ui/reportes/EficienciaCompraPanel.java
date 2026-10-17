package com.sistemacompras.ui.reportes;

import com.sistemacompras.datos.IConexionBD;
import com.sistemacompras.repositorios.reportes.EficienciaCompraRepositorio;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import java.awt.*;

public class EficienciaCompraPanel extends JPanel {

    private final EficienciaCompraRepositorio repo;
    private JPanel panelReporte;

    public EficienciaCompraPanel(IConexionBD conexion) {
        this.repo = new EficienciaCompraRepositorio(conexion);
        initComponents();
        generarReporteAutomatico();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(248, 249, 250));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // ===== PANEL SUPERIOR =====
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new BoxLayout(panelSuperior, BoxLayout.Y_AXIS));
        panelSuperior.setBackground(new Color(248, 249, 250));

        JLabel lblTitulo = new JLabel("Eficiencia del Proceso de Compra");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(33, 37, 41));
        lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Barra de controles
        JPanel barraControles = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        barraControles.setBackground(Color.WHITE);
        barraControles.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(222, 226, 230)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        barraControles.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblDesc = new JLabel(
            "Tiempo promedio desde la creación de la orden hasta su adjudicación");
        lblDesc.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblDesc.setForeground(new Color(108, 117, 125));
        barraControles.add(lblDesc);

        JButton btnRefrescar = new JButton("Refrescar");
        btnRefrescar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnRefrescar.setForeground(Color.WHITE);
        btnRefrescar.setBackground(new Color(13, 110, 253));
        btnRefrescar.setFocusPainted(false);
        btnRefrescar.setBorderPainted(false);
        btnRefrescar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRefrescar.setPreferredSize(new Dimension(120, 30));
        btnRefrescar.addActionListener(e -> generarReporte());
        barraControles.add(btnRefrescar);

        panelSuperior.add(lblTitulo);
        panelSuperior.add(Box.createVerticalStrut(10));
        panelSuperior.add(barraControles);

        add(panelSuperior, BorderLayout.NORTH);

        // ===== PANEL DEL REPORTE =====
        panelReporte = new JPanel(new BorderLayout());
        panelReporte.setBackground(Color.WHITE);
        panelReporte.setBorder(BorderFactory.createLineBorder(new Color(222, 226, 230)));

        JLabel lblMensaje = new JLabel("Generando reporte...", SwingConstants.CENTER);
        lblMensaje.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        lblMensaje.setForeground(new Color(108, 117, 125));
        panelReporte.add(lblMensaje, BorderLayout.CENTER);

        add(panelReporte, BorderLayout.CENTER);
    }

    private void generarReporteAutomatico() {
        // Generar el reporte al abrir el panel (no necesita parámetros)
        SwingUtilities.invokeLater(this::generarReporte);
    }

    private void generarReporte() {
        try {
            JasperPrint jasperPrint = repo.generarReporte();

            panelReporte.removeAll();
            JRViewer viewer = new JRViewer(jasperPrint);
            panelReporte.add(viewer, BorderLayout.CENTER);
            panelReporte.revalidate();
            panelReporte.repaint();

        } catch (Exception e) {
            e.printStackTrace();
            panelReporte.removeAll();
            JLabel lblError = new JLabel(
                "Error al generar reporte: " + e.getMessage(),
                SwingConstants.CENTER
            );
            lblError.setForeground(new Color(220, 53, 69));
            panelReporte.add(lblError, BorderLayout.CENTER);
            panelReporte.revalidate();
            panelReporte.repaint();
        }
    }
}
