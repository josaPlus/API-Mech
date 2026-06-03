/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.lasalle.model;

/**
 *
 * @author josaf
 */
public class Cliente {
    private int    id;
    private String nombre;
    private String telefono;
    private String correo;
    private String createdAt;
    
    public Cliente(){
        
    }
    
    public Cliente(int id, String nombre, String telefono, String correo, String createdAt) {
        this.id        = id;
        this.nombre    = nombre;
        this.telefono  = telefono;
        this.correo    = correo;
        this.createdAt = createdAt;
    }
    
    // Getter
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public String getCreatedAt() {
        return createdAt;
    }
    
    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    
}
