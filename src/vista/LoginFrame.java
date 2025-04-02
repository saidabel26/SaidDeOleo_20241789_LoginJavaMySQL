package vista;

import modelo.Usuario;
import dao.UsuarioDAO;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class LoginFrame extends JFrame {
    private JTextField txtUsuario;
    private JPasswordField txtContrasena;

    public LoginFrame() {
        setTitle("Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(new JLabel("Usuario:"));
        txtUsuario = new JTextField();
        panel.add(txtUsuario);
        panel.add(new JLabel("Contraseña:"));
        txtContrasena = new JPasswordField();
        panel.add(txtContrasena);

        JButton btnLogin = new JButton("Iniciar Sesión");
        JButton btnRegistro = new JButton("Registrarse");

        btnLogin.addActionListener(e -> {
            String usuario = txtUsuario.getText();
            String contrasena = new String(txtContrasena.getPassword());

            if(usuario.isEmpty() || contrasena.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe completar ambos campos");
                return;
            }

            try {
                UsuarioDAO dao = new UsuarioDAO();
                Usuario user = dao.login(usuario, contrasena);
                if(user != null) {
                    new PrincipalFrame(user);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Credenciales incorrectas");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error de conexión");
            }
        });

        btnRegistro.addActionListener(e -> new RegistroFrame());

        panel.add(btnLogin);
        panel.add(btnRegistro);
        add(panel);
        setVisible(true);
    }
}