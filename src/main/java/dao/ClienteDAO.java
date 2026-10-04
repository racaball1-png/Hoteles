/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import java.sql.*;
import javax.swing.JOptionPane;
/**
 *
 * @author acabi
 */
public class ClienteDAO {

    /**
     * Busca el cliente existente o crea a la persona y registra su ID de cliente.
     * Utilizado por ReservacionController.java
     */
    public int obtenerOBuscarIdCliente(String nombre, String apellido, String correo, String telefono, String dpi) {
        int idPersona = -1;
        int idCliente = -1;

        try (Connection con = Conexion.getConexion()) {
            if (con == null) {
                JOptionPane.showMessageDialog(null, "No se pudo establecer conexión con la base de datos.", "Error Conexión", JOptionPane.ERROR_MESSAGE);
                return -1;
            }

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
            } catch (SQLException ex) {
                String sqlBuscarAlt = "SELECT id_persona FROM persona WHERE numero_documento = ? OR correo = ?";
                try (PreparedStatement ps = con.prepareStatement(sqlBuscarAlt)) {
                    ps.setString(1, dpi);
                    ps.setString(2, correo);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            idPersona = rs.getInt("id_persona");
                        }
                    }
                } catch (SQLException ignored) {}
            }

            // 2. Si la persona no existe, crearla
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

            if (idPersona <= 0) {
                return -1;
            }

            // 3. Crear el registro en 'usuario' si no existe
            String sqlUsuario = "INSERT INTO usuario (id_persona, correo_login, clave, rol, estado) VALUES (?, ?, '123456', 'CLIENTE', 'ACTIVO')";
            try (PreparedStatement psUsuario = con.prepareStatement(sqlUsuario)) {
                psUsuario.setInt(1, idPersona);
                psUsuario.setString(2, correo);
                psUsuario.executeUpdate();
            } catch (SQLException ignored) {}

            // 4. Obtener o crear en la tabla 'cliente'
            String sqlBuscarCliente = "SELECT id_cliente FROM cliente WHERE id_persona = ?";
            try (PreparedStatement ps = con.prepareStatement(sqlBuscarCliente)) {
                ps.setInt(1, idPersona);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt("id_cliente");
                    }
                }
            } catch (SQLException ignored) {}

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
            JOptionPane.showMessageDialog(null, 
                "Error de MySQL en ClienteDAO:\n" + e.getMessage(), 
                "Detalle de Error MySQL", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return -1;
        }

        return idCliente > 0 ? idCliente : idPersona;
    }

    /**
     * Registra un cliente desde el formulario FrmRegistroUsuario
     */
    public boolean insertarCliente(String nombre, String apellido, String correo, String telefono, String dpi, String nit, String password) {
        int idCliente = obtenerOBuscarIdCliente(nombre, apellido, correo, telefono, dpi);

        if (idCliente <= 0) {
            return false;
        }

        try (Connection con = Conexion.getConexion()) {
            if (con == null) return false;

            String sqlObtenerPersona = "SELECT id_persona FROM persona WHERE dpi = ? OR correo = ?";
            int idPersona = -1;
            try (PreparedStatement ps = con.prepareStatement(sqlObtenerPersona)) {
                ps.setString(1, dpi);
                ps.setString(2, correo);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        idPersona = rs.getInt("id_persona");
                    }
                }
            }

            if (idPersona > 0) {
                String sqlUsuario = "INSERT INTO usuario (id_persona, correo_login, clave, rol, estado) VALUES (?, ?, ?, 'CLIENTE', 'ACTIVO') "
                                  + "ON DUPLICATE KEY UPDATE clave = ?, correo_login = ?";
                try (PreparedStatement psUsuario = con.prepareStatement(sqlUsuario)) {
                    psUsuario.setInt(1, idPersona);
                    psUsuario.setString(2, correo);
                    psUsuario.setString(3, password);
                    psUsuario.setString(4, password);
                    psUsuario.setString(5, correo);
                    psUsuario.executeUpdate();
                }
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}