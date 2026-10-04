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
public class TrabajadorDAO {

    public boolean insertarTrabajador(String nombre, String apellido, String correo, String telefono, String dpi, String password) {
        int idPersona = -1;

        try (Connection con = Conexion.getConexion()) {
            if (con == null) {
                JOptionPane.showMessageDialog(null, "No se pudo conectar a la base de datos.", "Error de Conexión", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            // 1. Buscar si la persona ya existe
            String sqlBuscar = "SELECT id_persona FROM persona WHERE dpi = ? OR correo = ?";
            try (PreparedStatement ps = con.prepareStatement(sqlBuscar)) {
                ps.setString(1, dpi);
                ps.setString(2, correo);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        idPersona = rs.getInt("id_persona");
                    }
                }
            }

            // 2. Si no existe, insertar en 'persona'
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
                return false;
            }

            // 3. Crear el registro en la tabla 'usuario' con rol TRABAJADOR
            String sqlUsuario = "INSERT INTO usuario (id_persona, correo_login, clave, rol, estado) VALUES (?, ?, ?, 'TRABAJADOR', 'ACTIVO')";
            try (PreparedStatement psUsuario = con.prepareStatement(sqlUsuario)) {
                psUsuario.setInt(1, idPersona);
                psUsuario.setString(2, correo);
                psUsuario.setString(3, password);
                psUsuario.executeUpdate();
            } catch (SQLException ex) {
                System.out.println("Aviso: El usuario trabajador ya existe o tiene credenciales registradas.");
            }

            // 4. Registrar en la tabla 'trabajador' si existe dicha tabla relacional
            String sqlTrabajador = "INSERT INTO trabajador (id_persona) VALUES (?)";
            try (PreparedStatement psTrabajador = con.prepareStatement(sqlTrabajador)) {
                psTrabajador.setInt(1, idPersona);
                psTrabajador.executeUpdate();
            } catch (SQLException ignored) {
                // Si no existe la tabla 'trabajador' o el id_persona ya está registrado
            }

            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                "Error al registrar trabajador en la base de datos:\n" + e.getMessage(), 
                "Error MySQL", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        }
    }
}