/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominio;

/**
 *
 * @author Sebas
 */
public class DetalleVenta {
    private int id;
    private Venta venta; // Relación con la clase Venta
    private Producto producto; // Relación con la clase Producto
    private int cantidad;
    private double precioUnitario;
    private double totalLinea;

    public DetalleVenta(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = producto.getPrecio();
        this.totalLinea = this.precioUnitario * this.cantidad;
    }

    public int getId() {
        return id;
    }

    public Venta getVenta() {
        return venta;
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public double getTotalLinea() {
        return totalLinea;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public void setTotalLinea(double totalLinea) {
        this.totalLinea = totalLinea;
    }
    
}