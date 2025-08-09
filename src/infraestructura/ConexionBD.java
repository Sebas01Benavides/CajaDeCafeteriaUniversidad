package infraestructura;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author Sebas
 */
public class ConexionBD {
    private static ConexionBD instancia;
    private Connection conexion;

    private ConexionBD() {
        try {
            String url = "jdbc:mysql://localhost:3306/nombre_de_tu_bd";
            String usuario = "tu_usuario";
            String password = "tu_password";
            this.conexion = DriverManager.getConnection(url, usuario, password);
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static ConexionBD getInstancia() {
        if (instancia == null) {
            instancia = new ConexionBD();
        }
        return instancia;
    }

    public Connection getConexion() {
        return conexion;
    }

    public void cerrarConexion() {
        if (conexion != null) {
            try {
                conexion.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}
