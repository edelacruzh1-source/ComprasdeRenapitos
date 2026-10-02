package com.sistemacompras.ui;

import com.sistemacompras.datos.IConexionBD;
import com.sistemacompras.entidades.Articulo;
import com.sistemacompras.entidades.Departamento;
import com.sistemacompras.entidades.PedidoInterno;
import com.sistemacompras.repositorios.ArticuloRepositorio;
import com.sistemacompras.repositorios.DepartamentoRepositorio;
import com.sistemacompras.repositorios.PedidoInternoRepositorio;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PedidoInternoPanel extends JPanel {

    private final PedidoInternoRepositorio repo;
    private final DepartamentoRepositorio deptoRepo;
    private final ArticuloRepositorio articuloRepo;
    private JTable tabla;
    private DefaultTableModel modelo;
    private JComboBox<Departamento> cboDepartamento;
    private JComboBox<Articulo> cboArticulo;
    private JTextField txtCantidad, txtFechaSolicitud, txtFechaNecesidad;
    private int idSeleccionado = 0;
    private final int pantallaID = Pantallas.PEDIDOS;

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public PedidoInternoPanel(IConexionBD conexion) {
        this.repo = new PedidoInternoRepositorio(conexion);
        this.deptoRepo = new DepartamentoRepositorio(conexion);
        this.articuloRepo = new ArticuloRepositorio(conexion);
        initComponents();
        cargarCombos();
        cargarTabla();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(248, 249, 250));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblTitulo = new JLabel("Gestión de Pedidos Internos");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        add(lblTitulo, BorderLayout.NORTH);

        modelo = new DefaultTableModel(
            new Object[]{"ID", "Departamento", "Artículo", "Cantidad",
                         "F. Solicitud", "F. Necesidad"}, 0) {
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
            BorderFactory.createTitledBorder("Datos del Pedido"),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        form.add(new JLabel("Departamento:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1; gbc.gridwidth = 3;
        cboDepartamento = new JComboBox<>();
        cboDepartamento.setPreferredSize(new Dimension(300, 25));
        form.add(cboDepartamento, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0; gbc.gridwidth = 1;
        form.add(new JLabel("Artículo:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1; gbc.gridwidth = 3;
        cboArticulo = new JComboBox<>();
        cboArticulo.setPreferredSize(new Dimension(300, 25));
        form.add(cboArticulo, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0; gbc.gridwidth = 1;
        form.add(new JLabel("Cantidad:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        txtCantidad = new JTextField(10);
        form.add(txtCantidad, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0;
        form.add(new JLabel("F. Solicitud:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        txtFechaSolicitud = new JTextField(12);
        txtFechaSolicitud.setText(LocalDate.now().format(FMT));
        form.add(txtFechaSolicitud, gbc);

        gbc.gridx = 2; gbc.weightx = 0;
        form.add(new JLabel("F. Necesidad:"), gbc);
        gbc.gridx = 3; gbc.weightx = 1;
        txtFechaNecesidad = new JTextField(12);
        txtFechaNecesidad.setText(LocalDate.now().plusDays(7).format(FMT));
        form.add(txtFechaNecesidad, gbc);

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
        cboDepartamento.setEnabled(puedeEditar);
        cboArticulo.setEnabled(puedeEditar);
        txtCantidad.setEnabled(puedeEditar);
        txtFechaSolicitud.setEnabled(puedeEditar);
        txtFechaNecesidad.setEnabled(puedeEditar);

        JPanel inferior = new JPanel(new BorderLayout(10, 10));
        inferior.setBackground(new Color(248, 249, 250));
        inferior.add(form, BorderLayout.CENTER);
        inferior.add(botones, BorderLayout.SOUTH);

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scroll, inferior);
        split.setResizeWeight(0.5);
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

    private void cargarCombos() {
        try {
            cboDepartamento.removeAllItems();
            for (Departamento d : deptoRepo.obtenerTodos()) cboDepartamento.addItem(d);
            cboArticulo.removeAllItems();
            for (Articulo a : articuloRepo.obtenerTodos()) cboArticulo.addItem(a);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error cargando combos: " + e.getMessage());
        }
    }

    private void cargarTabla() {
        try {
            modelo.setRowCount(0);
            List<Departamento> deptos = deptoRepo.obtenerTodos();
            List<Articulo> articulos = articuloRepo.obtenerTodos();

            for (PedidoInterno p : repo.obtenerTodos()) {
                String nombreDepto = deptos.stream()
                    .filter(d -> d.getDepartamentoID() == p.getDepartamentoID())
                    .map(Departamento::getNombre).findFirst().orElse("?");
                String nombreArt = articulos.stream()
                    .filter(a -> a.getArticuloID() == p.getArticuloID())
                    .map(Articulo::getNombre).findFirst().orElse("?");

                modelo.addRow(new Object[]{
                    p.getPedidoID(), nombreDepto, nombreArt, p.getCantidad(),
                    p.getFechaSolicitud().format(FMT),
                    p.getFechaNecesidad().format(FMT)
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
            PedidoInterno p = repo.obtenerPorId(idSeleccionado);
            if (p == null) return;

            txtCantidad.setText(String.valueOf(p.getCantidad()));
            txtFechaSolicitud.setText(p.getFechaSolicitud().format(FMT));
            txtFechaNecesidad.setText(p.getFechaNecesidad().format(FMT));

            for (int i = 0; i < cboDepartamento.getItemCount(); i++) {
                if (cboDepartamento.getItemAt(i).getDepartamentoID() == p.getDepartamentoID()) {
                    cboDepartamento.setSelectedIndex(i); break;
                }
            }
            for (int i = 0; i < cboArticulo.getItemCount(); i++) {
                if (cboArticulo.getItemAt(i).getArticuloID() == p.getArticuloID()) {
                    cboArticulo.setSelectedIndex(i); break;
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
            Departamento depto = (Departamento) cboDepartamento.getSelectedItem();
            Articulo art = (Articulo) cboArticulo.getSelectedItem();
            if (depto == null || art == null) {
                JOptionPane.showMessageDialog(this, "Selecciona departamento y artículo");
                return;
            }
            int cantidad = Integer.parseInt(txtCantidad.getText().trim());
            LocalDate fSol = LocalDate.parse(txtFechaSolicitud.getText().trim(), FMT);
            LocalDate fNec = LocalDate.parse(txtFechaNecesidad.getText().trim(), FMT);

            if (fNec.isBefore(fSol)) {
                JOptionPane.showMessageDialog(this,
                    "F. necesidad no puede ser anterior a F. solicitud");
                return;
            }

            PedidoInterno p = new PedidoInterno(idSeleccionado,
                depto.getDepartamentoID(), art.getArticuloID(),
                cantidad, fSol, fNec);

            if (idSeleccionado == 0) {
                repo.insertar(p);
                JOptionPane.showMessageDialog(this, "Pedido creado");
            } else {
                repo.actualizar(p);
                JOptionPane.showMessageDialog(this, "Pedido actualizado");
            }
            limpiar();
            cargarTabla();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "La cantidad debe ser un número");
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
            JOptionPane.showMessageDialog(this, "Pedido eliminado");
            limpiar();
            cargarTabla();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void limpiar() {
        idSeleccionado = 0;
        txtCantidad.setText("");
        txtFechaSolicitud.setText(LocalDate.now().format(FMT));
        txtFechaNecesidad.setText(LocalDate.now().plusDays(7).format(FMT));
        if (cboDepartamento.getItemCount() > 0) cboDepartamento.setSelectedIndex(0);
        if (cboArticulo.getItemCount() > 0) cboArticulo.setSelectedIndex(0);
        tabla.clearSelection();
    }
}