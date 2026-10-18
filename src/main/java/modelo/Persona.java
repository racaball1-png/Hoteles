/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author acabi
 */

public class Persona {

    private int idPersona;
    private String nombre;
    private String apellido;
    private String dpi;
    private String telefono;
    private String correo;

    // Colección dinámica para múltiples teléfonos (Relación 1:N)
    private List<Telefono> telefonos = new ArrayList<>();

    // Constructor por defecto (Requerido)
    public Persona() {
    }

    // Constructor parametrizado con 6 argumentos (Requerido por Cliente, Trabajador y Personal)
    public Persona(int idPersona, String nombre, String apellido, String dpi, String telefono, String correo) {
        this.idPersona = idPersona;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dpi = dpi;
        this.telefono = telefono;
        this.correo = correo;
    }

    // Clase interna para soportar teléfonos en la misma estructura y varios a la vez
    public static class Telefono {
        private String numero;
        private String tipo;

        public Telefono(String numero, String tipo) {
            this.numero = numero;
            this.tipo = tipo;
        }

        public String getNumero() { return numero; }
        public String getTipo() { return tipo; }
    }

    // --- GETTERS Y SETTERS ---

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDpi() {
        return dpi;
    }

    public void setDpi(String dpi) {
        this.dpi = dpi;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    // Alias para compatibilidad con LoginController y UsuarioDAO
    public String getNumeroTelefonico() {
        return telefono;
    }

    public void setNumeroTelefonico(String numeroTelefonico) {
        this.telefono = numeroTelefonico;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    // Alias para compatibilidad con LoginController y UsuarioDAO
    public String getCorreoElectronico() {
        return correo;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correo = correoElectronico;
    }

    public List<Telefono> getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(List<Telefono> telefonos) {
        this.telefonos = telefonos;
    }

    public void agregarTelefono(String numero, String tipo) {
        this.telefonos.add(new Telefono(numero, tipo));
    }
}