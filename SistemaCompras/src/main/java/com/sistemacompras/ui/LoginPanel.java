package com.sistemacompras.ui;

import com.sistemacompras.datos.ConexionFactory;
import com.sistemacompras.datos.IConexionBD;
import com.sistemacompras.entidades.Usuario;
import com.sistemacompras.negocio.LoginService;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {

    private final MainFrame frame;
    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JComboBox<String> cboGestor;
    private JLabel lblEstado;
    private IConexionBD conexionActual;

    public LoginPanel(MainFrame frame) {
        this.frame = frame;
        this.conexionActual = ConexionFactory.obtenerConexion(
            ConexionFactory.TipoGestor.SQL_SERVER);
        initComponents();
        actualizarEstado();
    }

    private void initComponents() {
        // Layout principal: GridBagLayout centra la tarjeta en el panel
        setLayout(new GridBagLayout());
        setBackground(new Color(240, 242, 245));

        // ===== Tarjeta contenedora =====
        JPanel tarjeta = new JPanel();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(30, 40, 30, 40)
        ));
        tarjeta.setPreferredSize(new Dimension(420, 500));
        tarjeta.setMaximumSize(new Dimension(420, 500));

        // ===== Título =====
        JLabel lblTitulo = new JLabel("INICIAR SESIÓN");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(33, 37, 41));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ===== Subtítulo =====
        JLabel lblSubtitulo = new JLabel("Sistema de Adquisiciones");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSubtitulo.setForeground(new Color(108, 117, 125));
        lblSubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ===== Label Usuario =====
        JLabel lblUsuario = new JLabel("Usuario");
        lblUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblUsuario.setForeground(new Color(108, 117, 125));
        lblUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ===== Campo Usuario =====
        txtUsuario = new JTextField();
        txtUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtUsuario.setMaximumSize(new Dimension(340, 35));
        txtUsuario.setPreferredSize(new Dimension(340, 35));
        txtUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);
        txtUsuario.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(206, 212, 218)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        // ===== Label Contraseña =====
        JLabel lblPassword = new JLabel("Contraseña");
        lblPassword.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblPassword.setForeground(new Color(108, 117, 125));
        lblPassword.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ===== Campo Contraseña =====
        txtPassword = new JPasswordField();
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtPassword.setMaximumSize(new Dimension(340, 35));
        txtPassword.setPreferredSize(new Dimension(340, 35));
        txtPassword.setAlignmentX(Component.CENTER_ALIGNMENT);
        txtPassword.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(206, 212, 218)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        txtPassword.addActionListener(e -> autenticar());

        // ===== Label Base de datos =====
        JLabel lblGestor = new JLabel("Base de datos");
        lblGestor.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblGestor.setForeground(new Color(108, 117, 125));
        lblGestor.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ===== Combo Base de datos =====
        cboGestor = new JComboBox<>(new String[]{"SQL Server", "MySQL"});
        cboGestor.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cboGestor.setMaximumSize(new Dimension(340, 35));
        cboGestor.setPreferredSize(new Dimension(340, 35));
        cboGestor.setAlignmentX(Component.CENTER_ALIGNMENT);
        cboGestor.addActionListener(e -> cambiarGestor());

        // ===== Label Estado de conexión =====
        lblEstado = new JLabel();
        lblEstado.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblEstado.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ===== Botón Ingresar =====
        JButton btnIngresar = new JButton("INGRESAR");
        btnIngresar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnIngresar.setForeground(Color.WHITE);
        btnIngresar.setBackground(new Color(13, 110, 253));
        btnIngresar.setFocusPainted(false);
        btnIngresar.setBorderPainted(false);
        btnIngresar.setMaximumSize(new Dimension(340, 40));
        btnIngresar.setPreferredSize(new Dimension(340, 40));
        btnIngresar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnIngresar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnIngresar.addActionListener(e -> autenticar());

        // ===== Armar tarjeta =====
        tarjeta.add(lblTitulo);
        tarjeta.add(Box.createVerticalStrut(5));
        tarjeta.add(lblSubtitulo);
        tarjeta.add(Box.createVerticalStrut(25));

        tarjeta.add(lblUsuario);
        tarjeta.add(Box.createVerticalStrut(5));
        tarjeta.add(txtUsuario);
        tarjeta.add(Box.createVerticalStrut(15));

        tarjeta.add(lblPassword);
        tarjeta.add(Box.createVerticalStrut(5));
        tarjeta.add(txtPassword);
        tarjeta.add(Box.createVerticalStrut(15));

        tarjeta.add(lblGestor);
        tarjeta.add(Box.createVerticalStrut(5));
        tarjeta.add(cboGestor);
        tarjeta.add(Box.createVerticalStrut(10));

        tarjeta.add(lblEstado);
        tarjeta.add(Box.createVerticalStrut(15));

        tarjeta.add(btnIngresar);

        // Agregar tarjeta al centro del panel
        add(tarjeta);
    }

    private void cambiarGestor() {
        String seleccionado = (String) cboGestor.getSelectedItem();
        ConexionFactory.TipoGestor tipo = "MySQL".equals(seleccionado)
            ? ConexionFactory.TipoGestor.MYSQL
            : ConexionFactory.TipoGestor.SQL_SERVER;

        conexionActual = ConexionFactory.obtenerConexion(tipo);
        actualizarEstado();
    }

    private void actualizarEstado() {
        if (conexionActual.probarConexion()) {
            lblEstado.setText("✅ Conectado a " + conexionActual.getNombreGestor());
            lblEstado.setForeground(new Color(25, 135, 84));
        } else {
            lblEstado.setText("❌ Sin conexión a " + conexionActual.getNombreGestor());
            lblEstado.setForeground(new Color(220, 53, 69));
        }
    }

    private void autenticar() {
        String usuario = txtUsuario.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (usuario.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Ingresa usuario y contraseña", "Aviso",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!conexionActual.probarConexion()) {
            JOptionPane.showMessageDialog(this,
                "No hay conexión a " + conexionActual.getNombreGestor() +
                "\nRevisa tu configuración.",
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            LoginService service = new LoginService(conexionActual);
            Usuario u = service.autenticar(usuario, password);

            if (u == null) {
                JOptionPane.showMessageDialog(this,
                    "Credenciales inválidas", "Error",
                    JOptionPane.ERROR_MESSAGE);
                txtPassword.setText("");
                txtPassword.requestFocus();
                return;
            }

            // Guardar usuario, conexión y cargar permisos
            SesionActual.setUsuario(u);
            SesionActual.setConexion(conexionActual);
            SesionActual.cargarPermisos();

            // Limpiar campos
            txtUsuario.setText("");
            txtPassword.setText("");

            // Mostrar menú
            frame.mostrarPanel("menu");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Error: " + ex.getMessage(), "Error",
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}