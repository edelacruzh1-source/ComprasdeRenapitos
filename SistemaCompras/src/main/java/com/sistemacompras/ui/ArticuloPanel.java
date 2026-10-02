package com.sistemacompras.ui;

import com.sistemacompras.datos.IConexionBD;
import com.sistemacompras.entidades.Articulo;
import com.sistemacompras.repositorios.ArticuloRepositorio;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ArticuloPanel extends JPanel {

    private final ArticuloRepositorio repo;
    private JTable tabla;
    private DefaultTableModel modelo;
    private JTextField txtCodigo, txtNombre, txtDescripcion;
    private int idSeleccionado = 0;
    private final int pantallaID = Pantallas.ARTICULOS;

    public ArticuloPanel(IConexionBD conexion) {
        this.repo = new ArticuloRepositorio(conexion);
        initComponents();
        cargarTabla();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(248, 249, 250));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblTitulo = new JLabel("Gestión de Artículos");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        add(lblTitulo, BorderLayout.NORTH);

        modelo = new DefaultTableModel(
            new Object[]{"ID", "Código", "Nombre", "Descripción"}, 0) {
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
            BorderFactory.createTitledBorder("Datos del Artículo"),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        form.add(new JLabel("Código:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        txtCodigo = new JTextField(15);
        form.add(txtCodigo, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        form.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        txtNombre = new JTextField(30);
        form.add(txtNombre, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        form.add(new JLabel("Descripción:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        txtDescripcion = new JTextField(30);
        form.add(txtDescripcion, gbc);

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
        txtCodigo.setEnabled(puedeEditar);
        txtNombre.setEnabled(puedeEditar);
        txtDescripcion.setEnabled(puedeEditar);

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
            for (Articulo a : repo.obtenerTodos()) {
                modelo.addRow(new Object[]{
                    a.getArticuloID(), a.getCodigo(),
                    a.getNombre(), a.getDescripcion()
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
        txtCodigo.setText((String) modelo.getValueAt(fila, 1));
        txtNombre.setText((String) modelo.getValueAt(fila, 2));
        txtDescripcion.setText((String) modelo.getValueAt(fila, 3));
    }

    private void guardar() {
        if (!SesionActual.puedeCrear(pantallaID) && !SesionActual.puedeActualizar(pantallaID)) {
            JOptionPane.showMessageDialog(this, "No tienes permiso");
            return;
        }
        try {
            String codigo = txtCodigo.getText().trim();
            String nombre = txtNombre.getText().trim();
            String desc = txtDescripcion.getText().trim();
            if (codigo.isEmpty() || nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Código y nombre obligatorios");
                return;
            }
            Articulo a = new Articulo(idSeleccionado, codigo, nombre, desc);
            if (idSeleccionado == 0) {
                repo.insertar(a);
                JOptionPane.showMessageDialog(this, "Artículo creado");
            } else {
                repo.actualizar(a);
                JOptionPane.showMessageDialog(this, "Artículo actualizado");
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
            JOptionPane.showMessageDialog(this, "Artículo eliminado");
            limpiar();
            cargarTabla();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void limpiar() {
        idSeleccionado = 0;
        txtCodigo.setText("");
        txtNombre.setText("");
        txtDescripcion.setText("");
        tabla.clearSelection();
    }
}