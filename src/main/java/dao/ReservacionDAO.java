/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import modelo.Reservacion;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
/**
 *
 * @author acabi
 */
public class ReservacionDAO {

    public List<Reservacion> listarReservaciones() {
        List<Reservacion> lista = new ArrayList<>();
        String sql = "SELECT * FROM reservacion";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Reservacion res = new Reservacion();
                res.setIdReservacion(rs.getInt("IDReservacion"));

                try { res.setIdCliente(rs.getInt("IDCliente")); } catch (Exception ignored) {}
                try { res.setIdTrabajador(rs.getInt("IDTrabajador")); } catch (Exception ignored) {}
                try { res.setIdHabitacion(rs.getInt("IDRoom")); } catch (Exception ignored) {}

                Timestamp tsIngreso = rs.getTimestamp("FechaEntrada");
                if (tsIngreso != null) {
                    res.setFechaIngreso(tsIngreso.toLocalDateTime());
                }

                Timestamp tsSalida = rs.getTimestamp("FechaSalida");
                if (tsSalida != null) {
                    res.setFechaSalida(tsSalida.toLocalDateTime());
                }

                try { res.setCostoAlojamiento(rs.getDouble("MontoTotal")); } catch (Exception ignored) {}
                try { res.setIdEstadoReserva(rs.getInt("IDEstadoReserva")); } catch (Exception ignored) {}

                lista.add(res);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar reservaciones: " + e.getMessage());
        }
        return lista;
    }

    public boolean insertarReservacion(int idCliente, int idTrabajador, int idHabitacion, LocalDateTime fechaEntrada, LocalDateTime fechaSalida, double costoTotal) {
        String sqlReserva = "INSERT INTO reservacion (IDCliente, IDTrabajador, IDRoom, FechaReserva, FechaEntrada, FechaSalida, MontoTotal, IDEstadoReserva) VALUES (?, ?, ?, NOW(), ?, ?, ?, ?)";
        String sqlActualizarHabitacion = "UPDATE habitacion SET id_estado_habitacion = 2 WHERE id_habitacion = ?";

        try (Connection con = Conexion.getConexion()) {
            if (con == null) return false;

            Integer trabajadorValido = obtenerIdTrabajadorValido(con, idTrabajador);
            Integer estadoValido = obtenerIdEstadoReservaValido(con);

            con.setAutoCommit(false); // Iniciar transacción

            try (PreparedStatement ps = con.prepareStatement(sqlReserva)) {
                ps.setInt(1, idCliente);

                if (trabajadorValido != null) {
                    ps.setInt(2, trabajadorValido);
                } else {
                    ps.setNull(2, java.sql.Types.INTEGER);
                }

                ps.setInt(3, idHabitacion);
                ps.setTimestamp(4, Timestamp.valueOf(fechaEntrada));
                ps.setTimestamp(5, Timestamp.valueOf(fechaSalida));
                ps.setDouble(6, costoTotal);

                if (estadoValido != null) {
                    ps.setInt(7, estadoValido);
                } else {
                    ps.setInt(7, 1);
                }

                int filasReserva = ps.executeUpdate();

                // Actualizar estado de la habitación a Ocupada (2)
                try (PreparedStatement psHab = con.prepareStatement(sqlActualizarHabitacion)) {
                    psHab.setInt(1, idHabitacion);
                    psHab.executeUpdate();
                } catch (SQLException exHab) {
                    String sqlAltHab = "UPDATE habitacion SET id_estado_habitacion = 2 WHERE IDRoom = ?";
                    try (PreparedStatement psHabAlt = con.prepareStatement(sqlAltHab)) {
                        psHabAlt.setInt(1, idHabitacion);
                        psHabAlt.executeUpdate();
                    } catch (SQLException ignored) {}
                }

                if (filasReserva > 0) {
                    con.commit(); // Confirmar cambios
                    JOptionPane.showMessageDialog(null, 
                        "¡Reservación realizada con éxito!", 
                        "Éxito", 
                        JOptionPane.INFORMATION_MESSAGE);
                    return true;
                } else {
                    con.rollback();
                }
            } catch (SQLException ex) {
                con.rollback(); // Revertir si ocurre un fallo
                JOptionPane.showMessageDialog(null, 
                    "Error de MySQL al insertar reservación:\n" + ex.getMessage(), 
                    "Detalle de Error MySQL", 
                    JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                "Error de Conexión en ReservacionDAO:\n" + e.getMessage(), 
                "Detalle de Error MySQL", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return false;
    }

    private Integer obtenerIdTrabajadorValido(Connection con, int idTrabajador) {
        if (idTrabajador > 0) {
            String sql = "SELECT IDTrabajador FROM trabajador WHERE IDTrabajador = ?";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, idTrabajador);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) return idTrabajador;
                }
            } catch (SQLException ignored) {}
        }

        String[] consultasPrimerTrabajador = {
            "SELECT IDTrabajador FROM trabajador LIMIT 1",
            "SELECT id_trabajador FROM trabajador LIMIT 1"
        };

        for (String sql : consultasPrimerTrabajador) {
            try (PreparedStatement ps = con.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            } catch (SQLException ignored) {}
        }

        return null;
    }

    private Integer obtenerIdEstadoReservaValido(Connection con) {
        String[] consultas = {
            "SELECT IDEstado FROM estado_reserva LIMIT 1",
            "SELECT IDEstadoReserva FROM estado_reserva LIMIT 1",
            "SELECT id_estado_reserva FROM estado_reserva LIMIT 1"
        };

        for (String sql : consultas) {
            try (PreparedStatement ps = con.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            } catch (SQLException ignored) {}
        }

        return 1;
    }

    public boolean cancelarReservacion(int idReservacion) {
        String sqlObtenerHabitacion = "SELECT IDRoom FROM reservacion WHERE IDReservacion = ?";
        String sqlCancelarReserva = "UPDATE reservacion SET IDEstadoReserva = 3 WHERE IDReservacion = ?";
        String sqlLiberarHabitacion = "UPDATE habitacion SET id_estado_habitacion = 1 WHERE id_habitacion = ?";

        try (Connection con = Conexion.getConexion()) {
            if (con == null) return false;

            con.setAutoCommit(false); // Iniciar transacción

            int idHabitacion = 0;
            
            // 1. Consultar cuál habitación está asociada a esta reserva
            try (PreparedStatement psSelect = con.prepareStatement(sqlObtenerHabitacion)) {
                psSelect.setInt(1, idReservacion);
                try (ResultSet rs = psSelect.executeQuery()) {
                    if (rs.next()) {
                        idHabitacion = rs.getInt("IDRoom");
                    }
                }
            }

            if (idHabitacion > 0) {
                // 2. Cambiar el estado de la reservación a Cancelada (3)
                try (PreparedStatement psReserva = con.prepareStatement(sqlCancelarReserva)) {
                    psReserva.setInt(1, idReservacion);
                    psReserva.executeUpdate();
                }

                // 3. Cambiar el estado de la habitación a Disponible (1)
                try (PreparedStatement psHab = con.prepareStatement(sqlLiberarHabitacion)) {
                    psHab.setInt(1, idHabitacion);
                    psHab.executeUpdate();
                }

                con.commit(); // Confirmar ambos cambios
                return true;
            } else {
                con.rollback();
            }

        } catch (SQLException e) {
            System.err.println("Error al cancelar reservación: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}