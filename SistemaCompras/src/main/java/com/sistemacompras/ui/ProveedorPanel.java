package com.sistemacompras.ui;

import com.sistemacompras.datos.IConexionBD;
import com.sistemacompras.entidades.Proveedor;
import com.sistemacompras.repositorios.ProveedorRepositorio;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ProveedorPanel extends JPanel {

    private final ProveedorRepositorio repo;
    private JTable tabla;
    private DefaultTableModel modelo;
    private JTextField txtNit, txtNombre, txtDireccion, txtTelefono;
    private int idSeleccionado = 0;

    public ProveedorPanel(IConexionBD conexion) {
        this.repo = new ProveedorRepositorio(conexion);
        initComponents();
        cargarTabla();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(248, 249, 250));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblTitulo = new JLabel("Gestión de Proveedores");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(33, 37, 41));
        add(lblTitulo, BorderLayout.NORTH);

        modelo = new DefaultTableModel(
            new Object[]{"ID", "NIT", "Nombre Comercial", "Teléfono", "Dirección"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla = new JTable(modelo);
        tabla.setRowHeight(28);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabla.getTableHeader().setBackground(new Color(233, 236, 239));
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) seleccionarFila();
        });

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(222, 226, 230)));

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(222, 226, 230)),
                "Datos del Proveedor"),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        form.add(new JLabel("NIT:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        txtNit = new JTextField(15);
        form.add(txtNit, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        form.add(new JLabel("Nombre Comercial:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1; gbc.gridwidth = 3;
        txtNombre = new JTextField(30);
        form.add(txtNombre, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0; gbc.gridwidth = 1;
        form.add(new JLabel("Dirección:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1; gbc.gridwidth = 3;
        txtDireccion = new JTextField(30);
        form.add(txtDireccion, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0; gbc.gridwidth = 1;
        form.add(new JLabel("Teléfono:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        txtTelefono = new JTextField(15);
        form.add(txtTelefono, gbc);

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        botones.setBackground(Color.WHITE);

        JButton btnNuevo = crearBoton("Nuevo", new Color(108, 117, 125));
        JButton btnGuardar = crearBoton("Guardar", new Color(25, 135, 84));
        JButton btnEliminar = crearBoton("Eliminar", new Color(220, 53, 69));

        btnNuevo.addActionListener(e -> limpiar());
        btnGuardar.addActionListener(e -> guardar());
        btnEliminar.addActionListener(e -> eliminar());

        botones.add(btnNuevo);
        botones.add(btnGuardar);
        botones.add(btnEliminar);

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

    private void cargarTabla() {
        try {
            modelo.setRowCount(0);
            for (Proveedor p : repo.obtenerTodos()) {
                modelo.addRow(new Object[]{
                    p.getProveedorID(), p.getNit(), p.getNombreComercial(),
                    p.getTelefono(), p.getDireccion()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void seleccionarFila() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) return;
        idSeleccionado = (int) modelo.getValueAt(fila, 0);
        txtNit.setText((String) modelo.getValueAt(fila, 1));
        txtNombre.setText((String) modelo.getValueAt(fila, 2));
        txtTelefono.setText((String) modelo.getValueAt(fila, 3));
        txtDireccion.setText((String) modelo.getValueAt(fila, 4));
    }

    private void guardar() {
        try {
            String nit = txtNit.getText().trim();
            String nombre = txtNombre.getText().trim();
            String dir = txtDireccion.getText().trim();
            String tel = txtTelefono.getText().trim();

            if (nit.isEmpty() || nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "NIT y nombre son obligatorios",
                    "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Proveedor p = new Proveedor(idSeleccionado, nit, nombre, dir, tel);

            if (idSeleccionado == 0) {
                repo.insertar(p);
                JOptionPane.showMessageDialog(this, "Proveedor creado");
            } else {
                repo.actualizar(p);
                JOptionPane.showMessageDialog(this, "Proveedor actualizado");
            }

            limpiar();
            cargarTabla();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminar() {
        if (idSeleccionado == 0) {
            JOptionPane.showMessageDialog(this, "Selecciona un proveedor");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this,
            "¿Eliminar el proveedor seleccionado?", "Confirmar",
            JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            repo.eliminar(idSeleccionado);
            JOptionPane.showMessageDialog(this, "Proveedor eliminado");
            limpiar();
            cargarTabla();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void limpiar() {
        idSeleccionado = 0;
        txtNit.setText("");
        txtNombre.setText("");
        txtDireccion.setText("");
        txtTelefono.setText("");
        tabla.clearSelection();
        txtNit.requestFocus();
    }
}