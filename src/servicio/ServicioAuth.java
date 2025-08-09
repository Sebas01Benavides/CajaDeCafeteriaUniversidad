package servicio;
import dominio.Usuario;
import infraestructura.UsuarioDAO;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.Base64; // Para codificar el salt a String
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
     * Intenta autenticar a un usuario con el nombre de usuario y la contraseña proporcionados.
     * Genera un hash de la contraseña ingresada con el salt almacenado y lo compara con el hash guardado
     *
     * @param username El nombre de usuario
     * @param password La contraseña en texto plano
     * @return El objeto Usuario si la autenticación es exitosa, null en caso contrario
     * @throws SQLException Si ocurre un error al acceder a la base de datos
     * @throws NoSuchAlgorithmException Si el algoritmo de hash (SHA-256) no está disponible
     */
    public Usuario login(String username, String password) throws SQLException, NoSuchAlgorithmException {
        Usuario usuario = usuarioDAO.buscarPorUsername(username);

        if (usuario == null) {
            // Usuario no encontrado
            return null;
        }

        // Recuperar el salt del usuario desde la base de datos
        String salt = usuario.getSalt();
        
        // Hashear la contraseña proporcionada por el usuario con el salt recuperado )
        String hashedPassword = this.hashPassword(password, salt); 

        // Comparar el hash generado con el hash almacenado en la base de datos
        if (hashedPassword.equals(usuario.getPasswordHash())) {
            return usuario; // Autenticación exitosa
        } else {
            return null; // Contraseña incorrecta
        }
    }

    /**
     * Registra un nuevo usuario en el sistema.
     * Genera un salt y un hash para la contraseña antes de guardarlo.
     *
     * @param username El nombre de usuario
     * @param password La contraseña en texto plano
     * @param rol El rol del usuario (ej. "empleado", "admin")
     * @return El objeto Usuario creado.
     * @throws SQLException Si ocurre un error al acceder a la base de datos
     * @throws NoSuchAlgorithmException Si el algoritmo de hash (SHA-256) no está disponible
     * @throws IllegalArgumentException Si el nombre de usuario ya exist
     */
    public Usuario registrarUsuario(String username, String password, String rol) throws SQLException, NoSuchAlgorithmException, IllegalArgumentException {
        // Verificar si el usuario ya existe antes de intentar registrar
        if (usuarioDAO.buscarPorUsername(username) != null) {
            throw new IllegalArgumentException("El nombre de usuario '" + username + "' ya está en uso.");
        }

        // Generar un salt aleatorio (llamada interna)
        String salt = this.generateSalt();
        
        // Hashear la contraseña con el salt generado 
        String hashedPassword = this.hashPassword(password, salt); 
        
        // Crear el objeto Usuario con los hashes y salt
        Usuario nuevoUsuario = new Usuario(username, hashedPassword, salt, rol);
        
        // Guardar el usuario en la base de datos
        usuarioDAO.guardar(nuevoUsuario);
        
        return nuevoUsuario;
    }

    /**
     * Genera un salt aleatorio de 16 bytes
     * @return El salt codificado en Base64 como String
     * @throws NoSuchAlgorithmException Si el generador de números aleatorios no está disponible
     */
    private String generateSalt() throws NoSuchAlgorithmException { 
        SecureRandom random = SecureRandom.getInstanceStrong();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt); // Codifica el salt a Base64 para almacenarlo como String
    }

    /**
     * Hashea una contraseña usando SHA-256 con un salt
     * @param password La contraseña en texto plano
     * @param salt La cadena salt (en formato Base64)
     * @return El hash de la contraseña codificado en hexadecimal
     * @throws NoSuchAlgorithmException Si el algoritmo SHA-256 no está disponible
     */
    private String hashPassword(String password, String salt) throws NoSuchAlgorithmException { 
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(Base64.getDecoder().decode(salt)); // Decodifica el salt de Base64
        byte[] hashedPassword = md.digest(password.getBytes());
        
        // Convierte el array de bytes a una representación hexadecimal
        StringBuilder sb = new StringBuilder();
        for (byte b : hashedPassword) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
    
    // --- TEMPORARY MAIN METHOD FOR REGISTRATION ---
    // Este método main es útil para generar el usuario admin o de prueba
    // directamente desde esta clasee después de recrear la BD.
    // En una aplicación real, este main no debería existir, es de prueba
    public static void main(String[] args) {
        ServicioAuth authService = new ServicioAuth(); // Usa la propia clase
        try {
            // Registra un usuario "admin con contraseña "testpass" y rol "admin"
            // Se pueden cambiar las credenciales para registrar otro usuario
            Usuario newUser = authService.registrarUsuario("testadmin", "testpass", "admin");
            System.out.println("Usuario '" + newUser.getUsername() + "' registrado con éxito con ID: " + newUser.getId());
            System.out.println("Por favor, usa 'admin' y 'testpass' para iniciar sesión.");
        } catch (IllegalArgumentException e) {
            System.out.println("Advertencia: " + e.getMessage());
        } catch (SQLException | NoSuchAlgorithmException e) {
            System.err.println("Error al registrar usuario: " + e.getMessage());
            e.printStackTrace();
        }
    }
}