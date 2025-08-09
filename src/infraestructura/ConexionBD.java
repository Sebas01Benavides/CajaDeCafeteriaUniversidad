package infraestructura;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;
/**
 *
 * @author Sebas
 */
public class ConexionBD {

    private static ConexionBD instancia;
    private Connection conexion;

    // Configuración para PostgreSQL
    private static final String DRIVER = "org.postgresql.Driver";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/bdcafeteria"; 
    private static final String USER = "postgres"; 
    private static final String PASS = "admin"; 

    /**
     * Constructor privado para el patrón Singleton.
     * Establece la conexión a la base de datos.
     */
    private ConexionBD() {
        try {
            Class.forName(DRIVER);
            this.conexion = DriverManager.getConnection(DB_URL, USER, PASS);
            JOptionPane.showMessageDialog(null, "Conexión a la base de datos exitosa.", "Conexión", JOptionPane.INFORMATION_MESSAGE);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Error: No se encontró el driver JDBC de PostgreSQL. Asegúrate de que el JAR esté en el classpath.", "Error de Driver", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al conectar a la base de datos: " + ex.getMessage(), "Error de Conexión", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    /**
     * Obtiene la única instancia de la clase ConexionBD.
     * @return La instancia de ConexionBD.
     */
    public static ConexionBD getInstancia() {
        if (instancia == null) {
            instancia = new ConexionBD();
        }
        return instancia;
    }

    /**
     * Obtiene la conexión a la base de datos.
     * @return El objeto Connection.
     */
    public Connection getConexion() {
        return conexion;
    }

    /**
     * Cierra la conexión a la base de datos.
     */
    public void cerrarConexion() {
        if (conexion != null) {
            try {
                conexion.close();
                instancia = null; // Resetear la instancia para permitir una nueva conexión si es necesario
                // JOptionPane.showMessageDialog(null, "Conexión a la base de datos cerrada.", "Conexión", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al cerrar la conexión: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}