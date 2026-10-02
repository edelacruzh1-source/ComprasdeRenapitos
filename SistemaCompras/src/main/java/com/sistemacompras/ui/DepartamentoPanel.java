package com.sistemacompras.ui;

import com.sistemacompras.datos.IConexionBD;
import com.sistemacompras.entidades.Departamento;
import com.sistemacompras.entidades.Sucursal;
import com.sistemacompras.repositorios.DepartamentoRepositorio;
import com.sistemacompras.repositorios.SucursalRepositorio;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DepartamentoPanel extends JPanel {

    private final DepartamentoRepositorio repo;
    private final SucursalRepositorio sucursalRepo;
    private JTable tabla;
    private DefaultTableModel modelo;
    private JComboBox<Sucursal> cboSucursal;
    private JTextField txtNombre, txtDescripcion;
    private int idSeleccionado = 0;
    private final int pantallaID = Pantallas.DEPARTAMENTOS;

    public DepartamentoPanel(IConexionBD conexion) {
        this.repo = new DepartamentoRepositorio(conexion);
        this.sucursalRepo = new SucursalRepositorio(conexion);
        initComponents();
        cargarSucursales();
        cargarTabla();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(248, 249, 250));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblTitulo = new JLabel("Gestión de Departamentos");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(33, 37, 41));
        add(lblTitulo, BorderLayout.NORTH);

        modelo = new DefaultTableModel(
            new Object[]{"ID", "Nombre", "Descripción", "Sucursal"}, 0) {
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
                "Datos del Departamento"),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        form.add(new JLabel("Sucursal:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        cboSucursal = new JComboBox<>();
        cboSucursal.setPreferredSize(new Dimension(300, 25));
        form.add(cboSucursal, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        form.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        txtNombre = new JTextField(20);
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
        cboSucursal.setEnabled(puedeEditar);
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

    private void cargarSucursales() {
        try {
            cboSucursal.removeAllItems();
            for (Sucursal s : sucursalRepo.obtenerTodos()) cboSucursal.addItem(s);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error cargando sucursales: " + e.getMessage());
        }
    }

    private void cargarTabla() {
        try {
            modelo.setRowCount(0);
            List<Sucursal> sucursales = sucursalRepo.obtenerTodos();
            for (Departamento d : repo.obtenerTodos()) {
                String nombreSuc = sucursales.stream()
                    .filter(s -> s.getSucursalID() == d.getSucursalID())
                    .map(Sucursal::getCodigo).findFirst().orElse("?");
                modelo.addRow(new Object[]{
                    d.getDepartamentoID(), d.getNombre(),
                    d.getDescripcion(), nombreSuc
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
            Departamento d = repo.obtenerPorId(idSeleccionado);
            if (d == null) return;

            txtNombre.setText(d.getNombre());
            txtDescripcion.setText(d.getDescripcion());
            for (int i = 0; i < cboSucursal.getItemCount(); i++) {
                if (cboSucursal.getItemAt(i).getSucursalID() == d.getSucursalID()) {
                    cboSucursal.setSelectedIndex(i); break;
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void guardar() {
        if (!SesionActual.puedeCrear(pantallaID) && !SesionActual.puedeActualizar(pantallaID)) {
            JOptionPane.showMessageDialog(this, "No tienes permiso para esta acción");
            return;
        }
        try {
            Sucursal suc = (Sucursal) cboSucursal.getSelectedItem();
            if (suc == null) {
                JOptionPane.showMessageDialog(this, "Selecciona una sucursal");
                return;
            }
            String nombre = txtNombre.getText().trim();
            String desc = txtDescripcion.getText().trim();
            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El nombre es obligatorio");
                return;
            }
            Departamento d = new Departamento(idSeleccionado, suc.getSucursalID(), nombre, desc);
            if (idSeleccionado == 0) {
                repo.insertar(d);
                JOptionPane.showMessageDialog(this, "Departamento creado");
            } else {
                repo.actualizar(d);
                JOptionPane.showMessageDialog(this, "Departamento actualizado");
            }
            limpiar();
            cargarTabla();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void eliminar() {
        if (!SesionActual.puedeBorrar(pantallaID)) {
            JOptionPane.showMessageDialog(this, "No tienes permiso para eliminar");
            return;
        }
        if (idSeleccionado == 0) {
            JOptionPane.showMessageDialog(this, "Selecciona un departamento");
            return;
        }
        if (JOptionPane.showConfirmDialog(this, "¿Eliminar?", "Confirmar",
            JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) return;
        try {
            repo.eliminar(idSeleccionado);
            JOptionPane.showMessageDialog(this, "Departamento eliminado");
            limpiar();
            cargarTabla();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void limpiar() {
        idSeleccionado = 0;
        txtNombre.setText("");
        txtDescripcion.setText("");
        if (cboSucursal.getItemCount() > 0) cboSucursal.setSelectedIndex(0);
        tabla.clearSelection();
    }
}