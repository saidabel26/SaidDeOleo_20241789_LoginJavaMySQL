package vista;

import modelo.Usuario;
import dao.UsuarioDAO;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class RegistroFrame extends JFrame {
    private JTextField txtUsuario, txtNombre, txtApellido, txtTelefono, txtCorreo;
    private JPasswordField txtContrasena, txtConfirmar;

    public RegistroFrame() {
        setTitle("Registro");
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(8, 2));
        panel.add(new JLabel("Usuario*:"));
        txtUsuario = new JTextField();
        panel.add(txtUsuario);

        panel.add(new JLabel("Nombre*:"));
        txtNombre = new JTextField();
        panel.add(txtNombre);

        panel.add(new JLabel("Apellido*:"));
        txtApellido = new JTextField();
        panel.add(txtApellido);

        panel.add(new JLabel("Teléfono*:"));
        txtTelefono = new JTextField();
        panel.add(txtTelefono);

        panel.add(new JLabel("Correo*:"));
        txtCorreo = new JTextField();
        panel.add(txtCorreo);

        panel.add(new JLabel("Contraseña*:"));
        txtContrasena = new JPasswordField();
        panel.add(txtContrasena);

        panel.add(new JLabel("Confirmar Contraseña*:"));
        txtConfirmar = new JPasswordField();
        panel.add(txtConfirmar);

        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.addActionListener(e -> validarRegistro());

        panel.add(btnRegistrar);
        add(panel);
        setVisible(true);
    }

    private void validarRegistro() {
        String usuario = txtUsuario.getText();
        String nombre = txtNombre.getText();
        String apellido = txtApellido.getText();
        String telefono = txtTelefono.getText();
        String correo = txtCorreo.getText();
        String contrasena = new String(txtContrasena.getPassword());
        String confirmar = new String(txtConfirmar.getPassword());

        // Validar campos vacíos
        if(usuario.isEmpty() || nombre.isEmpty() || apellido.isEmpty() ||
                telefono.isEmpty() || correo.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios");
            return;
        }

        // Validar contraseñas
        if(!contrasena.equals(confirmar)) {
            JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden");
            return;
        }

        // Registrar usuario
        try {
            UsuarioDAO dao = new UsuarioDAO();
            boolean exito = dao.registrarUsuario(new Usuario(
                    usuario, nombre, apellido, telefono, correo, contrasena
            ));

            if(exito) {
                JOptionPane.showMessageDialog(this, "Registro exitoso");
                dispose();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al registrar: " + e.getMessage());
        }
    }
}