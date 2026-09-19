/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import jakarta.persistence.*;

@Entity
public class TipoHabitacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTipoHabitacion;

    private String tipoServicio;
    private String tipoHabitacion;

    public TipoHabitacion() {
    }

    public TipoHabitacion(String tipoServicio, String tipoHabitacion) {
        this.tipoServicio = tipoServicio;
        this.tipoHabitacion = tipoHabitacion;
    }
}
