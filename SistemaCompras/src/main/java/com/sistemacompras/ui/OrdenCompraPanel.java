package com.sistemacompras.ui;

import com.sistemacompras.datos.IConexionBD;
import com.sistemacompras.entidades.OrdenCompra;
import com.sistemacompras.repositorios.OrdenCompraRepositorio;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class OrdenCompraPanel extends JPanel {

    private final OrdenCompraRepositorio repo;
    private JTable tabla;
    private DefaultTableModel modelo;
    private JTextField txtDescripcion, txtFechaCreacion, txtFechaLimite;
    private JComboBox<String> cboTipo, cboSubtipo;
    private int idSeleccionado = 0;
    private final int pantallaID = Pantallas.ORDENES;

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public OrdenCompraPanel(IConexionBD conexion) {
        this.repo = new OrdenCompraRepositorio(conexion);
        initComponents();
        cargarTabla();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(248, 249, 250));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblTitulo = new JLabel("Gestión de Órdenes de Compra");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        add(lblTitulo, BorderLayout.NORTH);

        modelo = new DefaultTableModel(
            new Object[]{"ID", "Descripción", "F. Creación", "F. Límite", "Tipo", "Subtipo"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla = new JTable(modelo);
        tabla.setRowHeight(28);
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) seleccionarFila();
        });

        JScrollPane scroll = new JScrollPane(tabla);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Datos de la Orden"),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        form.add(new JLabel("Descripción:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1; gbc.gridwidth = 3;
        txtDescripcion = new JTextField(30);
        form.add(txtDescripcion, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0; gbc.gridwidth = 1;
        form.add(new JLabel("F. Creación:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        txtFechaCreacion = new JTextField(12);
        txtFechaCreacion.setText(LocalDate.now().format(FMT));
        form.add(txtFechaCreacion, gbc);

        gbc.gridx = 2; gbc.weightx = 0;
        form.add(new JLabel("F. Límite:"), gbc);
        gbc.gridx = 3; gbc.weightx = 1;
        txtFechaLimite = new JTextField(12);
        txtFechaLimite.setText(LocalDate.now().plusDays(15).format(FMT));
        form.add(txtFechaLimite, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        form.add(new JLabel("Tipo:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        cboTipo = new JComboBox<>(new String[]{"Grande", "Chica"});
        cboTipo.addActionListener(e -> actualizarSubtipo());
        form.add(cboTipo, gbc);

        gbc.gridx = 2; gbc.weightx = 0;
        form.add(new JLabel("Subtipo:"), gbc);
        gbc.gridx = 3; gbc.weightx = 1;
        cboSubtipo = new JComboBox<>(new String[]{"Urgente", "Normal"});
        form.add(cboSubtipo, gbc);

        actualizarSubtipo();

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        botones.setBackground(Color.WHITE);

        JButton btnNuevo = crearBoton("Nuevo", new Color(108, 117, 125));
        JButton btnGuardar = crearBoton("Guardar", new Color(25, 135, 84));
        JButton btnEliminar = crearBoton("Eliminar", new Color(220, 53, 69));

        btnNuevo.addActionListener(e -> limpiar());
        btnGuardar.addActionListener(e -> guardar());
        btnEliminar.addActionListener(e -> eliminar());

        if (SesionActual.puedeCrear(pantallaID) || SesionActual.puedeActualizar(pantallaID)) {
            botones.add(btnNuevo);
            botones.add(btnGuardar);
        }
        if (SesionActual.puedeBorrar(pantallaID)) {
            botones.add(btnEliminar);
        }

        boolean puedeEditar = SesionActual.puedeCrear(pantallaID) 
                           || SesionActual.puedeActualizar(pantallaID);
        txtDescripcion.setEnabled(puedeEditar);
        txtFechaCreacion.setEnabled(puedeEditar);
        txtFechaLimite.setEnabled(puedeEditar);
        cboTipo.setEnabled(puedeEditar);
        cboSubtipo.setEnabled(puedeEditar);

        JPanel inferior = new JPanel(new BorderLayout(10, 10));
        inferior.setBackground(new Color(248, 249, 250));
        inferior.add(form, BorderLayout.CENTER);
        inferior.add(botones, BorderLayout.SOUTH);

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scroll, inferior);
        split.setResizeWeight(0.6);
        split.setBorder(null);
        add(split, BorderLayout.CENTER);
    }

    private JButton crearBoton(String texto, Color color) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setForeground(Color.WHITE);
        btn.setBackground(color);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(110, 35));
        return btn;
    }

    private void actualizarSubtipo() {
        boolean esGrande = "Grande".equals(cboTipo.getSelectedItem());
        cboSubtipo.setEnabled(!esGrande);
    }

    private void cargarTabla() {
        try {
            modelo.setRowCount(0);
            for (OrdenCompra o : repo.obtenerTodos()) {
                modelo.addRow(new Object[]{
                    o.getOrdenID(), o.getDescripcion(),
                    o.getFechaCreacion().format(FMT),
                    o.getFechaLimite().format(FMT),
                    o.getTipoOrden(),
                    o.getSubtipoOrden() == null ? "-" : o.getSubtipoOrden()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void seleccionarFila() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) return;
        try {
            idSeleccionado = (int) modelo.getValueAt(fila, 0);
            OrdenCompra o = repo.obtenerPorId(idSeleccionado);
            if (o == null) return;

            txtDescripcion.setText(o.getDescripcion());
            txtFechaCreacion.setText(o.getFechaCreacion().format(FMT));
            txtFechaLimite.setText(o.getFechaLimite().format(FMT));
            cboTipo.setSelectedItem(o.getTipoOrden());
            if (o.getSubtipoOrden() != null) cboSubtipo.setSelectedItem(o.getSubtipoOrden());
            actualizarSubtipo();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void guardar() {
        if (!SesionActual.puedeCrear(pantallaID) && !SesionActual.puedeActualizar(pantallaID)) {
            JOptionPane.showMessageDialog(this, "Sin permiso");
            return;
        }
        try {
            String desc = txtDescripcion.getText().trim();
            LocalDate fCre = LocalDate.parse(txtFechaCreacion.getText().trim(), FMT);
            LocalDate fLim = LocalDate.parse(txtFechaLimite.getText().trim(), FMT);

            if (fLim.isBefore(fCre)) {
                JOptionPane.showMessageDialog(this, "F. límite no puede ser anterior a F. creación");
                return;
            }

            String tipo = (String) cboTipo.getSelectedItem();
            String subtipo = "Grande".equals(tipo) ? null : (String) cboSubtipo.getSelectedItem();

            OrdenCompra o = new OrdenCompra(idSeleccionado, desc, fCre, fLim, tipo, subtipo);

            if (idSeleccionado == 0) {
                repo.insertar(o);
                JOptionPane.showMessageDialog(this, "Orden creada");
            } else {
                repo.actualizar(o);
                JOptionPane.showMessageDialog(this, "Orden actualizada");
            }
            limpiar();
            cargarTabla();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void eliminar() {
        if (!SesionActual.puedeBorrar(pantallaID)) {
            JOptionPane.showMessageDialog(this, "Sin permiso para eliminar");
            return;
        }
        if (idSeleccionado == 0) return;
        if (JOptionPane.showConfirmDialog(this, "¿Eliminar?", "Confirmar",
            JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) return;
        try {
            repo.eliminar(idSeleccionado);
            JOptionPane.showMessageDialog(this, "Orden eliminada");
            limpiar();
            cargarTabla();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void limpiar() {
        idSeleccionado = 0;
        txtDescripcion.setText("");
        txtFechaCreacion.setText(LocalDate.now().format(FMT));
        txtFechaLimite.setText(LocalDate.now().plusDays(15).format(FMT));
        cboTipo.setSelectedIndex(0);
        actualizarSubtipo();
        tabla.clearSelection();
    }
}