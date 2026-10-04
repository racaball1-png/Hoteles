/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import modelo.Habitacion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author acabi
 */
public class HabitacionDAO {

    public List<Habitacion> listarHabitaciones() {
        List<Habitacion> lista = new ArrayList<>();
        String sql = "SELECT * FROM habitacion";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearHabitacion(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar habitaciones: " + e.getMessage());
        }
        return lista;
    }

    public List<Habitacion> obtenerHabitacionesDisponibles() {
        List<Habitacion> lista = new ArrayList<>();
        String sql = "SELECT * FROM habitacion WHERE id_estado_habitacion = 1";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearHabitacion(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener habitaciones disponibles: " + e.getMessage());
        }
        return lista;
    }

    private Habitacion mapearHabitacion(ResultSet rs) throws SQLException {
        Habitacion hab = new Habitacion();

        int idHab = rs.getInt("id_habitacion");
        int numHab = rs.getInt("numero_habitacion");
        String tipo = rs.getString("tipo");
        double precio = rs.getDouble("precio_noche");
        int idEstado = rs.getInt("id_estado_habitacion");

        hab.setIdHabitacion(idHab);
        hab.setNumeroHabitacion(numHab);
        hab.setTipo(tipo);
        hab.setPrecioNoche(precio);
        hab.setIdEstadoHabitacion(idEstado);

        // Definir estado en texto para la tabla
        if (idEstado == 1) {
            hab.setEstado("Disponible");
        } else if (idEstado == 2) {
            hab.setEstado("Ocupada");
        } else if (idEstado == 3) {
            hab.setEstado("Mantenimiento");
        } else {
            hab.setEstado("Disponible");
        }

        // Calcular piso si no viene en BD (ej. 101 -> Piso 1, 201 -> Piso 2)
        try {
            hab.setPiso(rs.getInt("piso"));
        } catch (Exception e) {
            int pisoCalculado = numHab / 100;
            hab.setPiso(pisoCalculado > 0 ? pisoCalculado : 1);
        }

        // Calcular capacidad según el tipo de habitación
        try {
            hab.setCapacidad(rs.getInt("capacidad"));
        } catch (Exception e) {
            if (tipo != null && tipo.toLowerCase().contains("suite")) {
                hab.setCapacidad(6);
            } else if (tipo != null && tipo.toLowerCase().contains("doble")) {
                hab.setCapacidad(4);
            } else {
                hab.setCapacidad(2);
            }
        }

        return hab;
    }
}