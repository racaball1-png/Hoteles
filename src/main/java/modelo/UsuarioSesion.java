/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author acabi
 */
public class UsuarioSesion {
    public enum Rol { TRABAJADOR, CLIENTE }

    private int idUsuario;
    private int idPersona;
    private String nombre;
    private String apellido;
    private String correo;
    private Rol rol;

    public UsuarioSesion() {}

    public UsuarioSesion(int idUsuario, int idPersona, String nombre, String correo, Rol rol) {
        this.idUsuario = idUsuario;
        this.idPersona = idPersona;
        this.nombre = nombre;
        this.apellido = "";
        this.correo = correo;
        this.rol = rol;
    }

    public UsuarioSesion(int idUsuario, int idPersona, String nombre, String apellido, String correo, Rol rol) {
        this.idUsuario = idUsuario;
        this.idPersona = idPersona;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.rol = rol;
    }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public int getIdPersona() { return idPersona; }
    public void setIdPersona(int idPersona) { this.idPersona = idPersona; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }
}