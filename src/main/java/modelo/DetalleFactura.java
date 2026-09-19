/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import jakarta.persistence.*;

@Entity
public class DetalleFactura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDetalleFactura;

    @OneToOne
    @JoinColumn(name = "id_reservacion")
    private Reservacion reservacion;

    @ManyToOne
    @JoinColumn(name = "id_pago")
    private Pago pago;

    public DetalleFactura() {
    }

    public DetalleFactura(Reservacion reservacion, Pago pago) {
        this.reservacion = reservacion;
        this.pago = pago;
    }
}