package com.sistemacompras.ui;

import com.sistemacompras.datos.IConexionBD;
import com.sistemacompras.entidades.Adjudicacion;
import com.sistemacompras.entidades.OrdenCompra;
import com.sistemacompras.repositorios.AdjudicacionRepositorio;
import com.sistemacompras.repositorios.OrdenCompraRepositorio;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AdjudicacionPanel extends JPanel {

    private final AdjudicacionRepositorio repo;
    private final OrdenCompraRepositorio ordenRepo;
    private JTable tabla;
    private DefaultTableModel modelo;
    private JComboBox<OrdenCompra> cboOrden;
    private JTextField txtFechaResolucion;
    private int idSeleccionado = 0;
    private final int pantallaID = Pantallas.ADJUDICACIONES;

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public AdjudicacionPanel(IConexionBD conexion) {
        this.repo = new AdjudicacionRepositorio(conexion);
        this.ordenRepo = new OrdenCompraRepositorio(conexion);
        initComponents();
        cargarOrdenes();
        cargarTabla();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(248, 249, 250));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblTitulo = new JLabel("Gestión de Adjudicaciones");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        add(lblTitulo, BorderLayout.NORTH);

        modelo = new DefaultTableModel(
            new Object[]{"ID", "Orden", "Fecha Resolución"}, 0) {
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
            BorderFactory.createTitledBorder("Datos de la Adjudicación"),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        form.add(new JLabel("Orden:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        cboOrden = new JComboBox<>();
        cboOrden.setPreferredSize(new Dimension(350, 25));
        form.add(cboOrden, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        form.add(new JLabel("Fecha Resolución:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        txtFechaResolucion = new JTextField(12);
        txtFechaResolucion.setText(LocalDate.now().format(FMT));
        form.add(txtFechaResolucion, gbc);

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
        cboOrden.setEnabled(puedeEditar);
        txtFechaResolucion.setEnabled(puedeEditar);

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

    private void cargarOrdenes() {
        try {
            cboOrden.removeAllItems();
            for (OrdenCompra o : ordenRepo.obtenerTodos()) cboOrden.addItem(o);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error cargando órdenes: " + e.getMessage());
        }
    }

    private void cargarTabla() {
        try {
            modelo.setRowCount(0);
            List<OrdenCompra> ordenes = ordenRepo.obtenerTodos();
            for (Adjudicacion a : repo.obtenerTodos()) {
                String descOrden = ordenes.stream()
                    .filter(o -> o.getOrdenID() == a.getOrdenID())
                    .map(OrdenCompra::toString).findFirst().orElse("?");
                modelo.addRow(new Object[]{
                    a.getAdjudicacionID(), descOrden,
                    a.getFechaResolucion().format(FMT)
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
            Adjudicacion a = repo.obtenerPorId(idSeleccionado);
            if (a == null) return;

            txtFechaResolucion.setText(a.getFechaResolucion().format(FMT));
            for (int i = 0; i < cboOrden.getItemCount(); i++) {
                if (cboOrden.getItemAt(i).getOrdenID() == a.getOrdenID()) {
                    cboOrden.setSelectedIndex(i); break;
                }
            }
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
            OrdenCompra orden = (OrdenCompra) cboOrden.getSelectedItem();
            if (orden == null) {
                JOptionPane.showMessageDialog(this, "Selecciona una orden");
                return;
            }
            LocalDate fRes = LocalDate.parse(txtFechaResolucion.getText().trim(), FMT);

            if (fRes.isBefore(orden.getFechaCreacion())) {
                JOptionPane.showMessageDialog(this,
                    "F. resolución no puede ser anterior a creación de la orden");
                return;
            }

            Adjudicacion a = new Adjudicacion(idSeleccionado, orden.getOrdenID(), fRes);

            if (idSeleccionado == 0) {
                repo.insertar(a);
                JOptionPane.showMessageDialog(this, "Adjudicación creada");
            } else {
                repo.actualizar(a);
                JOptionPane.showMessageDialog(this, "Adjudicación actualizada");
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
            JOptionPane.showMessageDialog(this, "Adjudicación eliminada");
            limpiar();
            cargarTabla();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void limpiar() {
        idSeleccionado = 0;
        txtFechaResolucion.setText(LocalDate.now().format(FMT));
        if (cboOrden.getItemCount() > 0) cboOrden.setSelectedIndex(0);
        tabla.clearSelection();
    }
}