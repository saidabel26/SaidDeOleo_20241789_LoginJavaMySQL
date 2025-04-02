package vista;

import modelo.Usuario;
import dao.UsuarioDAO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class PrincipalFrame extends JFrame {
    private JTable tablaUsuarios;
    private DefaultTableModel model;
    private Usuario usuarioActual;

    public PrincipalFrame(Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
        setTitle("Clientes Registrados");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Modelo de tabla
        String[] columnas = {"Usuario", "Nombre", "Apellido", "Teléfono", "Correo"};
        model = new DefaultTableModel(columnas, 0);
        tablaUsuarios = new JTable(model);
        actualizarTabla();

        // Botones
        JButton btnNuevo = new JButton("Nuevo");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnCerrar = new JButton("Cerrar Sesión");

        // Panel de botones
        JPanel panelBotones = new JPanel();
        panelBotones.add(btnNuevo);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnCerrar);

        // Listeners
        btnNuevo.addActionListener(e -> nuevoUsuario());
        btnActualizar.addActionListener(e -> actualizarUsuario());
        btnEliminar.addActionListener(e -> eliminarUsuario());
        btnCerrar.addActionListener(e -> cerrarSesion());

        // Layout
        setLayout(new BorderLayout());
        add(new JScrollPane(tablaUsuarios), BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
        setVisible(true);
    }

    public void actualizarTabla() {
        model.setRowCount(0); // Limpiar tabla
        try {
            UsuarioDAO dao = new UsuarioDAO();
            List<Usuario> usuarios = dao.obtenerTodosUsuarios();
            for (Usuario u : usuarios) {
                model.addRow(new Object[]{
                        u.getUsuario(),
                        u.getNombre(),
                        u.getApellido(),
                        u.getTelefono(),
                        u.getCorreo()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar usuarios: " + e.getMessage());
        }
    }

    private void nuevoUsuario() {
        new RegistroFrame();
        actualizarTabla();
    }

    private void actualizarUsuario() {
        int filaSeleccionada = tablaUsuarios.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario de la tabla");
            return;
        }

        String usuario = (String) model.getValueAt(filaSeleccionada, 0);
        try {
            UsuarioDAO dao = new UsuarioDAO();
            // Obtener usuario COMPLETO de la base de datos
            Usuario user = dao.obtenerUsuarioPorUsername(usuario); // Nuevo método en UsuarioDAO

            if (user != null) {
                new ActualizarUsuarioFrame(user, this);
            } else {
                JOptionPane.showMessageDialog(this, "Usuario no encontrado en la base de datos");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar: " + e.getMessage());
        }
    }

    private void eliminarUsuario() {
        int filaSeleccionada = tablaUsuarios.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario para eliminar");
            return;
        }

        String usuario = (String) model.getValueAt(filaSeleccionada, 0);
        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Eliminar al usuario " + usuario + "?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                UsuarioDAO dao = new UsuarioDAO();
                if (dao.eliminarUsuario(usuario)) {
                    JOptionPane.showMessageDialog(this, "Usuario eliminado");
                    actualizarTabla();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al eliminar: " + e.getMessage());
            }
        }
    }

    private void cerrarSesion() {
        new LoginFrame();
        dispose();
    }
}