/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import modelo.Persona;
import java.sql.*;
import java.util.List;
import javax.swing.JOptionPane;
/**
 *
 * @author acabi
 */
public class ClienteDAO {

    /**
     * Inserta un cliente con su lista dinámica de teléfonos (Relación 1:N)
     * Requerido por FrmRegistroUsuario.java
     */
    public boolean insertarClienteConTelefonos(String nombre, String apellido, String correo, String dpi, String password, List<Persona.Telefono> listaTelefonos) {
        int idPersona = -1;

        try (Connection con = Conexion.getConexion()) {
            if (con == null) {
                JOptionPane.showMessageDialog(null, "No se pudo conectar a la base de datos.", "Error de Conexión", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            con.setAutoCommit(false); // Transacción atómica

            // 1. Insertar en tabla persona
            String sqlPersona = "INSERT INTO persona (nombre, apellido, dpi, correo, telefono) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement psPersona = con.prepareStatement(sqlPersona, Statement.RETURN_GENERATED_KEYS)) {
                psPersona.setString(1, nombre);
                psPersona.setString(2, apellido);
                psPersona.setString(3, dpi);
                psPersona.setString(4, correo);
                psPersona.setString(5, listaTelefonos.isEmpty() ? "" : listaTelefonos.get(0).getNumero());
                psPersona.executeUpdate();

                try (ResultSet rsKey = psPersona.getGeneratedKeys()) {
                    if (rsKey.next()) {
                        idPersona = rsKey.getInt(1);
                    }
                }
            }

            if (idPersona <= 0) {
                con.rollback();
                return false;
            }

            // 2. Insertar en tabla usuario
            String sqlUsuario = "INSERT INTO usuario (id_persona, correo_login, clave, rol, estado) VALUES (?, ?, ?, 'CLIENTE', 'ACTIVO')";
            try (PreparedStatement psUsuario = con.prepareStatement(sqlUsuario)) {
                psUsuario.setInt(1, idPersona);
                psUsuario.setString(2, correo);
                psUsuario.setString(3, password);
                psUsuario.executeUpdate();
            }

            // 3. Insertar en tabla cliente
            String sqlCliente = "INSERT INTO cliente (id_persona) VALUES (?)";
            try (PreparedStatement psCliente = con.prepareStatement(sqlCliente)) {
                psCliente.setInt(1, idPersona);
                psCliente.executeUpdate();
            } catch (SQLException ignored) {}

            // 4. Insertar la colección de teléfonos en la tabla 'telefono'
            if (!listaTelefonos.isEmpty()) {
                String sqlTel = "INSERT INTO telefono (id_persona, numero, tipo) VALUES (?, ?, ?)";
                try (PreparedStatement psTel = con.prepareStatement(sqlTel)) {
                    for (Persona.Telefono t : listaTelefonos) {
                        psTel.setInt(1, idPersona);
                        psTel.setString(2, t.getNumero());
                        psTel.setString(3, t.getTipo());
                        psTel.addBatch();
                    }
                    psTel.executeBatch();
                }
            }

            con.commit(); // Confirmar transacción
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar cliente: " + e.getMessage(), "Error MySQL", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Método sobrecargado para inserción tradicional de un solo teléfono
     */
    public boolean insertarCliente(String nombre, String apellido, String correo, String telefono, String dpi, String nit, String password) {
        java.util.List<Persona.Telefono> lista = new java.util.ArrayList<>();
        if (telefono != null && !telefono.isEmpty()) {
            lista.add(new Persona.Telefono(telefono, "Personal"));
        }
        return insertarClienteConTelefonos(nombre, apellido, correo, dpi, password, lista);
    }

    /**
     * Busca o registra la persona y retorna el ID del cliente correspondiente.
     * Requerido por ReservacionController.java
     */
    public int obtenerOBuscarIdCliente(String nombre, String apellido, String correo, String telefono, String dpi) {
        int idPersona = -1;
        int idCliente = -1;

        try (Connection con = Conexion.getConexion()) {
            if (con == null) return -1;

            // 1. Buscar si la persona ya existe por DPI o Correo
            String sqlBuscarPersona = "SELECT id_persona FROM persona WHERE dpi = ? OR correo = ?";
            try (PreparedStatement ps = con.prepareStatement(sqlBuscarPersona)) {
                ps.setString(1, dpi);
                ps.setString(2, correo);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        idPersona = rs.getInt("id_persona");
                    }
                }
            }

            // 2. Si no existe, crear la persona
            if (idPersona <= 0) {
                String sqlPersona = "INSERT INTO persona (nombre, apellido, dpi, telefono, correo) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement psPersona = con.prepareStatement(sqlPersona, Statement.RETURN_GENERATED_KEYS)) {
                    psPersona.setString(1, nombre);
                    psPersona.setString(2, apellido);
                    psPersona.setString(3, dpi);
                    psPersona.setString(4, telefono);
                    psPersona.setString(5, correo);
                    psPersona.executeUpdate();

                    try (ResultSet rsKey = psPersona.getGeneratedKeys()) {
                        if (rsKey.next()) {
                            idPersona = rsKey.getInt(1);
                        }
                    }
                }
            }

            if (idPersona <= 0) return -1;

            // 3. Obtener o crear en la tabla cliente
            String sqlBuscarCliente = "SELECT id_cliente FROM cliente WHERE id_persona = ?";
            try (PreparedStatement ps = con.prepareStatement(sqlBuscarCliente)) {
                ps.setInt(1, idPersona);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt("id_cliente");
                    }
                }
            }

            String sqlCliente = "INSERT INTO cliente (id_persona) VALUES (?)";
            try (PreparedStatement psCliente = con.prepareStatement(sqlCliente, Statement.RETURN_GENERATED_KEYS)) {
                psCliente.setInt(1, idPersona);
                psCliente.executeUpdate();

                try (ResultSet rsKey = psCliente.getGeneratedKeys()) {
                    if (rsKey.next()) {
                        idCliente = rsKey.getInt(1);
                    }
                }
            } catch (SQLException ex) {
                idCliente = idPersona;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }

        return idCliente > 0 ? idCliente : idPersona;
    }
}