/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import modelo.Factura;
import java.sql.*;
/**
 *
 * @author acabi
 */
public class FacturaDAO {

    public boolean generarFactura(Factura factura) {
        String sql = "INSERT INTO factura (id_reservacion, id_cliente, id_forma_pago, nit_cliente, nombre_factura, subtotal, impuesto, monto_total, fecha_emision, estado) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, NOW(), 'Emitida')";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, factura.getIdReservacion());
            ps.setInt(2, factura.getIdCliente());
            ps.setInt(3, factura.getIdFormaPago());
            ps.setString(4, factura.getNitCliente() != null && !factura.getNitCliente().trim().isEmpty() ? factura.getNitCliente() : "CF");
            ps.setString(5, factura.getNombreFactura());
            ps.setDouble(6, factura.getSubtotal());
            ps.setDouble(7, factura.getImpuesto());
            ps.setDouble(8, factura.getMontoTotal());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al generar la factura: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}