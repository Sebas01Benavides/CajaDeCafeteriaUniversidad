package infraestructura;
import dominio.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 *
 * @author Sebas
 */
public class ProductoDAO {

    public List<Producto> listarTodos() throws SQLException {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT id, nombre_completo, precio_unitario, activo, creado FROM PRODUCTOS";
        
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Producto producto = new Producto();
                producto.setId(rs.getInt("id"));
                producto.setNombre(rs.getString("nombre_completo"));
                producto.setPrecio(rs.getDouble("precio_unitario"));
                producto.setActivo(rs.getBoolean("activo"));
                producto.setCreado(new Date(rs.getTimestamp("creado").getTime()));
                productos.add(producto);
            }
        }
        return productos;
    }

    public void guardar(Producto producto) throws SQLException {
        String sql = "INSERT INTO PRODUCTOS (nombre_completo, precio_unitario, activo, creado) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, producto.getNombre());
            pstmt.setDouble(2, producto.getPrecio());
            pstmt.setBoolean(3, producto.isActivo());
            pstmt.setTimestamp(4, new java.sql.Timestamp(producto.getCreado().getTime()));
            
            pstmt.executeUpdate();
        }
    }
    
    public void actualizar(Producto producto) throws SQLException {
        String sql = "UPDATE PRODUCTOS SET nombre_completo = ?, precio_unitario = ?, activo = ? WHERE id = ?";
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, producto.getNombre());
            pstmt.setDouble(2, producto.getPrecio());
            pstmt.setBoolean(3, producto.isActivo());
            pstmt.setInt(4, producto.getId());
            
            pstmt.executeUpdate();
        }
    }

    public void cambiarEstado(int id, boolean activo) throws SQLException {
        String sql = "UPDATE PRODUCTOS SET activo = ? WHERE id = ?";
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setBoolean(1, activo);
            pstmt.setInt(2, id);
            
            pstmt.executeUpdate();
        }
    }
}
