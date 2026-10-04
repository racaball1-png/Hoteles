/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import java.time.LocalDateTime;

/**
 *
 * @author acabi
 */
public class Trabajador extends Persona {
    private int idTrabajador;
    private String puesto;

    public Trabajador() {
        super();
    }

    public Trabajador(int idTrabajador, int idPersona, String nombre, String apellido, String dpi, String numeroTelefonico, String correoElectronico, String puesto) {
        super(idPersona, nombre, apellido, dpi, numeroTelefonico, correoElectronico);
        this.idTrabajador = idTrabajador;
        this.puesto = puesto;
    }

    public int getIdTrabajador() { return idTrabajador; }
    public void setIdTrabajador(int idTrabajador) { this.idTrabajador = idTrabajador; }

    // Compatibilidad para llamadas a setId / getId
    public int getId() { return idTrabajador; }
    public void setId(int id) { this.idTrabajador = id; }

    public String getPuesto() { return puesto; }
    public void setPuesto(String puesto) { this.puesto = puesto; }
}