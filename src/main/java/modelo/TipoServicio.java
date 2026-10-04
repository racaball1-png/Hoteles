/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author acabi
 */
public class TipoServicio {
    private int idTipoServicio;
    private int servicioHabitacion;
    private int buffetIncluido;
    private int espaciosVip;

    public TipoServicio() {}
    public int getIdTipoServicio() { return idTipoServicio; }
    public void setIdTipoServicio(int idTipoServicio) { this.idTipoServicio = idTipoServicio; }
    public int getServicioHabitacion() { return servicioHabitacion; }
    public void setServicioHabitacion(int servicioHabitacion) { this.servicioHabitacion = servicioHabitacion; }
    public int getBuffetIncluido() { return buffetIncluido; }
    public void setBuffetIncluido(int buffetIncluido) { this.buffetIncluido = buffetIncluido; }
    public int getEspaciosVip() { return espaciosVip; }
    public void setEspaciosVip(int espaciosVip) { this.espaciosVip = espaciosVip; }
}