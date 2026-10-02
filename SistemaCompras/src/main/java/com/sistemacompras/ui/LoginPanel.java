package com.sistemacompras.ui;

import com.sistemacompras.datos.IConexionBD;
import com.sistemacompras.entidades.Usuario;
import com.sistemacompras.negocio.LoginService;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {

    private final IConexionBD conexion;
    private final MainFrame frame;
    private JTextField txtUsuario;
    private JPasswordField txtPassword;

    public LoginPanel(IConexionBD conexion, MainFrame frame) {
        this.conexion = conexion;
        this.frame = frame;
        initComponents();
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
        tarjeta.setPreferredSize(new Dimension(380, 320));

        JLabel lblTitulo = new JLabel("INICIAR SESIÓN");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setForeground(new Color(33, 37, 41));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblSubtitulo = new JLabel("Sistema de Adquisiciones");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSubtitulo.setForeground(new Color(108, 117, 125));
        lblSubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

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

        // Enter en contraseña dispara login
        txtPassword.addActionListener(e -> autenticar());

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
        tarjeta.add(Box.createVerticalStrut(25));
        tarjeta.add(btnIngresar);

        add(tarjeta);
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

        try {
            LoginService service = new LoginService(conexion);
            Usuario u = service.autenticar(usuario, password);

            if (u == null) {
                JOptionPane.showMessageDialog(this,
                    "Credenciales inválidas", "Error",
                    JOptionPane.ERROR_MESSAGE);
                txtPassword.setText("");
                txtPassword.requestFocus();
                return;
            }

            // Guardar usuario y cargar permisos ANTES de mostrar el menú
            SesionActual.setUsuario(u);
            SesionActual.cargarPermisos(conexion);

            // Limpiar campos para el próximo login
            txtUsuario.setText("");
            txtPassword.setText("");

            // Mostrar el menú (se construye AHORA con los permisos cargados)
            frame.mostrarPanel("menu");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Error: " + ex.getMessage(), "Error",
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}