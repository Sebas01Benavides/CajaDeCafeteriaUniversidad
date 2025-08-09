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
    private final Connection conexion;

    public UsuarioDAO() {
        this.conexion = ConexionBD.getInstancia().getConexion();
    }

    /**
     * Busca un usuario por su nombre de usuario.
     * @param username El nombre de usuario a buscar.
     * @return El objeto Usuario si se encuentra, o null si no existe.
     * @throws SQLException Si ocurre un error de SQL.
     */
    public Usuario buscarPorUsername(String username) throws SQLException {
        String sql = "SELECT id, username, password_hash, salt, rol, activo, creado FROM USUARIOS WHERE username = ?";
        Usuario usuario = null;
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    usuario = new Usuario(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password_hash"),
                        rs.getString("salt"),
                        rs.getString("rol"),
                        rs.getBoolean("activo"),
                        rs.getTimestamp("creado").toLocalDateTime()
                    );
                }
            }
        }
        return usuario;
    }

    /**
     * Guarda un nuevo usuario en la base de datos.
     * @param usuario El objeto Usuario a guardar.
     * @throws SQLException Si ocurre un error de SQL.
     */
    public void guardar(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO USUARIOS (username, password_hash, salt, rol, activo, creado) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, usuario.getUsername());
            stmt.setString(2, usuario.getPasswordHash());
            stmt.setString(3, usuario.getSalt());
            stmt.setString(4, usuario.getRol());
            stmt.setBoolean(5, usuario.isActivo());
            stmt.setTimestamp(6, java.sql.Timestamp.valueOf(usuario.getCreado()));
            stmt.executeUpdate();
        }
    }
} 