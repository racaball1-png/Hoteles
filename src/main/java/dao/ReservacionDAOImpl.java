/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import dao.ReservacionDAO;
import modelo.Reservacion;
import dao.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ReservacionDAOImpl implements ReservacionDAO {

    private Connection conexion;

    public ReservacionDAOImpl() {
        try {
            this.conexion = ConexionDB.obtenerConexion();
        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
        }
    }

    @Override
    public List<Reservacion> listar() {
        List<Reservacion> lista = new ArrayList<>();
        String sql = "SELECT * FROM reservacion";

        try (PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Reservacion res = mapearReservacion(rs);
                lista.add(res);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar reservaciones: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public Reservacion obtenerPorId(int id) {
        String sql = "SELECT * FROM reservacion WHERE IDReservacion = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearReservacion(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener reservación por ID: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Boolean crear(Reservacion res) {
        String sql = "INSERT INTO reservacion (IDCliente, IDTrabajador, IDRoom, IDEstadoReserva, " +
                     "CantidadPersonas, FechaEntrada, FechaSalida, MontoTotal) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, res.getIdCliente());
            stmt.setInt(2, res.getIdTrabajador());
            stmt.setInt(3, res.getIdRoom());
            stmt.setInt(4, res.getIdEstadoReserva());
            stmt.setInt(5, res.getCantidadPersonas());
            stmt.setTimestamp(6, Timestamp.valueOf(res.getFechaEntrada()));
            stmt.setTimestamp(7, Timestamp.valueOf(res.getFechaSalida()));
            stmt.setDouble(8, res.getMontoTotal());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al crear reservación: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Boolean actualizar(Reservacion res) {
        String sql = "UPDATE reservacion SET IDCliente = ?, IDTrabajador = ?, IDRoom = ?, IDEstadoReserva = ?, " +
                     "CantidadPersonas = ?, FechaEntrada = ?, FechaSalida = ?, MontoTotal = ? WHERE IDReservacion = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, res.getIdCliente());
            stmt.setInt(2, res.getIdTrabajador());
            stmt.setInt(3, res.getIdRoom());
            stmt.setInt(4, res.getIdEstadoReserva());
            stmt.setInt(5, res.getCantidadPersonas());
            stmt.setTimestamp(6, Timestamp.valueOf(res.getFechaEntrada()));
            stmt.setTimestamp(7, Timestamp.valueOf(res.getFechaSalida()));
            stmt.setDouble(8, res.getMontoTotal());
            stmt.setInt(9, res.getIdReservacion());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar reservación: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Boolean eliminar(int id) {
        String sql = "DELETE FROM reservacion WHERE IDReservacion = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar reservación: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Reservacion> buscarPorCliente(int idCliente) {
        List<Reservacion> lista = new ArrayList<>();
        String sql = "SELECT * FROM reservacion WHERE IDCliente = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idCliente);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearReservacion(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar por cliente: " + e.getMessage());
        }
        return lista;
    }

    private Reservacion mapearReservacion(ResultSet rs) throws SQLException {
        Reservacion res = new Reservacion();
        res.setIdReservacion(rs.getInt("IDReservacion"));
        res.setIdCliente(rs.getInt("IDCliente"));
        res.setIdTrabajador(rs.getInt("IDTrabajador"));
        res.setIdRoom(rs.getInt("IDRoom"));
        res.setIdEstadoReserva(rs.getInt("IDEstadoReserva"));
        res.setCantidadPersonas(rs.getInt("CantidadPersonas"));
        
        Timestamp entrada = rs.getTimestamp("FechaEntrada");
        if (entrada != null) res.setFechaEntrada(entrada.toLocalDateTime());

        Timestamp salida = rs.getTimestamp("FechaSalida");
        if (salida != null) res.setFechaSalida(salida.toLocalDateTime());

        res.setMontoTotal(rs.getDouble("MontoTotal"));
        return res;
    }
}