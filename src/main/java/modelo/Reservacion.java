/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
/**
 *
 * @author acabi
 */
public class Reservacion {

    private int idReservacion;
    private int idCliente;
    private int idTrabajador;
    private int idHabitacion;
    private LocalDateTime fechaIngreso;
    private LocalDateTime fechaSalida;
    private double costoAlojamiento;
    private int idEstadoReserva;

    public Reservacion() {
    }

    public Reservacion(int idReservacion, int idCliente, int idTrabajador, int idHabitacion, LocalDateTime fechaIngreso, LocalDateTime fechaSalida, double costoAlojamiento, int idEstadoReserva) {
        this.idReservacion = idReservacion;
        this.idCliente = idCliente;
        this.idTrabajador = idTrabajador;
        this.idHabitacion = idHabitacion;
        this.fechaIngreso = fechaIngreso;
        this.fechaSalida = fechaSalida;
        this.costoAlojamiento = costoAlojamiento;
        this.idEstadoReserva = idEstadoReserva;
    }

    public int getIdReservacion() {
        return idReservacion;
    }

    public void setIdReservacion(int idReservacion) {
        this.idReservacion = idReservacion;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdTrabajador() {
        return idTrabajador;
    }

    public void setIdTrabajador(int idTrabajador) {
        this.idTrabajador = idTrabajador;
    }

    public int getIdHabitacion() {
        return idHabitacion;
    }

    public void setIdHabitacion(int idHabitacion) {
        this.idHabitacion = idHabitacion;
    }

    public int getIdRoom() {
        return idHabitacion;
    }

    public void setIdRoom(int idRoom) {
        this.idHabitacion = idRoom;
    }

    // Getters y Setters para Fechas (usando LocalDateTime)
    public LocalDateTime getFechaIngreso() {
        return fechaIngreso;
    }

    public LocalDateTime getFechaEntrada() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDateTime fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public void setFechaIngreso(Timestamp ts) {
        if (ts != null) {
            this.fechaIngreso = ts.toLocalDateTime();
        }
    }

    public void setFechaIngreso(Date date) {
        if (date != null) {
            if (date instanceof Timestamp) {
                this.fechaIngreso = ((Timestamp) date).toLocalDateTime();
            } else {
                this.fechaIngreso = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            }
        }
    }

    public LocalDateTime getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDateTime fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public void setFechaSalida(Timestamp ts) {
        if (ts != null) {
            this.fechaSalida = ts.toLocalDateTime();
        }
    }

    public void setFechaSalida(Date date) {
        if (date != null) {
            if (date instanceof Timestamp) {
                this.fechaSalida = ((Timestamp) date).toLocalDateTime();
            } else {
                this.fechaSalida = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            }
        }
    }

    // Costos y Montos
    public double getCostoAlojamiento() {
        return costoAlojamiento;
    }

    public void setCostoAlojamiento(double costoAlojamiento) {
        this.costoAlojamiento = costoAlojamiento;
    }

    public double getCostoTotal() {
        return costoAlojamiento;
    }

    public void setCostoTotal(double costoTotal) {
        this.costoAlojamiento = costoTotal;
    }

    public double getMontoTotal() {
        return costoAlojamiento;
    }

    public void setMontoTotal(double montoTotal) {
        this.costoAlojamiento = montoTotal;
    }

    public int getIdEstadoReserva() {
        return idEstadoReserva;
    }

    public void setIdEstadoReserva(int idEstadoReserva) {
        this.idEstadoReserva = idEstadoReserva;
    }
}