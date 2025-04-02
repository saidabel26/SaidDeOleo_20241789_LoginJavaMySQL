package vista;

import modelo.Usuario;
import dao.UsuarioDAO;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class ActualizarUsuarioFrame extends JFrame {
    private JTextField txtUsuario, txtNombre, txtApellido, txtTelefono, txtCorreo;
    private JPasswordField txtContrasena;
    private JButton btnActualizar;
    private Usuario usuarioOriginal;
    private PrincipalFrame principalFrame;

    public ActualizarUsuarioFrame(Usuario usuario, PrincipalFrame principal) {
        this.usuarioOriginal = usuario;
        this.principalFrame = principal;

        setTitle("Actualizar Usuario");
        setSize(400, 350);
        setLocationRelativeTo(principal);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Campos de texto
        txtUsuario = new JTextField(usuario.getUsuario());
        txtNombre = new JTextField(usuario.getNombre());
        txtApellido = new JTextField(usuario.getApellido());
        txtTelefono = new JTextField(usuario.getTelefono());
        txtCorreo = new JTextField(usuario.getCorreo());
        txtContrasena = new JPasswordField();

        // Configurar campos
        txtUsuario.setEditable(false); // El username no se puede modificar

        // Agregar componentes al panel
        panel.add(new JLabel("Usuario:"));
        panel.add(txtUsuario);
        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Apellido:"));
        panel.add(txtApellido);
        panel.add(new JLabel("Teléfono:"));
        panel.add(txtTelefono);
        panel.add(new JLabel("Correo:"));
        panel.add(txtCorreo);
        panel.add(new JLabel("Nueva Contraseña:"));
        panel.add(txtContrasena);

        // Botón de actualizar
        btnActualizar = new JButton("Actualizar");
        btnActualizar.addActionListener(e -> actualizarUsuario());

        JPanel panelBoton = new JPanel();
        panelBoton.add(btnActualizar);

        // Configuración final
        add(panel, BorderLayout.CENTER);
        add(panelBoton, BorderLayout.SOUTH);
        setVisible(true);
    }

    private void actualizarUsuario() {
        try {
            // Crear objeto con datos actualizados
            Usuario usuarioActualizado = new Usuario(
                    txtUsuario.getText(),
                    txtNombre.getText(),
                    txtApellido.getText(),
                    txtTelefono.getText(),
                    txtCorreo.getText(),
                    new String(txtContrasena.getPassword()).isEmpty() ?
                            usuarioOriginal.getContrasena() :
                            new String(txtContrasena.getPassword())
            );

            // Validar campos obligatorios
            if (txtNombre.getText().isEmpty() || txtApellido.getText().isEmpty() ||
                    txtTelefono.getText().isEmpty() || txtCorreo.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos excepto contraseña son obligatorios");
                return;
            }

            // Actualizar en BD
            UsuarioDAO dao = new UsuarioDAO();
            if (dao.actualizarUsuario(usuarioOriginal.getUsuario(), usuarioActualizado)) {
                JOptionPane.showMessageDialog(this, "Usuario actualizado correctamente");
                principalFrame.actualizarTabla();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar el usuario");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error de base de datos: " + ex.getMessage());
        }
    }
}