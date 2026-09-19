/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import jakarta.persistence.*;

@Entity
public class EstadoHabitacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEstado;

    private String estadoHabitacion;

    public EstadoHabitacion() {
    }

    public EstadoHabitacion(String estadoHabitacion) {
        this.estadoHabitacion = estadoHabitacion;
    }
}