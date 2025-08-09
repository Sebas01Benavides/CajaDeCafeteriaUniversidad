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

    public Producto(String nombre, double precio) {
        this.nombre = nombre;
        this.precio = precio;
        this.activo = true;
    }

    public Producto(int id, String nombre, double precio, boolean activo, LocalDateTime creado) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.activo = activo;
        this.creado = creado;
    }

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
    
    
}