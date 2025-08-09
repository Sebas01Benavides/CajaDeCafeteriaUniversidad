package infraestructura;
import dominio.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
/**
 *
 * @author Sebas
 */


public class UsuarioDAO {
    
    // Método para buscar un usuario por su nombre de usuario
    public Usuario buscarPorUsername(String username) throws SQLException {
        String sql = "SELECT id, username, password, rol, activo, creado FROM USUARIOS WHERE username = ? AND activo = true";
        
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setUsername(rs.getString("username"));
                usuario.setPassword(rs.getString("password")); // Aquí se obtiene el hash
                usuario.setRol(rs.getString("rol"));
                usuario.setActivo(rs.getBoolean("activo"));
                usuario.setCreado(rs.getDate("creado"));
                return usuario;
            }
        }
        return null;
    }

    // Método para guardar un nuevo usuario
    public void guardar(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO USUARIOS (username, password, rol, activo, creado) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, usuario.getUsername());
            pstmt.setString(2, usuario.getPassword());
            pstmt.setString(3, usuario.getRol());
            pstmt.setBoolean(4, usuario.isActivo());
            pstmt.setTimestamp(5, new java.sql.Timestamp(usuario.getCreado().getTime()));
            
            pstmt.executeUpdate();
        }
    }
}
