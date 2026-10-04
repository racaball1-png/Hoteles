/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author acabi
 */
public class EstadoHabitacion {
    private int idEstado;
    private String estadoHabitacion;

    public EstadoHabitacion() {}
    public EstadoHabitacion(int idEstado, String estadoHabitacion) {
        this.idEstado = idEstado;
        this.estadoHabitacion = estadoHabitacion;
    }
    public int getIdEstado() { return idEstado; }
    public void setIdEstado(int idEstado) { this.idEstado = idEstado; }
    public String getEstadoHabitacion() { return estadoHabitacion; }
    public void setEstadoHabitacion(String estadoHabitacion) { this.estadoHabitacion = estadoHabitacion; }
}