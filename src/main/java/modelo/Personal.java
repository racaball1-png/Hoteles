/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author acabi
 */
public class Personal extends Persona {
    private String puesto;

    public Personal() {
        super();
    }

    public Personal(int idPersona, String nombre, String apellido, String dpi, String numeroTelefonico, String correoElectronico, String puesto) {
        super(idPersona, nombre, apellido, dpi, numeroTelefonico, correoElectronico);
        this.puesto = puesto;
    }

    public String getPuesto() { return puesto; }
    public void setPuesto(String puesto) { this.puesto = puesto; }
}