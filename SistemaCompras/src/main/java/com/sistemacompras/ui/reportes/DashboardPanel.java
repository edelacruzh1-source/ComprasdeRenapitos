package com.sistemacompras.ui.reportes;

import com.sistemacompras.datos.IConexionBD;
import com.sistemacompras.repositorios.reportes.DashboardRepositorio;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

public class DashboardPanel extends JPanel {

    private final DashboardRepositorio repo;
    private JPanel grid;

    public DashboardPanel(IConexionBD conexion) {
        this.repo = new DashboardRepositorio(conexion);
        initComponents();
        cargarGraficos();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(248, 249, 250));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // ===== PANEL SUPERIOR =====
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new BoxLayout(panelSuperior, BoxLayout.Y_AXIS));
        panelSuperior.setBackground(new Color(248, 249, 250));

        JLabel lblTitulo = new JLabel("Dashboard Gerencial");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
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

        JButton btnRefrescar = new JButton("🔄 Refrescar");
        btnRefrescar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnRefrescar.setForeground(Color.WHITE);
        btnRefrescar.setBackground(new Color(13, 110, 253));
        btnRefrescar.setFocusPainted(false);
        btnRefrescar.setBorderPainted(false);
        btnRefrescar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRefrescar.setPreferredSize(new Dimension(130, 30));
        btnRefrescar.addActionListener(e -> cargarGraficos());
        barraControles.add(btnRefrescar);

        JButton btnPNG = new JButton("📸 Guardar PNG");
        btnPNG.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnPNG.setForeground(Color.WHITE);
        btnPNG.setBackground(new Color(220, 53, 69));
        btnPNG.setFocusPainted(false);
        btnPNG.setBorderPainted(false);
        btnPNG.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnPNG.setPreferredSize(new Dimension(150, 30));
        btnPNG.addActionListener(e -> guardarComoPNG());
        barraControles.add(btnPNG);

        panelSuperior.add(lblTitulo);
        panelSuperior.add(Box.createVerticalStrut(10));
        panelSuperior.add(barraControles);

        add(panelSuperior, BorderLayout.NORTH);

        // ===== GRID DE GRÁFICOS =====
        grid = new JPanel(new GridLayout(3, 2, 10, 10));
        grid.setBackground(new Color(248, 249, 250));
        add(grid, BorderLayout.CENTER);
    }

    private void cargarGraficos() {
        try {
            grid.removeAll();

            // 1. Top 5 proveedores (barras)
            DefaultCategoryDataset dsTop = new DefaultCategoryDataset();
            for (Object[] fila : repo.obtenerTop5Proveedores()) {
                dsTop.addValue((Double) fila[1], "Monto", (String) fila[0]);
            }
            JFreeChart chartTop = ChartFactory.createBarChart(
                "Top 5 Proveedores", "Proveedor", "Monto (Q)",
                dsTop, PlotOrientation.VERTICAL, false, true, false);
            grid.add(new ChartPanel(chartTop));

            // 2. Gasto por departamento (pastel)
            DefaultPieDataset<String> dsGasto = new DefaultPieDataset<>();
            for (Object[] fila : repo.obtenerGastoDepartamental()) {
                dsGasto.setValue((String) fila[0], (Double) fila[1]);
            }
            JFreeChart chartGasto = ChartFactory.createPieChart(
                "Gasto por Departamento", dsGasto, true, true, false);
            grid.add(new ChartPanel(chartGasto));

            // 3. Eficiencia (líneas)
            DefaultCategoryDataset dsEf = new DefaultCategoryDataset();
            for (Object[] fila : repo.obtenerEficiencia()) {
                dsEf.addValue((Integer) fila[1], "Días", "Orden " + fila[0]);
            }
            JFreeChart chartEf = ChartFactory.createLineChart(
                "Eficiencia del Proceso", "Orden", "Días",
                dsEf, PlotOrientation.VERTICAL, false, true, false);
            grid.add(new ChartPanel(chartEf));

            // 4. Pedidos pendientes por sucursal (barras)
            DefaultCategoryDataset dsPend = new DefaultCategoryDataset();
            for (Object[] fila : repo.obtenerPedidosPendientesPorSucursal()) {
                dsPend.addValue((Integer) fila[1], "Pendientes", (String) fila[0]);
            }
            JFreeChart chartPend = ChartFactory.createBarChart(
                "Pedidos Pendientes por Sucursal", "Sucursal", "Pendientes",
                dsPend, PlotOrientation.VERTICAL, false, true, false);
            grid.add(new ChartPanel(chartPend));

            // 5. Órdenes activas vs adjudicadas (pastel)
            DefaultPieDataset<String> dsEst = new DefaultPieDataset<>();
            for (Object[] fila : repo.obtenerEstadoOrdenes()) {
                dsEst.setValue((String) fila[0], (Integer) fila[1]);
            }
            JFreeChart chartEst = ChartFactory.createPieChart(
                "Estado de Órdenes", dsEst, true, true, false);
            grid.add(new ChartPanel(chartEst));

            // 6. Espacio vacío
            grid.add(new JPanel());

            grid.revalidate();
            grid.repaint();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Error cargando dashboard: " + e.getMessage());
        }
    }

    private void guardarComoPNG() {
        try {
            // Pedir al usuario dónde guardar
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guardar dashboard como PNG");
            fileChooser.setSelectedFile(new File("Dashboard.png"));

            int resultado = fileChooser.showSaveDialog(this);
            if (resultado != JFileChooser.APPROVE_OPTION) return;

            File archivo = fileChooser.getSelectedFile();

            // Crear una imagen del panel
            BufferedImage imagen = new BufferedImage(
                grid.getWidth(), grid.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = imagen.createGraphics();
            grid.paint(g2);
            g2.dispose();

            // Guardar como PNG
            javax.imageio.ImageIO.write(imagen, "png", archivo);

            JOptionPane.showMessageDialog(this,
                "Dashboard guardado exitosamente en:\n" + archivo.getAbsolutePath(),
                "Éxito", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Error al guardar imagen: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}