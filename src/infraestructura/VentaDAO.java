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
     * Guarda una venta y sus detalles en la base de datos.
     *
     * @param venta El objeto Venta a guardar.
     * @return El ID de la venta guardada.
     * @throws SQLException Si ocurre un error de SQL.
     */
    public int guardar(Venta venta) throws SQLException {
        String sqlVenta = "INSERT INTO VENTAS (user_id, fecha_hora, subtotal, impuestoIVA, impuestoIVI, descuento, total) VALUES (?, ?, ?, ?, ?, ?, ?)";
        int ventaId = -1;

        try (PreparedStatement stmtVenta = conexion.prepareStatement(sqlVenta, Statement.RETURN_GENERATED_KEYS)) {
            stmtVenta.setInt(1, venta.getUserId());
            stmtVenta.setTimestamp(2, Timestamp.valueOf(venta.getFechaHora()));
            stmtVenta.setDouble(3, venta.getSubtotal());
            stmtVenta.setDouble(4, venta.getImpuestoIVA());
            stmtVenta.setDouble(5, venta.getImpuestoIVI());
            stmtVenta.setDouble(6, venta.getDescuento());
            stmtVenta.setDouble(7, venta.getTotal());
            stmtVenta.executeUpdate();

            try (ResultSet rs = stmtVenta.getGeneratedKeys()) {
                if (rs.next()) {
                    ventaId = rs.getInt(1);
                }
            }
        }

        if (ventaId != -1) {
            guardarDetallesVenta(ventaId, venta);
        }

        return ventaId;
    }

    /**
     * Guarda los detalles de una venta en la base de datos.
     * Este es un método auxiliar que no debe ser llamado directamente desde el servicio.
     *
     * @param ventaId El ID de la venta a la que pertenecen los detalles.
     * @param venta El objeto Venta que contiene la lista de detalles.
     * @throws SQLException Si ocurre un error de SQL.
     */
    private void guardarDetallesVenta(int ventaId, Venta venta) throws SQLException {
        String sqlDetalle = "INSERT INTO DETALLES_VENTA (venta_id, product_id, cantidad, precio_unit, total_linea) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmtDetalle = conexion.prepareStatement(sqlDetalle)) {
            for (DetalleVenta detalle : venta.getDetalles()) {
                stmtDetalle.setInt(1, ventaId);
                stmtDetalle.setInt(2, detalle.getProductId());
                stmtDetalle.setInt(3, detalle.getCantidad());
                stmtDetalle.setDouble(4, detalle.getPrecioUnit());
                stmtDetalle.setDouble(5, detalle.getTotalLinea());
                stmtDetalle.addBatch();
            }
            stmtDetalle.executeBatch();
        }
    }

    /**
     * Lista todas las ventas de la base de datos para una fecha específica.
     * @param fecha La fecha para la que se desean listar las ventas.
     * @return Una lista de objetos Venta.
     * @throws SQLException Si ocurre un error de SQL.
     */
    public List<Venta> listarVentasPorFecha(LocalDate fecha) throws SQLException {
        List<Venta> ventas = new ArrayList<>();
        // Consulta SQL para seleccionar ventas en un rango de fecha específico (día completo)
        String sql = "SELECT id, user_id, fecha_hora, subtotal, impuestoIVA, impuestoIVI, descuento, total FROM VENTAS WHERE fecha_hora >= ? AND fecha_hora < ?";
        
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            // Establece la fecha de inicio (principio del día)
            stmt.setTimestamp(1, Timestamp.valueOf(fecha.atStartOfDay()));
            // Establece la fecha de fin (principio del día siguiente)
            stmt.setTimestamp(2, Timestamp.valueOf(fecha.plusDays(1).atStartOfDay()));
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Venta venta = new Venta(
                        rs.getInt("user_id"), // Constructor pide userId primero
                        new ArrayList<>() // Inicialmente una lista vacía para los detalles
                    );
                    venta.setId(rs.getInt("id"));
                    venta.setFechaHora(rs.getTimestamp("fecha_hora").toLocalDateTime());
                    venta.setSubtotal(rs.getDouble("subtotal"));
                    venta.setImpuestoIVA(rs.getDouble("impuestoIVA"));
                    venta.setImpuestoIVI(rs.getDouble("impuestoIVI"));
                    venta.setDescuento(rs.getDouble("descuento"));
                    venta.setTotal(rs.getDouble("total"));
                    // NOTA: Los detalles de la venta no se cargan automáticamente aquí.
                    // Si necesitas los detalles, deberías tener otro método para cargarlos bajo demanda.
                    ventas.add(venta);
                }
            }
        }
        return ventas;
    }

    /**
     * Obtiene los detalles de una venta específica por su ID.
     * @param ventaId El ID de la venta.
     * @return Una lista de DetalleVenta.
     * @throws SQLException Si ocurre un error de SQL.
     */
    public List<DetalleVenta> obtenerDetallesDeVenta(int ventaId) throws SQLException {
        List<DetalleVenta> detalles = new ArrayList<>();
        // Asume que también necesitarás el nombre del producto para la UI, por lo que se hace un JOIN.
        String sql = "SELECT dv.product_id, p.nombre, dv.cantidad, dv.precio_unit, dv.total_linea " +
                     "FROM DETALLES_VENTA dv " +
                     "JOIN PRODUCTOS p ON dv.product_id = p.id " +
                     "WHERE dv.venta_id = ?";
        
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, ventaId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    DetalleVenta detalle = new DetalleVenta(
                        rs.getInt("product_id"),
                        rs.getString("nombre"), // Nombre del producto desde la tabla PRODUCTOS
                        rs.getInt("cantidad"),
                        rs.getDouble("precio_unit")
                    );
                    // El total de línea ya está calculado en DetalleVenta si usas el constructor adecuado
                    // detalle.setTotalLinea(rs.getDouble("total_linea"));
                    detalles.add(detalle);
                }
            }
        }
        return detalles;
    }
}