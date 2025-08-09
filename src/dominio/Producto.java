/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominio;

import java.util.Date;

/**
 *
 * @author Sebas
 */
public class Producto {
     private int id;
     private String nombre;
     private String descripcion;
     private boolean activo;
     private double precio;
     private Date creado;

    public Producto(String nombre, String descripcion, boolean activo, double precio, Date creado) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.activo = activo;
        this.precio = precio;
        this.creado = creado;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public boolean isActivo() {
        return activo;
    }

    public double getPrecio() {
        return precio;
    }

    public Date getCreado() {
        return creado;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setCreado(Date creado) {
        this.creado = creado;
    }
     
}
