package com.sistemacompras.ui.reportes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.sistemacompras.datos.IConexionBD;
import com.sistemacompras.entidades.Articulo;
import com.sistemacompras.entidades.Proveedor;
import com.sistemacompras.repositorios.ArticuloRepositorio;
import com.sistemacompras.repositorios.ProveedorRepositorio;
import com.sistemacompras.repositorios.reportes.VariacionPreciosRepositorio;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;

public class VariacionPreciosPanel extends JPanel {

	private static final long serialVersionUID = 1L;
    private final VariacionPreciosRepositorio repo;
    private final ArticuloRepositorio articuloRepo;
    private final ProveedorRepositorio proveedorRepo;

    private JComboBox<Articulo> cboArticulo;
    private JComboBox<Proveedor> cboProveedor;
    private JPanel panelReporte;

    public VariacionPreciosPanel(IConexionBD conexion) {
        this.repo = new VariacionPreciosRepositorio(conexion);
        this.articuloRepo = new ArticuloRepositorio(conexion);
        this.proveedorRepo = new ProveedorRepositorio(conexion);
        initComponents();
        cargarCombos();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(248, 249, 250));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // ===== PANEL SUPERIOR =====
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new BoxLayout(panelSuperior, BoxLayout.Y_AXIS));
        panelSuperior.setBackground(new Color(248, 249, 250));

        JLabel lblTitulo = new JLabel("Variación de Precios");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(33, 37, 41));
        lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel barraControles = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        barraControles.setBackground(Color.WHITE);
        barraControles.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(222, 226, 230)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        barraControles.setAlignmentX(Component.LEFT_ALIGNMENT);

        barraControles.add(new JLabel("Artículo:"));
        cboArticulo = new JComboBox<>();
        cboArticulo.setPreferredSize(new Dimension(280, 28));
        cboArticulo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        barraControles.add(cboArticulo);

        barraControles.add(Box.createHorizontalStrut(15));

        barraControles.add(new JLabel("Proveedor:"));
        cboProveedor = new JComboBox<>();
        cboProveedor.setPreferredSize(new Dimension(250, 28));
        cboProveedor.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        barraControles.add(cboProveedor);

        barraControles.add(Box.createHorizontalStrut(15));

        JButton btnGenerar = new JButton("Generar Reporte");
        btnGenerar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnGenerar.setForeground(Color.WHITE);
        btnGenerar.setBackground(new Color(25, 135, 84));
        btnGenerar.setFocusPainted(false);
        btnGenerar.setBorderPainted(false);
        btnGenerar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnGenerar.setPreferredSize(new Dimension(150, 30));
        btnGenerar.addActionListener(e -> generarReporte());
        barraControles.add(btnGenerar);

        panelSuperior.add(lblTitulo);
        panelSuperior.add(Box.createVerticalStrut(10));
        panelSuperior.add(barraControles);

        add(panelSuperior, BorderLayout.NORTH);

        // ===== PANEL DEL REPORTE =====
        panelReporte = new JPanel(new BorderLayout());
        panelReporte.setBackground(Color.WHITE);
        panelReporte.setBorder(BorderFactory.createLineBorder(new Color(222, 226, 230)));

        JLabel lblMensaje = new JLabel(
            "Selecciona un artículo y un proveedor, luego haz clic en 'Generar Reporte'",
            SwingConstants.CENTER);
        lblMensaje.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        lblMensaje.setForeground(new Color(108, 117, 125));
        panelReporte.add(lblMensaje, BorderLayout.CENTER);

        add(panelReporte, BorderLayout.CENTER);
    }

    private void cargarCombos() {
        try {
            cboArticulo.removeAllItems();
            List<Articulo> articulos = articuloRepo.obtenerTodos();
            for (Articulo a : articulos) {
                cboArticulo.addItem(a);
            }

            cboProveedor.removeAllItems();
            List<Proveedor> proveedores = proveedorRepo.obtenerTodos();
            for (Proveedor p : proveedores) {
                cboProveedor.addItem(p);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error cargando combos: " + e.getMessage());
        }
    }

    private void generarReporte() {
        Articulo articulo = (Articulo) cboArticulo.getSelectedItem();
        Proveedor proveedor = (Proveedor) cboProveedor.getSelectedItem();

        if (articulo == null || proveedor == null) {
            JOptionPane.showMessageDialog(this,
                "Selecciona un artículo y un proveedor");
            return;
        }

        try {
            JasperPrint jasperPrint = repo.generarReporte(
                articulo.getArticuloID(),
                proveedor.getProveedorID()
            );

            boolean hayDatos = !jasperPrint.getPages().isEmpty()
                && !jasperPrint.getPages().get(0).getElements().isEmpty();

            if (!hayDatos) {
                panelReporte.removeAll();
                JLabel lblVacio = new JLabel(
                    "<html><center>No hay ofertas registradas para:<br>" +
                    articulo.getNombre() + " - " + proveedor.getNombreComercial() +
                    "</center></html>",
                    SwingConstants.CENTER);
                lblVacio.setFont(new Font("Segoe UI", Font.ITALIC, 14));
                lblVacio.setForeground(new Color(108, 117, 125));
                panelReporte.add(lblVacio, BorderLayout.CENTER);
                panelReporte.revalidate();
                panelReporte.repaint();
                return;
            }

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
