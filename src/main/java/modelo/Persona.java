/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author acabi
 */
public class Persona {
    private int idPersona;
    private String nombre;
    private String apellido;
    private String dpi;
    private String numeroTelefonico;
    private String correoElectronico;
    private String contrasena;

    public Persona() {}

    public Persona(int idPersona, String nombre, String apellido, String dpi, String numeroTelefonico, String correoElectronico) {
        this.idPersona = idPersona;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dpi = dpi;
        this.numeroTelefonico = numeroTelefonico;
        this.correoElectronico = correoElectronico;
    }

    public int getIdPersona() { return idPersona; }
    public void setIdPersona(int idPersona) { this.idPersona = idPersona; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getDpi() { return dpi; }
    public void setDpi(String dpi) { this.dpi = dpi; }

    public String getNumeroTelefonico() { return numeroTelefonico; }
    public void setNumeroTelefonico(String numeroTelefonico) { this.numeroTelefonico = numeroTelefonico; }

    public String getCorreoElectronico() { return correoElectronico; }
    public void setCorreoElectronico(String correoElectronico) { this.correoElectronico = correoElectronico; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    // Compatibilidad para evitar errores de compilación con la letra 'ñ'
    public String getContraseña() { return contrasena; }
    public void setContraseña(String contrasena) { this.contrasena = contrasena; }
}