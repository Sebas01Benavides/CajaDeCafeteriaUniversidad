package servicio;
import dominio.Venta;
import dominio.DetalleVenta;
import infraestructura.VentaDAO;
import java.sql.SQLException;
import java.time.LocalDate;
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

    /**
     * Procesa una venta, calcula los totales e impuestos y la guarda en la base de datos.
     * @param venta El objeto Venta a procesar y guardar.
     * @return El ID de la venta guardada.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
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

    /**
     * Lista todas las ventas registradas para una fecha específica.
     * Delega la consulta al VentaDAO.
     * @param fecha La fecha para la que se desean listar las ventas.
     * @return Una lista de objetos Venta.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    public List<Venta> listarVentasPorFecha(LocalDate fecha) throws SQLException {
        // Asume que VentaDAO tendrá un método similar para filtrar por fecha
        return ventaDAO.listarVentasPorFecha(fecha);
    }
    public List<DetalleVenta> obtenerDetallesDeVenta(int ventaId) throws SQLException {
        // Este método simplemente delega la llamada al VentaDAO
        return ventaDAO.obtenerDetallesDeVenta(ventaId);
    }
}