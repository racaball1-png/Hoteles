/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import jakarta.persistence.*;

@Entity
public class Habitacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRoom;

    private String numeroHabitacion;
    private Double precioNoche;
    private Integer capacidadPersona;
    private Integer nivelPiso;

    @ManyToOne
    @JoinColumn(name = "id_tipo_habitacion")
    private TipoHabitacion tipoHabitacion;

    @ManyToOne
    @JoinColumn(name = "id_estado_habitacion")
    private EstadoHabitacion estadoHabitacion;

    public Habitacion() {
    }

    public Habitacion(String numeroHabitacion, Double precioNoche, Integer capacidadPersona, Integer nivelPiso, TipoHabitacion tipoHabitacion, EstadoHabitacion estadoHabitacion) {
        this.numeroHabitacion = numeroHabitacion;
        this.precioNoche = precioNoche;
        this.capacidadPersona = capacidadPersona;
        this.nivelPiso = nivelPiso;
        this.tipoHabitacion = tipoHabitacion;
        this.estadoHabitacion = estadoHabitacion;
    }
}
