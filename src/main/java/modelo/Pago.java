/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPago;

    private LocalDateTime fechaPago;
    private String estadoPago;
    private String metodoPago;

    public Pago() {
    }

    public Pago(LocalDateTime fechaPago, String estadoPago, String metodoPago) {
        this.fechaPago = fechaPago;
        this.estadoPago = estadoPago;
        this.metodoPago = metodoPago;
    }
}