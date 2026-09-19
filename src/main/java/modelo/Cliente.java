/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import jakarta.persistence.Entity;
/**
 *
 * @author acabi
 */
public class Cliente extends Persona {
    private String nit;
    private String direccion;

    public Cliente() {
    }

    public Cliente(String nombre, String apellido, String correoElectronico, String numeroTelefonico, String dpi, String contrasena, String nit, String direccion) {
        super(nombre, apellido, correoElectronico, numeroTelefonico, dpi, contrasena);
        this.nit = nit;
        this.direccion = direccion;
    }
}