/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import jakarta.persistence.*;

/**
 *
 * @author acabi
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;
    private String apellido;
    private String correoElectronico;
    private String numeroTelefonico;
    private String dpi;
    private String contrasena;

    public Persona() {
    }

    public Persona(String nombre, String apellido, String correoElectronico, String numeroTelefonico, String dpi, String contrasena) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correoElectronico = correoElectronico;
        this.numeroTelefonico = numeroTelefonico;
        this.dpi = dpi;
        this.contrasena = contrasena;
    }
}