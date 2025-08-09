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
    private UsuarioDAO usuarioDAO;
    public ServicioAuth() {
        this.usuarioDAO = new UsuarioDAO();
    }
    /**
     * Valida las credenciales de un usuario.
     * @param username El nombre de usuario.
     * @param password La contraseña en texto plano.
     * @return El objeto Usuario si las credenciales son correctas, o null si fallan.
     */
    public Usuario login(String username, String password) throws SQLException, NoSuchAlgorithmException {
        Usuario usuarioDB = usuarioDAO.buscarPorUsername(username);

        // Si el usuario no existe o está inactivo
        if (usuarioDB == null || !usuarioDB.isActivo()) {
            return null;
        }

        // Encriptar la contraseña proporcionada y compararla con el hash almacenado en la BD
        String passwordHash = generarHashSHA256(password);
        if (passwordHash.equals(usuarioDB.getPassword())) {
            // Aquí se podría registrar un suceso de login exitoso en la tabla LOGS
            return usuarioDB;
        } else {
            // Aquí se podría registrar un suceso de login fallido en la tabla LOGS
            return null;
        }
    }
    
    /**
     * Genera un hash SHA-256 para una cadena de texto.
     * @param texto El texto a encriptar.
     * @return El hash SHA-256 en formato String.
     */
    private String generarHashSHA256(String texto) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(texto.getBytes());
        byte[] digest = md.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
