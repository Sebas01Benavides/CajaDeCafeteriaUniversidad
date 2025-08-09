package dominio;

/**
 *
 * @author Sebas
 */
public class DetalleVenta {
    private int id;
    private int ventaId;
    private int productId;
    private int cantidad;
    private double precioUnit;
    private double totalLinea;
    
    public DetalleVenta(int id, int ventaId, int productId, int cantidad, double precioUnit, double totalLinea) {
        this.id = id;
        this.ventaId = ventaId;
        this.productId = productId;
        this.cantidad = cantidad;
        this.precioUnit = precioUnit;
        this.totalLinea = totalLinea;
    }
    
    public DetalleVenta(int productId, int cantidad, double precioUnit) {
        this.productId = productId;
        this.cantidad = cantidad;
        this.precioUnit = precioUnit;
        this.totalLinea = cantidad * precioUnit;
    }

    public int getId() {
        return id;
    }

    public int getVentaId() {
        return ventaId;
    }

    public int getProductId() {
        return productId;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getPrecioUnit() {
        return precioUnit;
    }

    public double getTotalLinea() {
        return totalLinea;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setVentaId(int ventaId) {
        this.ventaId = ventaId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setPrecioUnit(double precioUnit) {
        this.precioUnit = precioUnit;
    }

    public void setTotalLinea(double totalLinea) {
        this.totalLinea = totalLinea;
    }
    
    
}