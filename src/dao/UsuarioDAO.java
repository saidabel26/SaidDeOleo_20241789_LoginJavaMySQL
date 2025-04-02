package dao;

import modelo.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    private Connection conexion;

    public UsuarioDAO() throws SQLException {
        conexion = DatabaseConnection.getInstance().getConnection();
    }

    // Registrar nuevo usuario
    public boolean registrarUsuario(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuarios (usuario, nombre, apellido, telefono, correo, contraseña) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, usuario.getUsuario());
            pstmt.setString(2, usuario.getNombre());
            pstmt.setString(3, usuario.getApellido());
            pstmt.setString(4, usuario.getTelefono());
            pstmt.setString(5, usuario.getCorreo());
            pstmt.setString(6, usuario.getContrasena());
            return pstmt.executeUpdate() > 0;
        }
    }

    // Login de usuario
    public Usuario login(String usuario, String contrasena) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE usuario = ? AND contraseña = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, usuario);
            pstmt.setString(2, contrasena);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapearUsuario(rs);
                }
            }
        }
        return null;
    }

    // Obtener usuario por username (para actualización)
    public Usuario obtenerUsuarioPorUsername(String username) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE usuario = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapearUsuario(rs);
                }
            }
        }
        return null;
    }

    // Obtener todos los usuarios
    public List<Usuario> obtenerTodosUsuarios() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                usuarios.add(mapearUsuario(rs));
            }
        }
        return usuarios;
    }

    // Actualizar usuario
    public boolean actualizarUsuario(String usuarioOriginal, Usuario usuarioActualizado) throws SQLException {
        String sql = "UPDATE usuarios SET usuario = ?, nombre = ?, apellido = ?, telefono = ?, correo = ?, contraseña = ? WHERE usuario = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, usuarioActualizado.getUsuario());
            pstmt.setString(2, usuarioActualizado.getNombre());
            pstmt.setString(3, usuarioActualizado.getApellido());
            pstmt.setString(4, usuarioActualizado.getTelefono());
            pstmt.setString(5, usuarioActualizado.getCorreo());
            pstmt.setString(6, usuarioActualizado.getContrasena());
            pstmt.setString(7, usuarioOriginal);
            return pstmt.executeUpdate() > 0;
        }
    }

    // Eliminar usuario
    public boolean eliminarUsuario(String usuario) throws SQLException {
        String sql = "DELETE FROM usuarios WHERE usuario = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, usuario);
            return pstmt.executeUpdate() > 0;
        }
    }

    // Verificar si usuario existe
    public boolean existeUsuario(String usuario) throws SQLException {
        String sql = "SELECT 1 FROM usuarios WHERE usuario = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, usuario);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    // Mapear ResultSet a objeto Usuario (método privado reutilizable)
    private Usuario mapearUsuario(ResultSet rs) throws SQLException {
        return new Usuario(
                rs.getString("usuario"),
                rs.getString("nombre"),
                rs.getString("apellido"),
                rs.getString("telefono"),
                rs.getString("correo"),
                rs.getString("contraseña")
        );
    }
}