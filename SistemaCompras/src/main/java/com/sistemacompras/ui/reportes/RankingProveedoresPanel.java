package com.sistemacompras.ui.reportes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.time.LocalDate;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;

import com.sistemacompras.datos.IConexionBD;
import com.sistemacompras.repositorios.reportes.RankingProveedoresRepositorio;
import com.sistemacompras.repositorios.reportes.ReporteExporter;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;

public class RankingProveedoresPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private final RankingProveedoresRepositorio repo;

    private JSpinner spnFechaInicio;
    private JSpinner spnFechaFin;
    private JPanel panelReporte;

    public RankingProveedoresPanel(IConexionBD conexion) {
        this.repo = new RankingProveedoresRepositorio(conexion);
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(248, 249, 250));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // ===== PANEL SUPERIOR =====
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new BoxLayout(panelSuperior, BoxLayout.Y_AXIS));
        panelSuperior.setBackground(new Color(248, 249, 250));

        JLabel lblTitulo = new JLabel("Top 5 Proveedores por Monto Adjudicado");
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

        barraControles.add(new JLabel("Fecha inicio:"));
        spnFechaInicio = crearSpinnerFecha(LocalDate.of(2026, 1, 1));
        barraControles.add(spnFechaInicio);

        barraControles.add(Box.createHorizontalStrut(15));

        barraControles.add(new JLabel("Fecha fin:"));
        spnFechaFin = crearSpinnerFecha(LocalDate.of(2026, 12, 31));
        barraControles.add(spnFechaFin);

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

        // ===== BOTONES DE EXPORTAR =====
        JButton btnPDF = new JButton("📄 PDF");
        btnPDF.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnPDF.setForeground(Color.WHITE);
        btnPDF.setBackground(new Color(220, 53, 69));
        btnPDF.setFocusPainted(false);
        btnPDF.setBorderPainted(false);
        btnPDF.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnPDF.setPreferredSize(new Dimension(100, 30));
        btnPDF.addActionListener(e -> exportarReporte("PDF"));
        barraControles.add(btnPDF);

        JButton btnExcel = new JButton("📊 Excel");
        btnExcel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnExcel.setForeground(Color.WHITE);
        btnExcel.setBackground(new Color(25, 135, 84));
        btnExcel.setFocusPainted(false);
        btnExcel.setBorderPainted(false);
        btnExcel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnExcel.setPreferredSize(new Dimension(100, 30));
        btnExcel.addActionListener(e -> exportarReporte("Excel"));
        barraControles.add(btnExcel);

        panelSuperior.add(lblTitulo);
        panelSuperior.add(Box.createVerticalStrut(10));
        panelSuperior.add(barraControles);

        add(panelSuperior, BorderLayout.NORTH);

        panelReporte = new JPanel(new BorderLayout());
        panelReporte.setBackground(Color.WHITE);
        panelReporte.setBorder(BorderFactory.createLineBorder(new Color(222, 226, 230)));

        JLabel lblMensaje = new JLabel(
            "Selecciona un rango de fechas y haz clic en 'Generar Reporte'",
            SwingConstants.CENTER);
        lblMensaje.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        lblMensaje.setForeground(new Color(108, 117, 125));
        panelReporte.add(lblMensaje, BorderLayout.CENTER);

        add(panelReporte, BorderLayout.CENTER);
    }

    private JSpinner crearSpinnerFecha(LocalDate fechaInicial) {
        SpinnerDateModel model = new SpinnerDateModel();
        JSpinner spinner = new JSpinner(model);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(spinner, "yyyy-MM-dd");
        spinner.setEditor(editor);
        java.sql.Date fechaSql = java.sql.Date.valueOf(fechaInicial);
        spinner.setValue(new java.util.Date(fechaSql.getTime()));
        spinner.setPreferredSize(new Dimension(130, 28));
        return spinner;
    }

    private void generarReporte() {
        try {
            java.util.Date fechaIniUtil = (java.util.Date) spnFechaInicio.getValue();
            java.util.Date fechaFinUtil = (java.util.Date) spnFechaFin.getValue();

            LocalDate fechaInicio = new java.sql.Date(fechaIniUtil.getTime()).toLocalDate();
            LocalDate fechaFin = new java.sql.Date(fechaFinUtil.getTime()).toLocalDate();

            if (fechaFin.isBefore(fechaInicio)) {
                JOptionPane.showMessageDialog(this,
                    "La fecha fin no puede ser anterior a la fecha inicio",
                    "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }

            JasperPrint jasperPrint = repo.generarReporte(fechaInicio, fechaFin);

            boolean hayDatos = !jasperPrint.getPages().isEmpty()
                && !jasperPrint.getPages().get(0).getElements().isEmpty();

            if (!hayDatos) {
                panelReporte.removeAll();
                JLabel lblVacio = new JLabel(
                    "No hay adjudicaciones en el rango " + fechaInicio + " al " + fechaFin,
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

    private void exportarReporte(String formato) {
        try {
            java.util.Date fechaIniUtil = (java.util.Date) spnFechaInicio.getValue();
            java.util.Date fechaFinUtil = (java.util.Date) spnFechaFin.getValue();
            LocalDate fechaInicio = new java.sql.Date(fechaIniUtil.getTime()).toLocalDate();
            LocalDate fechaFin = new java.sql.Date(fechaFinUtil.getTime()).toLocalDate();

            if (fechaFin.isBefore(fechaInicio)) {
                JOptionPane.showMessageDialog(this,
                    "La fecha fin no puede ser anterior a la fecha inicio",
                    "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }

            JasperPrint jasperPrint = repo.generarReporte(fechaInicio, fechaFin);

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guardar reporte como " + formato);
            String extension = formato.equals("PDF") ? ".pdf" : ".xlsx";
            fileChooser.setSelectedFile(new java.io.File("Reporte_Top5" + extension));

            int resultado = fileChooser.showSaveDialog(this);
            if (resultado != JFileChooser.APPROVE_OPTION) return;

            java.io.File archivo = fileChooser.getSelectedFile();

            if (formato.equals("PDF")) {
                ReporteExporter.exportarPDF(jasperPrint, archivo);
            } else {
                ReporteExporter.exportarExcel(jasperPrint, archivo);
            }

            JOptionPane.showMessageDialog(this,
                "Reporte exportado exitosamente a:\n" + archivo.getAbsolutePath(),
                "Éxito", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Error al exportar: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}