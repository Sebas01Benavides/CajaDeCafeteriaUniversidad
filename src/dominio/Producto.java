package dominio;
import java.time.LocalDateTime;
import java.util.Date;

/**
 *
 * @author Sebas
 */
public class Producto {
    private int id;
    private String nombre;
    private double precio;
    private boolean activo;
    private LocalDateTime creado;
    private int stock; // ¡NUEVO CAMPO: Stock!

    // Constructor para crear un nuevo producto (sin ID ni creado, se generan en BD)
    public Producto(String nombre, double precio, int stock) { 
        this.nombre = nombre;
        this.precio = precio;
        this.activo = true; // Por defecto un producto nuevo está activo
        this.creado = LocalDateTime.now(); // Se asigna la fecha y hora actual
        this.stock = stock; // Asigna el stock
    }

    // Constructor para recuperar un producto de la base de datos (con todos los campos)
    public Producto(int id, String nombre, double precio, boolean activo, LocalDateTime creado, int stock) { // Constructor 
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.activo = activo;
        this.creado = creado;
        this.stock = stock; 
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public boolean isActivo() {
        return activo;
    }

    public LocalDateTime getCreado() {
        return creado;
    }
    
    public int getStock() { 
        return stock;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public void setCreado(LocalDateTime creado) {
        this.creado = creado;
    }
    
    public void setStock(int stock) { 
        this.stock = stock;
    }
}