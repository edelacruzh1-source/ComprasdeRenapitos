package com.sistemacompras.ui.reportes;

import com.sistemacompras.datos.IConexionBD;
import com.sistemacompras.entidades.OrdenCompra;
import com.sistemacompras.repositorios.OrdenCompraRepositorio;
import com.sistemacompras.repositorios.reportes.AnalisisOfertasRepositorio;
import com.sistemacompras.repositorios.reportes.ReporteExporter;

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
        
     // Botón Exportar PDF
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

        // Botón Exportar Excel
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

        // Armar el panel superior
        panelSuperior.add(lblTitulo);
        panelSuperior.add(Box.createVerticalStrut(10));
        panelSuperior.add(barraControles);


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

        
        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panelSuperior, panelReporte);
        split.setResizeWeight(0.2); // 20% para la barra superior, 80% para el reporte
        split.setBorder(null);
        add(split, BorderLayout.CENTER);
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
    
    private void exportarReporte(String formato) {
        try {
            // Obtener la orden seleccionada del combo
            com.sistemacompras.entidades.OrdenCompra orden =
                (com.sistemacompras.entidades.OrdenCompra) cboOrden.getSelectedItem();

            if (orden == null) {
                JOptionPane.showMessageDialog(this, "Selecciona una orden");
                return;
            }

            // Generar el JasperPrint con el OrdenID
            JasperPrint jasperPrint = repo.generarReporte(orden.getOrdenID());

            // Pedir al usuario dónde guardar
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guardar reporte como " + formato);
            String extension = formato.equals("PDF") ? ".pdf" : ".xlsx";
            fileChooser.setSelectedFile(new java.io.File("Reporte_Ofertas" + extension));

            int resultado = fileChooser.showSaveDialog(this);
            if (resultado != JFileChooser.APPROVE_OPTION) return;

            java.io.File archivo = fileChooser.getSelectedFile();

            // Exportar según el formato
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