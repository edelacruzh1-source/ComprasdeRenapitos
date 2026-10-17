package com.sistemacompras.ui.reportes;

import com.sistemacompras.datos.IConexionBD;
import com.sistemacompras.entidades.Sucursal;
import com.sistemacompras.repositorios.SucursalRepositorio;
import com.sistemacompras.repositorios.reportes.GastoDepartamentalRepositorio;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GastoDepartamentalPanel extends JPanel {

    private final GastoDepartamentalRepositorio repo;
    private final SucursalRepositorio sucursalRepo;

    private JComboBox<Sucursal> cboSucursal;
    private JComboBox<Integer> cboAnio;
    private JPanel panelReporte;

    public GastoDepartamentalPanel(IConexionBD conexion) {
        this.repo = new GastoDepartamentalRepositorio(conexion);
        this.sucursalRepo = new SucursalRepositorio(conexion);
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

        JLabel lblTitulo = new JLabel("Gasto por Departamento");
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

        barraControles.add(new JLabel("Sucursal:"));
        cboSucursal = new JComboBox<>();
        cboSucursal.setPreferredSize(new Dimension(300, 28));
        cboSucursal.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        barraControles.add(cboSucursal);

        barraControles.add(Box.createHorizontalStrut(20));
        barraControles.add(new JLabel("Año:"));
        cboAnio = new JComboBox<>();
        cboAnio.setPreferredSize(new Dimension(100, 28));
        cboAnio.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        barraControles.add(cboAnio);

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
            "Selecciona sucursal y año, luego haz clic en 'Generar Reporte'",
            SwingConstants.CENTER);
        lblMensaje.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        lblMensaje.setForeground(new Color(108, 117, 125));
        panelReporte.add(lblMensaje, BorderLayout.CENTER);

        add(panelReporte, BorderLayout.CENTER);
    }

    private void cargarCombos() {
        try {
            cboSucursal.removeAllItems();
            for (Sucursal s : sucursalRepo.obtenerTodos()) {
                cboSucursal.addItem(s);
            }

            cboAnio.removeAllItems();
            List<Integer> anios = repo.obtenerAniosConDatos();
            for (Integer a : anios) {
                cboAnio.addItem(a);
            }

            if (anios.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "No hay adjudicaciones registradas en la base de datos");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error cargando combos: " + e.getMessage());
        }
    }

    private void generarReporte() {
        Sucursal sucursal = (Sucursal) cboSucursal.getSelectedItem();
        Integer anio = (Integer) cboAnio.getSelectedItem();

        if (sucursal == null || anio == null) {
            JOptionPane.showMessageDialog(this, "Selecciona sucursal y año");
            return;
        }

        try {
            // ✅ CORREGIDO: sucursal.getSucursalID() en vez de sucursalID
            JasperPrint jasperPrint = repo.generarReporte(sucursal.getSucursalID(), anio);

            boolean hayDatos = !jasperPrint.getPages().isEmpty()
                && !jasperPrint.getPages().get(0).getElements().isEmpty();

            if (!hayDatos) {
                panelReporte.removeAll();
                JLabel lblVacio = new JLabel(
                    "No hay datos para " + sucursal.getCodigo() + " en el año " + anio,
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