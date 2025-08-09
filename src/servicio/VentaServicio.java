package servicio;
import dominio.Venta;
import dominio.DetalleVenta;
import infraestructura.VentaDAO;
import java.sql.SQLException;
import java.util.List;
/**
 *
 * @author Sebas
 */
public class VentaServicio {
    private final VentaDAO ventaDAO;
    // Se definen las constantes según el documento
    private static final double IMPUESTO_IVA = 0.07; // 7%
    private static final double IMPUESTO_IVI = 0.13; // 13%

    public VentaServicio() {
        this.ventaDAO = new VentaDAO();
    }
    public int procesarVenta(Venta venta) throws SQLException {
        double subtotal = 0;
        for (DetalleVenta detalle : venta.getDetalles()) {
            subtotal += detalle.getTotalLinea();
        }
        venta.setSubtotal(subtotal);

        // Se calculan ambos impuestos por separado
        double impuestoIVA = subtotal * IMPUESTO_IVA;
        double impuestoIVI = subtotal * IMPUESTO_IVI;
        
        venta.setImpuestoIVA(impuestoIVA);
        venta.setImpuestoIVI(impuestoIVI);
        
        // El descuento se deja en 0 por defecto, pero se puede modificar
        venta.setDescuento(0);

        // El total es la suma del subtotal y ambos impuestos, menos el descuento
        double total = subtotal + impuestoIVA + impuestoIVI - venta.getDescuento();
        venta.setTotal(total);

        return ventaDAO.guardar(venta);
    }
}