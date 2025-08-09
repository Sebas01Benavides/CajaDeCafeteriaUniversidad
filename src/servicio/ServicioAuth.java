/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicio;
import dominio.Usuario;
import infraestructura.UsuarioDAO;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
/**
 *
 * @author Sebas
 */
public class ServicioAuth {
    private final UsuarioDAO usuarioDAO;

    public ServicioAuth() {
        this.usuarioDAO = new UsuarioDAO();
    }

    /**
     * Autentica a un usuario verificando su nombre y contraseña.
     * @param username El nombre de usuario ingresado.
     * @param password La contraseña ingresada por el usuario.
     * @return Un objeto Usuario si la autenticación es exitosa, de lo contrario, null.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     * @throws NoSuchAlgorithmException Si el algoritmo de encriptación no está disponible.
     */
    public Usuario login(String username, String password) throws SQLException, NoSuchAlgorithmException {
        // 1. Busca el usuario en la base de datos usando el DAO
        Usuario usuario = usuarioDAO.buscarPorUsername(username);

        if (usuario != null) {
            // 2. Encripta la contraseña ingresada por el usuario
            String passwordHashIngresado = encriptarPassword(password);
            
            // 3. Compara el hash de la contraseña ingresada con el hash de la base de datos
            if (usuario.getPassword().equals(passwordHashIngresado)) {
                // Si coinciden, devuelve el objeto Usuario
                return usuario;
            }
        }
        
        // Si no se encuentra el usuario o las contraseñas no coinciden, devuelve null
        return null;
    }

    /**
     * Encripta una contraseña usando el algoritmo SHA-256.
     * @param password La contraseña a encriptar.
     * @return El hash de la contraseña en formato hexadecimal.
     * @throws NoSuchAlgorithmException Si el algoritmo SHA-256 no está disponible.
     */
    private String encriptarPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes());
        byte[] bytes = md.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}