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
     * @throws SQLException Si ocurre un error de SQL.
     */
    public void guardar(Producto producto) throws SQLException {
        // La consulta SQL inserta un nuevo registro en la tabla PRODUCTOS
        // Se añade "stock" a la lista de columnas y a los valores a insertar.
        String sql = "INSERT INTO PRODUCTOS (nombre, precio_unitario, activo, creado, stock) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, producto.getNombre());
            stmt.setDouble(2, producto.getPrecio());
            stmt.setBoolean(3, producto.isActivo());
            // Convierte LocalDateTime a Timestamp para la base de datos
            stmt.setTimestamp(4, Timestamp.valueOf(producto.getCreado()));
            stmt.setInt(5, producto.getStock()); // Establece el valor del stock
            stmt.executeUpdate();
        }
    }

    /**
     * Devuelve una lista de todos los productos de la base de datos.
     * @return Una lista de objetos Producto.
     * @throws SQLException Si ocurre un error de SQL.
     */
    public List<Producto> listarTodos() throws SQLException {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT id, nombre, precio_unitario, activo, creado, stock FROM PRODUCTOS";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Producto producto = new Producto(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getDouble("precio_unitario"),
                    rs.getBoolean("activo"),
                    // Convertir Timestamp a LocalDateTime
                    rs.getTimestamp("creado").toLocalDateTime(),
                    rs.getInt("stock") //  Obtiene la cantidad del stock
                );
                productos.add(producto);
            }
        }
        return productos;
    }
    
    /**
     * Busca productos por nombre (o parte del nombre) en la base de datos.
     * @param nombre El nombre o parte del nombre a buscar.
     * @return Una lista de productos que coinciden con el criterio de búsqueda.
     * @throws SQLException Si ocurre un error de SQL.
     */
    public List<Producto> buscarPorNombre(String nombre) throws SQLException {
        List<Producto> productos = new ArrayList<>();
        // Se usa LIKE para búsquedas parciales y CONCAT para añadir comodines
        String sql = "SELECT id, nombre, precio_unitario, activo, creado, stock FROM PRODUCTOS WHERE nombre LIKE ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, "%" + nombre + "%"); // Envuelve el nombre con comodines %
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Producto producto = new Producto(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getDouble("precio_unitario"),
                        rs.getBoolean("activo"),
                        rs.getTimestamp("creado").toLocalDateTime(),
                        rs.getInt("stock")
                    );
                    productos.add(producto);
                }
            }
        }
        return productos;
    }

    /**
     * Actualiza la información de un producto existente.
     * @param producto El objeto Producto con los datos actualizados.
     * @throws SQLException Si ocurre un error de SQL.
     */
    public void actualizar(Producto producto) throws SQLException {
        // Se añade 'stock' a la sentencia UPDATE
        String sql = "UPDATE PRODUCTOS SET nombre = ?, precio_unitario = ?, activo = ?, stock = ? WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, producto.getNombre());
            stmt.setDouble(2, producto.getPrecio());
            stmt.setBoolean(3, producto.isActivo());
            stmt.setInt(4, producto.getStock()); // Actualiza el valor del stock
            stmt.setInt(5, producto.getId());
            stmt.executeUpdate();
        }
    }

    /**
     * Cambia el estado (activo/inactivo) de un producto por su ID.
     * @param id El ID del producto.
     * @param estado El nuevo estado (true para activo, false para inactivo).
     * @throws SQLException Si ocurre un error de SQL.
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