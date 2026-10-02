package com.sistemacompras.ui;

import com.sistemacompras.datos.IConexionBD;
import com.sistemacompras.entidades.Sucursal;
import com.sistemacompras.repositorios.SucursalRepositorio;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class SucursalPanel extends JPanel {

    private final SucursalRepositorio repo;
    private JTable tabla;
    private DefaultTableModel modelo;
    private JTextField txtCodigo, txtDireccion, txtCiudad, txtDepartamento;
    private int idSeleccionado = 0;
    private final int pantallaID = Pantallas.SUCURSALES;

    public SucursalPanel(IConexionBD conexion) {
        this.repo = new SucursalRepositorio(conexion);
        initComponents();
        cargarTabla();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(248, 249, 250));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblTitulo = new JLabel("Gestión de Sucursales");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(33, 37, 41));
        add(lblTitulo, BorderLayout.NORTH);

        // Tabla
        modelo = new DefaultTableModel(
            new Object[]{"ID", "Código", "Dirección", "Ciudad", "Departamento"}, 0) {
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

        // Formulario
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(222, 226, 230)),
                "Datos de la Sucursal"),
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
        form.add(new JLabel("Dirección:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1; gbc.gridwidth = 3;
        txtDireccion = new JTextField(30);
        form.add(txtDireccion, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0; gbc.gridwidth = 1;
        form.add(new JLabel("Ciudad:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        txtCiudad = new JTextField(15);
        form.add(txtCiudad, gbc);

        gbc.gridx = 2; gbc.weightx = 0;
        form.add(new JLabel("Departamento:"), gbc);
        gbc.gridx = 3; gbc.weightx = 1;
        txtDepartamento = new JTextField(15);
        form.add(txtDepartamento, gbc);

        // Botones con permisos
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

        // Deshabilitar campos si no puede editar
        boolean puedeEditar = SesionActual.puedeCrear(pantallaID) 
                           || SesionActual.puedeActualizar(pantallaID);
        txtCodigo.setEnabled(puedeEditar);
        txtDireccion.setEnabled(puedeEditar);
        txtCiudad.setEnabled(puedeEditar);
        txtDepartamento.setEnabled(puedeEditar);

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
            for (Sucursal s : repo.obtenerTodos()) {
                modelo.addRow(new Object[]{
                    s.getSucursalID(), s.getCodigo(), s.getDireccion(),
                    s.getCiudad(), s.getDepartamento()
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
        txtDireccion.setText((String) modelo.getValueAt(fila, 2));
        txtCiudad.setText((String) modelo.getValueAt(fila, 3));
        txtDepartamento.setText((String) modelo.getValueAt(fila, 4));
    }

    private void guardar() {
        if (!SesionActual.puedeCrear(pantallaID) && !SesionActual.puedeActualizar(pantallaID)) {
            JOptionPane.showMessageDialog(this, "No tienes permiso para esta acción");
            return;
        }
        try {
            String codigo = txtCodigo.getText().trim();
            String direccion = txtDireccion.getText().trim();
            String ciudad = txtCiudad.getText().trim();
            String depto = txtDepartamento.getText().trim();

            if (codigo.isEmpty() || direccion.isEmpty() || ciudad.isEmpty() || depto.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Completa todos los campos",
                    "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Sucursal s = new Sucursal(idSeleccionado, codigo, direccion, ciudad, depto);

            if (idSeleccionado == 0) {
                repo.insertar(s);
                JOptionPane.showMessageDialog(this, "Sucursal creada");
            } else {
                repo.actualizar(s);
                JOptionPane.showMessageDialog(this, "Sucursal actualizada");
            }
            limpiar();
            cargarTabla();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminar() {
        if (!SesionActual.puedeBorrar(pantallaID)) {
            JOptionPane.showMessageDialog(this, "No tienes permiso para eliminar");
            return;
        }
        if (idSeleccionado == 0) {
            JOptionPane.showMessageDialog(this, "Selecciona una sucursal");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this,
            "¿Eliminar la sucursal seleccionada?", "Confirmar",
            JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            repo.eliminar(idSeleccionado);
            JOptionPane.showMessageDialog(this, "Sucursal eliminada");
            limpiar();
            cargarTabla();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void limpiar() {
        idSeleccionado = 0;
        txtCodigo.setText("");
        txtDireccion.setText("");
        txtCiudad.setText("");
        txtDepartamento.setText("");
        tabla.clearSelection();
        txtCodigo.requestFocus();
    }
}