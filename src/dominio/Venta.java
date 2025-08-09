package dominio;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
/**
 *
 * @author Sebas
 */
public class Venta {
    private int id;
    private int userId;
    private LocalDateTime fechaHora;
    private double subtotal;
    private double impuestoIVA;
    private double impuestoIVI;
    private double descuento;
    private double total;
    private List<DetalleVenta> detalles;

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
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

    public List<DetalleVenta> getDetalles() {
        return detalles;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
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

    public void setDetalles(List<DetalleVenta> detalles) {
        this.detalles = detalles;
    }

    
}