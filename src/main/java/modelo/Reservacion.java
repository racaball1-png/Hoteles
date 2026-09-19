/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Reservacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idReservacion;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_trabajador")
    private Trabajador trabajador;

    @ManyToOne
    @JoinColumn(name = "id_room")
    private Habitacion habitacion;

    @ManyToOne
    @JoinColumn(name = "id_estado_reserva")
    private EstadoReserva estadoReserva;

    private LocalDateTime fechaReserva;
    private Integer cantidadPersona; // Corregido de LocalDateTime a Integer
    private LocalDateTime fechaEntrada;
    private LocalDateTime fechaSalida;
    private Double montoTotal;

    public Reservacion() {
    }

    public Reservacion(Cliente cliente, Trabajador trabajador, Habitacion habitacion, EstadoReserva estadoReserva, LocalDateTime fechaReserva, Integer cantidadPersona, LocalDateTime fechaEntrada, LocalDateTime fechaSalida, Double montoTotal) {
        this.cliente = cliente;
        this.trabajador = trabajador;
        this.habitacion = habitacion;
        this.estadoReserva = estadoReserva;
        this.fechaReserva = fechaReserva;
        this.cantidadPersona = cantidadPersona;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.montoTotal = montoTotal;
    }
}
