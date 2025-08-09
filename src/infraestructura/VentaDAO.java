/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package infraestructura;
import dominio.Venta;
import dominio.DetalleVenta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp; // Importar Timestamp
import java.time.LocalDate; // Importar LocalDate
import java.time.LocalDateTime; // Importar LocalDateTime
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Sebas
 */
public class VentaDAO {

    private final Connection conexion;

    public VentaDAO() {
        this.conexion = ConexionBD.getInstancia().getConexion();
    }

    /**
     * Guarda una nueva venta y sus detalles en la base de datos.
     * La venta principal se guarda primero, y luego se insertan sus detalles.
     *
     * @param venta El objeto Venta a guardar.
     * @return El ID de la venta generada por la base de datos.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    public int guardar(Venta venta) throws SQLException {
        String sqlVenta = "INSERT INTO ventas (user_id, fecha_hora, subtotal, impuesto_iva, impuesto_ivi, descuento, total) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String sqlDetalle = "INSERT INTO detalle_venta (venta_id, producto_id, cantidad, precio_unitario, total_linea, nombre_producto) VALUES (?, ?, ?, ?, ?, ?)";
        int ventaId = -1;

        // Inicia una transacción para asegurar la atomicidad de la operación
        conexion.setAutoCommit(false); // Deshabilita el auto-commit

        try (PreparedStatement stmtVenta = conexion.prepareStatement(sqlVenta, Statement.RETURN_GENERATED_KEYS)) {
            stmtVenta.setInt(1, venta.getUserId());
            stmtVenta.setTimestamp(2, Timestamp.valueOf(venta.getFechaHora()));
            stmtVenta.setDouble(3, venta.getSubtotal());
            stmtVenta.setDouble(4, venta.getImpuestoIVA());
            stmtVenta.setDouble(5, venta.getImpuestoIVI());
            stmtVenta.setDouble(6, venta.getDescuento());
            stmtVenta.setDouble(7, venta.getTotal());

            int affectedRows = stmtVenta.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("La creación de la venta falló, no se obtuvieron filas afectadas.");
            }

            try (ResultSet generatedKeys = stmtVenta.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    ventaId = generatedKeys.getInt(1);
                    venta.setId(ventaId); // Asigna el ID generado al objeto Venta
                } else {
                    throw new SQLException("La creación de la venta falló, no se obtuvo ID generado.");
                }
            }

            // Guardar detalles de la venta
            try (PreparedStatement stmtDetalle = conexion.prepareStatement(sqlDetalle)) {
                for (DetalleVenta detalle : venta.getDetalles()) {
                    stmtDetalle.setInt(1, ventaId);
                    stmtDetalle.setInt(2, detalle.getProductId());
                    stmtDetalle.setInt(3, detalle.getCantidad());
                    stmtDetalle.setDouble(4, detalle.getPrecioUnit());
                    stmtDetalle.setDouble(5, detalle.getTotalLinea());
                    stmtDetalle.setString(6, detalle.getNombreProducto()); // Guardar nombre del producto
                    stmtDetalle.addBatch(); // Agrega la sentencia al batch
                }
                stmtDetalle.executeBatch(); // Ejecuta todas las sentencias del batch
            }

            conexion.commit(); // Confirma la transacción
        } catch (SQLException e) {
            conexion.rollback(); // Deshace la transacción en caso de error
            throw e; // Re-lanza la excepción
        } finally {
            conexion.setAutoCommit(true); // Restablece el auto-commit
        }
        return ventaId;
    }

    /**
     * Lista todas las ventas registradas para una fecha específica.
     *
     * @param fecha La fecha para la que se desean listar las ventas.
     * @return Una lista de objetos Venta.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    public List<Venta> listarVentasPorFecha(LocalDate fecha) throws SQLException {
        List<Venta> ventas = new ArrayList<>();
        String sql = "SELECT id, user_id, fecha_hora, subtotal, impuesto_iva, impuesto_ivi, descuento, total FROM ventas WHERE DATE(fecha_hora) = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setDate(1, java.sql.Date.valueOf(fecha)); // Convierte LocalDate a java.sql.Date
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Venta venta = new Venta(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getTimestamp("fecha_hora").toLocalDateTime(), // Convierte Timestamp a LocalDateTime
                        rs.getDouble("subtotal"),
                        rs.getDouble("impuesto_iva"),
                        rs.getDouble("impuesto_ivi"),
                        rs.getDouble("descuento"),
                        rs.getDouble("total")
                    );
                    ventas.add(venta);
                }
            }
        }
        return ventas;
    }

    /**
     * Obtiene los detalles de una venta específica por su ID.
     *
     * @param ventaId El ID de la venta.
     * @return Una lista de objetos DetalleVenta.
     * @throws SQLException Si ocurre un error de SQL.
     */
    public List<DetalleVenta> obtenerDetallesDeVenta(int ventaId) throws SQLException {
        List<DetalleVenta> detalles = new ArrayList<>();
        String sql = "SELECT producto_id, cantidad, precio_unitario, total_linea, nombre_producto FROM detalle_venta WHERE venta_id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, ventaId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    DetalleVenta detalle = new DetalleVenta(
                        rs.getInt("producto_id"),
                        rs.getString("nombre_producto"), // Asume que el nombre del producto se guarda en detalle_venta
                        rs.getInt("cantidad"),
                        rs.getDouble("precio_unitario")
                        // total_linea se calcula en el constructor o se obtiene de la BD si ya está allí
                    );
                    detalle.setTotalLinea(rs.getDouble("total_linea")); 
                    detalles.add(detalle);
                }
            }
        }
        return detalles;
    }

    /**
     * Actualiza el stock de un producto después de una venta.
     *
     * @param productId El ID del producto.
     * @param quantitySold La cantidad vendida (restar del stock).
     * @throws SQLException Si ocurre un error de SQL.
     */
    public void actualizarStockProducto(int productId, int quantitySold) throws SQLException {
        String sql = "UPDATE productos SET stock = stock - ? WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, quantitySold);
            stmt.setInt(2, productId);
            stmt.executeUpdate();
        }
    }
    
    // Método adicional para buscar un producto por ID para obtener su nombre,
    public String getNombreProductoById(int productId) throws SQLException {
        String productName = null;
        String sql = "SELECT nombre FROM productos WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    productName = rs.getString("nombre");
                }
            }
        }
        return productName;
    }
}