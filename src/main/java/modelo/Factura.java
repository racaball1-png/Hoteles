/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import java.time.LocalDateTime;
/**
 *
 * @author acabi
 */
public class Factura {
    private int idFactura;
    private int idReservacion;
    private int idCliente;
    private int idFormaPago;
    private String nitCliente;
    private String nombreFactura;
    private double subtotal;
    private double impuesto;
    private double montoTotal;
    private LocalDateTime fechaEmision;
    private String estado;

    public Factura() {}

    // Getters y Setters
    public int getIdFactura() { return idFactura; }
    public void setIdFactura(int idFactura) { this.idFactura = idFactura; }

    public int getIdReservacion() { return idReservacion; }
    public void setIdReservacion(int idReservacion) { this.idReservacion = idReservacion; }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public int getIdFormaPago() { return idFormaPago; }
    public void setIdFormaPago(int idFormaPago) { this.idFormaPago = idFormaPago; }

    public String getNitCliente() { return nitCliente; }
    public void setNitCliente(String nitCliente) { this.nitCliente = nitCliente; }

    public String getNombreFactura() { return nombreFactura; }
    public void setNombreFactura(String nombreFactura) { this.nombreFactura = nombreFactura; }

    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }

    public double getImpuesto() { return impuesto; }
    public void setImpuesto(double impuesto) { this.impuesto = impuesto; }

    public double getMontoTotal() { return montoTotal; }
    public void setMontoTotal(double montoTotal) { this.montoTotal = montoTotal; }

    public LocalDateTime getFechaEmision() { return fechaEmision; }
    public void setFechaEmision(LocalDateTime fechaEmision) { this.fechaEmision = fechaEmision; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
