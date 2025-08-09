package dominio;
import java.time.LocalDateTime;
import java.util.List;
/**
 *
 * @author Sebas
 */
public class Venta {
    private int id;
    private int userId; // ID del usuario que realizó la venta
    private LocalDateTime fechaHora; // Fecha y hora de la venta
    private List<DetalleVenta> detalles; // Lista de productos vendidos
    private double subtotal;
    private double impuestoIVA; // Impuesto de Valor Agregado
    private double impuestoIVI; // Impuesto de Valor Incluido (si aplica, según tu diseño)
    private double descuento;
    private double total;

    // Constructor completo para crear una Venta desde la base de datos
    public Venta(int id, int userId, LocalDateTime fechaHora, double subtotal, double impuestoIVA, double impuestoIVI, double descuento, double total) {
        this.id = id;
        this.userId = userId;
        this.fechaHora = fechaHora;
        this.subtotal = subtotal;
        this.impuestoIVA = impuestoIVA;
        this.impuestoIVI = impuestoIVI;
        this.descuento = descuento;
        this.total = total;
        // Los detalles no se cargan aquí, se cargan por separado con VentaDAO
        this.detalles = null; 
    }

    // Constructor para crear una nueva Venta desde la aplicación (con detalles)
    // Este constructor es el que se usa en RealizarVentaUI
    public Venta(int userId, LocalDateTime fechaHora, List<DetalleVenta> detalles) { 
        this.userId = userId;
        this.fechaHora = fechaHora;
        this.detalles = detalles;
        // Los demás campos (subtotal, impuestos, descuento, total) se calculan en el servicio
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public List<DetalleVenta> getDetalles() {
        return detalles;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public double getImpuestoIVA() {
        return impuestoIVA;
    }

    public double getImpuestoIVI() {
        return impuestoIVI;
    }

    public double getDescuento() {
        return descuento;
    }

    public double getTotal() {
        return total;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public void setDetalles(List<DetalleVenta> detalles) {
        this.detalles = detalles;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public void setImpuestoIVA(double impuestoIVA) {
        this.impuestoIVA = impuestoIVA;
    }

    public void setImpuestoIVI(double impuestoIVI) {
        this.impuestoIVI = impuestoIVI;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}