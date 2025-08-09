package dominio;

/**
 *
 * @author Sebas
 */
public class DetalleVenta {
    private int productId;
    private String nombreProducto; // Necesario para mostrarlo en la UI
    private int cantidad;
    private double precioUnit;
    private double totalLinea;

    // Constructor para la interfaz
    public DetalleVenta(int productId, String nombreProducto, int cantidad, double precioUnit) {
        this.productId = productId;
        this.nombreProducto = nombreProducto;
        this.cantidad = cantidad;
        this.precioUnit = precioUnit;
        this.totalLinea = cantidad * precioUnit;
    }

    public int getProductId() {
        return productId;
    }

    public String getNombreProducto() {
        return nombreProducto;
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

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
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