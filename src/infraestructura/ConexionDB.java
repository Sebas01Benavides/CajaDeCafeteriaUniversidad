package infraestructura;

/**
 *
 * @author Sebas
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    // Configuración de la conexión a la base de datos
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver"; 
    private static final String URL = "jdbc:mysql://localhost:3306/nombre_de_tu_bd";
    private static final String USER = "postgres";
    private static final String PASSWORD = "admin";

    // Método estático para obtener una conexión a la base de datos
    public static Connection obtenerConexion() throws SQLException {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("No se encontró el driver de la base de datos");
            throw new SQLException("Driver de BD no encontrado.", e);
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
