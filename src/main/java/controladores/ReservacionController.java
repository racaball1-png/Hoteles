/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;
import dao.ClienteDAO;
import dao.ReservacionDAO;
import java.time.LocalDateTime;
/**
 *
 * @author acabi
 */
public class ReservacionController {

    private final ClienteDAO clienteDAO;
    private final ReservacionDAO reservacionDAO;

    public ReservacionController() {
        this.clienteDAO = new ClienteDAO();
        this.reservacionDAO = new ReservacionDAO();
    }

    public boolean crearReservacionConDatosHuesped(
            String nombres, String apellidos, String correo, 
            String telefono, String documento, int idTrabajador, 
            int idHabitacion, LocalDateTime fechaEntrada, 
            LocalDateTime fechaSalida, double precioNoche) {

        try {
            int idCliente = clienteDAO.obtenerOBuscarIdCliente(nombres, apellidos, correo, telefono, documento);

            if (idCliente <= 0) {
                System.err.println("No se pudo obtener ni crear el cliente.");
                return false;
            }

            return reservacionDAO.insertarReservacion(idCliente, idTrabajador, idHabitacion, fechaEntrada, fechaSalida, precioNoche);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean cancelarReservacion(int idReservacion) {
        try {
            return reservacionDAO.cancelarReservacion(idReservacion);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}