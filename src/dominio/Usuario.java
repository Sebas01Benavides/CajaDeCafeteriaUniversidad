/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominio;
import java.time.LocalDateTime;
import java.util.Date;

/**
 *
 * @author Sebas
 */

public class Usuario {
    private int id;
    private String username;
    private String passwordHash; // Campo para almacenar el hash de la contraseña
    private String salt;         // Campo para almacenar el salt
    private String rol;          // Campo para almacenar el rol del usuario (ej. "admin", "empleado")
    private boolean activo;      // Campo para indicar si el usuario está activo
    private LocalDateTime creado; // Campo para la fecha de creación

    // Constructor para un nuevo usuario (sin ID, activo por defecto, fecha de creación actual)
    // Usado al registrar un nuevo usuario desde la aplicación
    public Usuario(String username, String passwordHash, String salt, String rol) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.salt = salt;
        this.rol = rol;
        this.activo = true; // Por defecto un nuevo usuario está activo
        this.creado = LocalDateTime.now(); // Se asigna la fecha y hora actual
    }

    // Constructor para recuperar un usuario de la base de datos (con todos los campos)
    public Usuario(int id, String username, String passwordHash, String salt, String rol, boolean activo, LocalDateTime creado) {
        this.id = id;
        this.username = username;
        this.passwordHash = passwordHash;
        this.salt = salt;
        this.rol = rol;
        this.activo = activo;
        this.creado = creado;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() { 
        return passwordHash;
    }

    public String getSalt() { 
        return salt;
    }

    public String getRol() { 
        return rol;
    }

    public boolean isActivo() { 
        return activo;
    }

    public LocalDateTime getCreado() { 
        return creado;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPasswordHash(String passwordHash) { 
        this.passwordHash = passwordHash;
    }

    public void setSalt(String salt) { 
        this.salt = salt;
    }

    public void setRol(String rol) { 
        this.rol = rol;
    }

    public void setActivo(boolean activo) { 
        this.activo = activo;
    }

    public void setCreado(LocalDateTime creado) {
        this.creado = creado;
    }
}