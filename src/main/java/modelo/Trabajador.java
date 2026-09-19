/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import jakarta.persistence.Entity;
import java.time.LocalDateTime;
/**
 *
 * @author acabi
 */
public class Trabajador extends Persona {
    private Integer codigoTrabajador;
    private LocalDateTime fechaInicio;

    public Trabajador() {
    }

    public Trabajador(String nombre, String apellido, String correoElectronico, String numeroTelefonico, String dpi, String contrasena, Integer codigoTrabajador, LocalDateTime fechaInicio) {
        super(nombre, apellido, correoElectronico, numeroTelefonico, dpi, contrasena);
        this.codigoTrabajador = codigoTrabajador;
        this.fechaInicio = fechaInicio;
    }
}
