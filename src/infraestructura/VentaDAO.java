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
/**
 *
 * @author Sebas
 */
public class VentaDAO {

    public void guardarVenta(Venta venta) throws SQLException {
        String sqlVenta = "INSERT INTO VENTAS (user_id, fecha_hora, subtotal, impuestoIVA, impuestoIVI, descuento, total) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement pstmtVenta = conn.prepareStatement(sqlVenta, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmtVenta.setInt(1, venta.getUsuario().getId());
            pstmtVenta.setTimestamp(2, new java.sql.Timestamp(venta.getFechaHora().getTime()));
            pstmtVenta.setDouble(3, venta.getSubtotal());
            pstmtVenta.setDouble(4, venta.getImpuestoIVA());
            pstmtVenta.setDouble(5, venta.getImpuestoIVI());
            pstmtVenta.setDouble(6, venta.getDescuento());
            pstmtVenta.setDouble(7, venta.getTotal());
            
            pstmtVenta.executeUpdate();
            
            // Obtener el ID generado para la Venta
            try (ResultSet generatedKeys = pstmtVenta.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    venta.setId(generatedKeys.getInt(1));
                    // Guardar los detalles de la venta usando el ID generado
                    DetalleVentaDAO detalleDAO = new DetalleVentaDAO();
                    detalleDAO.guardarDetalles(venta.getId(), venta.getDetalles());
                }
            }
        }
    }
}
    

