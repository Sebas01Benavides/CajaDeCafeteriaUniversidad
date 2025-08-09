package infraestructura;
import dominio.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Sebas
 */
public class ProductoDAO {
    private final Connection conexion;

    public ProductoDAO() {
        // Obtenemos la instancia de la conexión a la base de datos
        this.conexion = ConexionBD.getInstancia().getConexion();
    }

    /**
     * Guarda un nuevo producto en la tabla PRODUCTOS.
     * @param producto El objeto Producto a guardar.
     * @throws SQLException
     */
    public void guardar(Producto producto) throws SQLException {
        // La consulta SQL inserta un nuevo registro en la tabla PRODUCTOS
        String sql = "INSERT INTO PRODUCTOS (nombre, precio_unitario, activo) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, producto.getNombre());
            stmt.setDouble(2, producto.getPrecio());
            stmt.setBoolean(3, producto.isActivo());
            stmt.executeUpdate();
        }
    }

    /**
     * Devuelve una lista de todos los productos de la base de datos.
     * @return Una lista de objetos Producto.
     * @throws SQLException
     */
    public List<Producto> listarTodos() throws SQLException {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM PRODUCTOS";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Producto producto = new Producto(
                    rs.getInt("id"),
                    rs.getString("nombre"), // Nombre de la columna en la BD
                    rs.getDouble("precio_unitario"), // Nombre de la columna en la BD
                    rs.getBoolean("activo"),
                    rs.getTimestamp("creado").toLocalDateTime()
                );
                productos.add(producto);
            }
        }
        return productos;
    }

    /**
     * Actualiza la información de un producto existente.
     * @param producto El objeto Producto con los datos actualizados.
     * @throws SQLException
     */
    public void actualizar(Producto producto) throws SQLException {
        String sql = "UPDATE PRODUCTOS SET nombre = ?, precio_unitario = ? WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, producto.getNombre());
            stmt.setDouble(2, producto.getPrecio());
            stmt.setInt(3, producto.getId());
            stmt.executeUpdate();
        }
    }

    /**
     * Cambia el estado (activo/inactivo) de un producto por su ID.
     * @param id El ID del producto.
     * @param estado El nuevo estado (true para activo, false para inactivo).
     * @throws SQLException
     */
    public void cambiarEstado(int id, boolean estado) throws SQLException {
        String sql = "UPDATE PRODUCTOS SET activo = ? WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setBoolean(1, estado);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        }
    }
}