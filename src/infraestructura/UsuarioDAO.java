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
     * Busca un usuario en la base de datos por su nombre de usuario.
     *
     * @param username El nombre de usuario a buscar.
     * @return Un objeto Usuario si se encuentra, de lo contrario, null.
     * @throws SQLException
     */
    public Usuario buscarPorUsername(String username) throws SQLException {
        String sql = "SELECT id, username, password FROM USUARIOS WHERE username = ? AND activo = 1";
        
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String passwordHash = rs.getString("password");
                    return new Usuario(id, username, passwordHash);
                }
            }
        }
        return null;
    }
}