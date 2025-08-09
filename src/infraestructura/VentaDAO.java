/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package infraestructura;
import dominio.Venta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import dominio.DetalleVenta;
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
     * @throws SQLException
     */
    public int guardar(Venta venta) throws SQLException {
        String sqlVenta = "INSERT INTO VENTAS (user_id, fecha_hora, subtotal, impuestoIVA, impuestoIVI, descuento, total) VALUES (?, ?, ?, ?, ?, ?, ?)";
        int ventaId = -1;

        try (PreparedStatement stmtVenta = conexion.prepareStatement(sqlVenta, Statement.RETURN_GENERATED_KEYS)) {
            stmtVenta.setInt(1, venta.getUserId());
            stmtVenta.setTimestamp(2, java.sql.Timestamp.valueOf(venta.getFechaHora()));
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
     * @throws SQLException
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
}