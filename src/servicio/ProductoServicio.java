/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicio;
import dominio.Producto;
import infraestructura.ProductoDAO;
import java.sql.SQLException;
import java.util.List;
/**
 *
 * @author Sebas
 */
public class ProductoServicio {
    private ProductoDAO productoDAO;
    public ProductoServicio() {
        this.productoDAO = new ProductoDAO();
    }
    
    /**
     * Guarda un nuevo producto en la base de datos.
     * @param producto El objeto Producto a guardar.
     */
    public void crearProducto(Producto producto) throws SQLException {
        // Lógica de negocio, como validar que el precio sea mayor que cero
        if (producto.getPrecio() <= 0) {
            throw new IllegalArgumentException("El precio del producto debe ser positivo.");
        }
        productoDAO.guardar(producto);
    }
    
    /**
     * Lista todos los productos disponibles.
     * @return Una lista de objetos Producto.
     */
    public List<Producto> listarTodos() throws SQLException {
        return productoDAO.listarTodos();
    }
    
    /**
     * Actualiza la información de un producto existente.
     * @param producto El objeto Producto con los datos actualizados.
     */
    public void actualizarProducto(Producto producto) throws SQLException {
        // Lógica de validación, si es necesaria
        productoDAO.actualizar(producto);
    }
    
    /**
     * Cambia el estado (activo/inactivo) de un producto.
     * @param id El ID del producto.
     * @param estado El nuevo estado (true para activo, false para inactivo).
     */
    public void cambiarEstado(int id, boolean estado) throws SQLException {
        productoDAO.cambiarEstado(id, estado);
    }
}
