/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package infraestructura;
import dominio.DetalleVenta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
/**
 *
 * @author Sebas
 */
public class DetalleVentaDAO {
    
    public void guardarDetalles(int ventaId, List<DetalleVenta> detalles) throws SQLException {
        String sql = "INSERT INTO DETALLES_VENTA (venta_id, product_id, cantidad, precio_unit, total_linea) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            for (DetalleVenta detalle : detalles) {
                pstmt.setInt(1, ventaId);
                pstmt.setInt(2, detalle.getProducto().getId());
                pstmt.setInt(3, detalle.getCantidad());
                pstmt.setDouble(4, detalle.getPrecioUnitario());
                pstmt.setDouble(5, detalle.getTotalLinea());
                
                pstmt.addBatch(); // Agrega la sentencia al lote
            }
            pstmt.executeBatch(); // Ejecuta todas las sentencias del lote
        }
    }
}
