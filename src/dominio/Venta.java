package dominio;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
/**
 *
 * @author Sebas
 */
public class Venta {
    private int id;
    private Usuario usuario; // Relación con la clase Usuario
    private Date fechaHora;
    private double subtotal;
    private double impuestoIVA; // IVA del 7%
    private double impuestoIVI; // IVI del 13%
    private double descuento;
    private double total;
    
    // Lista para almacenar los ítems de la venta (relación con DetalleVenta)
    private List<DetalleVenta> detalles;

    public Venta(Usuario usuario) {
        this.usuario = usuario;
        this.fechaHora = new Date();
        this.detalles = new ArrayList<>();
    }

    public void agregarDetalle(DetalleVenta detalle) {
        this.detalles.add(detalle);
        // Lógica para recalcular el total de la venta al agregar un detalle
        recalcularTotales();
    }
    
    public void recalcularTotales() {
        this.subtotal = 0;
        for (DetalleVenta detalle : this.detalles) {
            this.subtotal += detalle.getTotalLinea();
        }

        // Aplicar impuestos
        this.impuestoIVA = this.subtotal * 0.07;
        this.impuestoIVI = this.subtotal * 0.13;

        // Calcular el total
        this.total = this.subtotal + this.impuestoIVA + this.impuestoIVI - this.descuento;
    }

    public int getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Date getFechaHora() {
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

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setFechaHora(Date fechaHora) {
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