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
        setLayout(new GridBagLayout());
        setBackground(new Color(240, 242, 245));

        JPanel tarjeta = new JPanel();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(30, 40, 30, 40)
        ));
        tarjeta.setPreferredSize(new Dimension(420, 460));

        // Título
        JLabel lblTitulo = new JLabel("INICIAR SESIÓN");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setForeground(new Color(33, 37, 41));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblSubtitulo = new JLabel("Sistema de Adquisiciones");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSubtitulo.setForeground(new Color(108, 117, 125));
        lblSubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Usuario
        JLabel lblUsuario = new JLabel("Usuario");
        lblUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblUsuario.setAlignmentX(Component.LEFT_ALIGNMENT);

        txtUsuario = new JTextField();
        txtUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtUsuario.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        txtUsuario.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(206, 212, 218)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        // Contraseña
        JLabel lblPassword = new JLabel("Contraseña");
        lblPassword.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblPassword.setAlignmentX(Component.LEFT_ALIGNMENT);

        txtPassword = new JPasswordField();
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtPassword.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        txtPassword.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(206, 212, 218)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        txtPassword.addActionListener(e -> autenticar());

        // Selector de Base de Datos
        JLabel lblGestor = new JLabel("Base de datos");
        lblGestor.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblGestor.setAlignmentX(Component.LEFT_ALIGNMENT);

        cboGestor = new JComboBox<>(new String[]{"SQL Server", "MySQL"});
        cboGestor.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cboGestor.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        cboGestor.setAlignmentX(Component.LEFT_ALIGNMENT);
        cboGestor.addActionListener(e -> cambiarGestor());

        // Estado de conexión
        lblEstado = new JLabel();
        lblEstado.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblEstado.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Botón
        JButton btnIngresar = new JButton("INGRESAR");
        btnIngresar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnIngresar.setForeground(Color.WHITE);
        btnIngresar.setBackground(new Color(13, 110, 253));
        btnIngresar.setFocusPainted(false);
        btnIngresar.setBorderPainted(false);
        btnIngresar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btnIngresar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnIngresar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnIngresar.addActionListener(e -> autenticar());

        // Armar tarjeta
        tarjeta.add(lblTitulo);
        tarjeta.add(Box.createVerticalStrut(5));
        tarjeta.add(lblSubtitulo);
        tarjeta.add(Box.createVerticalStrut(20));
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