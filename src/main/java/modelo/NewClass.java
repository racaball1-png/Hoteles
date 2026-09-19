/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idFactura;

    @OneToOne
    @JoinColumn(name = "id_detalle_factura")
    private DetalleFactura detalleFactura;

    private String noFactura;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaCertificacion;
    private LocalDateTime fechaAnulacion;
    private LocalDateTime fechaModificacion;
    private String estadoFactura;
    private Double montoSinIVA;
    private Double iva;

    public Factura() {
    }

    public Factura(DetalleFactura detalleFactura, String noFactura, LocalDateTime fechaCreacion, LocalDateTime fechaCertificacion, LocalDateTime fechaAnulacion, LocalDateTime fechaModificacion, String estadoFactura, Double montoSinIVA, Double iva) {
        this.detalleFactura = detalleFactura;
        this.noFactura = noFactura;
        this.fechaCreacion = fechaCreacion;
        this.fechaCertificacion = fechaCertificacion;
        this.fechaAnulacion = fechaAnulacion;
        this.fechaModificacion = fechaModificacion;
        this.estadoFactura = estadoFactura;
        this.montoSinIVA = montoSinIVA;
        this.iva = iva;
    }
}
