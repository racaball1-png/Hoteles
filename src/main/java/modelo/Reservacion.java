/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.time.LocalDateTime;

public class Reservacion {
    private int idReservacion;
    private int idCliente;
    private int idTrabajador;
    private int idRoom;
    private int idEstadoReserva;
    private int cantidadPersonas;
    private LocalDateTime fechaEntrada;
    private LocalDateTime fechaSalida;
    private double montoTotal;

    public Reservacion() {}

    public int getIdReservacion() { return idReservacion; }
    public void setIdReservacion(int idReservacion) { this.idReservacion = idReservacion; }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public int getIdTrabajador() { return idTrabajador; }
    public void setIdTrabajador(int idTrabajador) { this.idTrabajador = idTrabajador; }

    public int getIdRoom() { return idRoom; }
    public void setIdRoom(int idRoom) { this.idRoom = idRoom; }

    public int getIdEstadoReserva() { return idEstadoReserva; }
    public void setIdEstadoReserva(int idEstadoReserva) { this.idEstadoReserva = idEstadoReserva; }

    public int getCantidadPersonas() { return cantidadPersonas; }
    public void setCantidadPersonas(int cantidadPersonas) { this.cantidadPersonas = cantidadPersonas; }

    public LocalDateTime getFechaEntrada() { return fechaEntrada; }
    public void setFechaEntrada(LocalDateTime fechaEntrada) { this.fechaEntrada = fechaEntrada; }

    public LocalDateTime getFechaSalida() { return fechaSalida; }
    public void setFechaSalida(LocalDateTime fechaSalida) { this.fechaSalida = fechaSalida; }

    public double getMontoTotal() { return montoTotal; }
    public void setMontoTotal(double montoTotal) { this.montoTotal = montoTotal; }
}
