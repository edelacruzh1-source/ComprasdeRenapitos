package com.sistemacompras.ui;

import com.sistemacompras.datos.IConexionBD;
import com.sistemacompras.entidades.Rol;
import com.sistemacompras.entidades.Usuario;
import com.sistemacompras.negocio.UsuarioService;
import com.sistemacompras.repositorios.RolRepositorio;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class UsuarioPanel extends JPanel {

    private final UsuarioService service;
    private final RolRepositorio rolRepo;

    private JTable tabla;
    private DefaultTableModel modelo;
    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JComboBox<Rol> cboRol;
    private JCheckBox chkActivo;
    private JLabel lblHint;
    private int idSeleccionado = 0;

    public UsuarioPanel(IConexionBD conexion) {
        this.service = new UsuarioService(conexion);
        this.rolRepo = new RolRepositorio(conexion);
        initComponents();
        cargarRoles();
        cargarTabla();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(248, 249, 250));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblTitulo = new JLabel("Gestión de Usuarios");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(33, 37, 41));
        add(lblTitulo, BorderLayout.NORTH);

        // Tabla
        modelo = new DefaultTableModel(
            new Object[]{"ID", "Usuario", "Rol", "Activo"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla = new JTable(modelo);
        tabla.setRowHeight(28);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
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
                "Datos del Usuario"),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        form.add(new JLabel("Usuario:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        txtUsuario = new JTextField(20);
        form.add(txtUsuario, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        form.add(new JLabel("Contraseña:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 1;
        txtPassword = new JPasswordField(20);
        form.add(txtPassword, gbc);

        gbc.gridx = 2;
        lblHint = new JLabel("(dejar vacío para no cambiar)");
        lblHint.setForeground(Color.GRAY);
        lblHint.setFont(lblHint.getFont().deriveFont(10f));
        form.add(lblHint, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        form.add(new JLabel("Rol:"), gbc);
        gbc.gridx = 1;
        cboRol = new JComboBox<>();
        cboRol.setPreferredSize(new Dimension(200, 25));
        form.add(cboRol, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        form.add(new JLabel("Activo:"), gbc);
        gbc.gridx = 1;
        chkActivo = new JCheckBox();
        chkActivo.setSelected(true);
        chkActivo.setBackground(Color.WHITE);
        form.add(chkActivo, gbc);

        // Botones
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

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
            scroll, inferior);
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

    private void cargarRoles() {
        try {
            cboRol.removeAllItems();
            for (Rol r : rolRepo.obtenerTodos()) {
                cboRol.addItem(r);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error cargando roles: " + e.getMessage());
        }
    }

    private void cargarTabla() {
        try {
            modelo.setRowCount(0);
            for (Usuario u : service.obtenerTodos()) {
                modelo.addRow(new Object[]{
                    u.getUsuarioID(),
                    u.getNombreUsuario(),
                    u.getNombreRol(),
                    u.isActivo() ? "Sí" : "No"
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
            Usuario u = service.obtenerPorId(idSeleccionado);
            if (u == null) return;

            txtUsuario.setText(u.getNombreUsuario());
            txtPassword.setText("");
            chkActivo.setSelected(u.isActivo());

            for (int i = 0; i < cboRol.getItemCount(); i++) {
                if (cboRol.getItemAt(i).getRolID() == u.getRolID()) {
                    cboRol.setSelectedIndex(i);
                    break;
                }
            }
            lblHint.setText("(dejar vacío para no cambiar)");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void guardar() {
        try {
            String usuario = txtUsuario.getText().trim();
            String password = new String(txtPassword.getPassword());
            Rol rol = (Rol) cboRol.getSelectedItem();

            if (rol == null) {
                JOptionPane.showMessageDialog(this, "Selecciona un rol");
                return;
            }

            if (idSeleccionado == 0) {
                service.crear(usuario, password, rol.getRolID(), chkActivo.isSelected());
                JOptionPane.showMessageDialog(this,
                    "Usuario creado\nEl hash de la contraseña se generó automáticamente");
            } else {
                service.actualizar(idSeleccionado, usuario, password,
                                   rol.getRolID(), chkActivo.isSelected());
                JOptionPane.showMessageDialog(this, "Usuario actualizado");
            }

            limpiar();
            cargarTabla();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Validación",
                JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminar() {
        if (idSeleccionado == 0) {
            JOptionPane.showMessageDialog(this, "Selecciona un usuario");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this,
            "¿Eliminar el usuario seleccionado?", "Confirmar",
            JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            service.eliminar(idSeleccionado);
            JOptionPane.showMessageDialog(this, "Usuario eliminado");
            limpiar();
            cargarTabla();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void limpiar() {
        idSeleccionado = 0;
        txtUsuario.setText("");
        txtPassword.setText("");
        chkActivo.setSelected(true);
        if (cboRol.getItemCount() > 0) cboRol.setSelectedIndex(0);
        tabla.clearSelection();
        lblHint.setText("(obligatoria al crear)");
        txtUsuario.requestFocus();
    }
}