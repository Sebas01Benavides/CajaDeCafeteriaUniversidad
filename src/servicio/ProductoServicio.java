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
     * @throws SQLException Si ocurre un error de SQL.
     * @throws IllegalArgumentException Si la validación del producto falla.
     */
    public void crearProducto(Producto producto) throws SQLException, IllegalArgumentException {
        // Lógica de negocio, como validar que el precio sea mayor que cero
        if (producto.getPrecio() <= 0) {
            throw new IllegalArgumentException("El precio del producto debe ser positivo.");
        }
        if (producto.getStock() < 0) { // Validación de stock
            throw new IllegalArgumentException("El stock del producto no puede ser negativo.");
        }
        productoDAO.guardar(producto);
    }
    
    /**
     * Lista todos los productos disponibles.
     * @return Una lista de objetos Producto.
     * @throws SQLException Si ocurre un error de SQL.
     */
    public List<Producto> listarTodos() throws SQLException {
        return productoDAO.listarTodos();
    }
    
    /**
     * Busca productos por nombre (o parte del nombre) en la base de datos.
     * Delega la búsqueda al ProductoDAO.
     * @param nombre El nombre o parte del nombre a buscar.
     * @return Una lista de productos que coinciden con el criterio de búsqueda.
     * @throws SQLException Si ocurre un error de SQL.
     */
    public List<Producto> buscarPorNombre(String nombre) throws SQLException {
        return productoDAO.buscarPorNombre(nombre);
    }
    
    /**
     * Actualiza la información de un producto existente.
     * @param producto El objeto Producto con los datos actualizados.
     * @throws SQLException Si ocurre un error de SQL.
     * @throws IllegalArgumentException Si la validación del producto falla.
     */
    public void actualizarProducto(Producto producto) throws SQLException, IllegalArgumentException {
        if (producto.getPrecio() <= 0) {
            throw new IllegalArgumentException("El precio del producto debe ser positivo.");
        }
        if (producto.getStock() < 0) { // Validación de stock
            throw new IllegalArgumentException("El stock del producto no puede ser negativo.");
        }
        productoDAO.actualizar(producto);
    }
    
    /**
     * Cambia el estado (activo/inactivo) de un producto.
     * @param id El ID del producto.
     * @param estado El nuevo estado (true para activo, false para inactivo).
     * @throws SQLException Si ocurre un error de SQL.
     */
    public void cambiarEstado(int id, boolean estado) throws SQLException {
        productoDAO.cambiarEstado(id, estado);
    }
}