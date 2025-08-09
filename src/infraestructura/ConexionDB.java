/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver"; // O el driver de tu BD (ej. org.postgresql.Driver)
    private static final String URL = "jdbc:mysql://localhost:3306/nombre_de_tu_bd";
    private static final String USER = "tu_usuario";
    private static final String PASSWORD = "tu_contraseña";

    // Método estático para obtener una conexión a la base de datos
    public static Connection obtenerConexion() throws SQLException {
        // La práctica sugiere manejar los errores con try-catch.
        // Aquí se puede envolver la llamada al driver para manejar errores.
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("No se encontró el driver de la base de datos.");
            // Aquí podrías loggear el error en la tabla LOGS, como pide la práctica[cite: 36].
            throw new SQLException("Driver de BD no encontrado.", e);
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
