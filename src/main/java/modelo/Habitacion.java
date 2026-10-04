/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author acabi
 */
public class Habitacion {
    private int idHabitacion;
    private int numeroHabitacion;
    private String tipo;
    private double precioNoche;
    private int idEstadoHabitacion;
    private int piso;
    private int capacidad;
    private String estado;

    public Habitacion() {
    }

    public Habitacion(int idHabitacion, int numeroHabitacion, String tipo, double precioNoche, int idEstadoHabitacion) {
        this.idHabitacion = idHabitacion;
        this.numeroHabitacion = numeroHabitacion;
        this.tipo = tipo;
        this.precioNoche = precioNoche;
        this.idEstadoHabitacion = idEstadoHabitacion;
    }

    // Getters y Setters Estándar
    public int getIdHabitacion() {
        return idHabitacion;
    }

    public void setIdHabitacion(int idHabitacion) {
        this.idHabitacion = idHabitacion;
    }

    public int getNumeroHabitacion() {
        return numeroHabitacion;
    }

    public void setNumeroHabitacion(int numeroHabitacion) {
        this.numeroHabitacion = numeroHabitacion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getPrecioNoche() {
        return precioNoche;
    }

    public void setPrecioNoche(double precioNoche) {
        this.precioNoche = precioNoche;
    }

    public int getIdEstadoHabitacion() {
        return idEstadoHabitacion;
    }

    public void setIdEstadoHabitacion(int idEstadoHabitacion) {
        this.idEstadoHabitacion = idEstadoHabitacion;
    }

    public int getPiso() {
        return piso;
    }

    public void setPiso(int piso) {
        this.piso = piso;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    // Métodos Alias para compatibilidad con todas las vistas
    public int getIdRoom() {
        return idHabitacion;
    }

    public void setIdRoom(int idRoom) {
        this.idHabitacion = idRoom;
    }

    public String getNumero() {
        return String.valueOf(numeroHabitacion);
    }

    public void setNumero(String numero) {
        try {
            this.numeroHabitacion = Integer.parseInt(numero);
        } catch (NumberFormatException e) {
            this.numeroHabitacion = 0;
        }
    }

    public double getPrecio() {
        return precioNoche;
    }

    public void setPrecio(double precio) {
        this.precioNoche = precio;
    }

    public int getNivelPiso() {
        return piso;
    }

    public void setNivelPiso(int nivelPiso) {
        this.piso = nivelPiso;
    }

    public int getCapacidadPersona() {
        return capacidad;
    }

    public void setCapacidadPersona(int capacidadPersona) {
        this.capacidad = capacidadPersona;
    }

    @Override
    public String toString() {
        return "Hab. " + numeroHabitacion + " (" + tipo + ") - Q" + precioNoche + "/noche";
    }
}