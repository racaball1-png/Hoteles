/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import modelo.Persona;
import modelo.UsuarioSesion;
import modelo.UsuarioSesion.Rol;
import java.sql.*;
/**
 *
 * @author acabi
 */
public class UsuarioDAO {

    public UsuarioSesion login(String correo, String password) {
        String sql = "SELECT u.id_usuario, p.id_persona, p.nombre, p.apellido, u.correo_login, u.rol " +
                     "FROM usuario u " +
                     "JOIN persona p ON u.id_persona = p.id_persona " +
                     "WHERE u.correo_login = ? AND u.clave = ? AND u.estado = 'ACTIVO'";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, correo);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int idUsuario = rs.getInt("id_usuario");
                    int idPersona = rs.getInt("id_persona");
                    String nombreComp = rs.getString("nombre") + " " + rs.getString("apellido");
                    String mail = rs.getString("correo_login");
                    Rol rol = Rol.valueOf(rs.getString("rol").toUpperCase());

                    return new UsuarioSesion(idUsuario, idPersona, nombreComp, mail, rol);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en Login: " + e.getMessage());
        }
        return null;
    }

    public boolean existeCorreo(String correo) {
        String sql = "SELECT id_usuario FROM usuario WHERE correo_login = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, correo);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean registrarUsuario(Persona persona, String password, Rol rol, String nit) {
        String sqlPersona = "INSERT INTO persona (nombre, apellido, dpi, telefono, correo) VALUES (?, ?, ?, ?, ?)";
        String sqlUsuario = "INSERT INTO usuario (id_persona, correo_login, clave, rol) VALUES (?, ?, ?, ?)";
        String sqlCliente = "INSERT INTO cliente (id_persona, nit) VALUES (?, ?)";
        String sqlTrabajador = "INSERT INTO trabajador (id_persona) VALUES (?)";

        Connection con = null;
        try {
            con = Conexion.getConexion();
            con.setAutoCommit(false);

            int idPersonaGenerado = -1;
            try (PreparedStatement psP = con.prepareStatement(sqlPersona, Statement.RETURN_GENERATED_KEYS)) {
                psP.setString(1, persona.getNombre());
                psP.setString(2, persona.getApellido());
                psP.setString(3, persona.getDpi());
                psP.setString(4, persona.getNumeroTelefonico());
                psP.setString(5, persona.getCorreoElectronico());
                psP.executeUpdate();

                try (ResultSet rs = psP.getGeneratedKeys()) {
                    if (rs.next()) {
                        idPersonaGenerado = rs.getInt(1);
                    }
                }
            }

            if (idPersonaGenerado == -1) {
                con.rollback();
                return false;
            }

            try (PreparedStatement psU = con.prepareStatement(sqlUsuario)) {
                psU.setInt(1, idPersonaGenerado);
                psU.setString(2, persona.getCorreoElectronico());
                psU.setString(3, password);
                psU.setString(4, rol.name());
                psU.executeUpdate();
            }

            if (rol == Rol.CLIENTE) {
                try (PreparedStatement psC = con.prepareStatement(sqlCliente)) {
                    psC.setInt(1, idPersonaGenerado);
                    psC.setString(2, (nit == null || nit.isBlank()) ? "CF" : nit);
                    psC.executeUpdate();
                }
            } else {
                try (PreparedStatement psT = con.prepareStatement(sqlTrabajador)) {
                    psT.setInt(1, idPersonaGenerado);
                    psT.executeUpdate();
                }
            }

            con.commit();
            return true;

        } catch (SQLException e) {
            if (con != null) {
                try { con.rollback(); } catch (SQLException ex) { System.err.println(ex.getMessage()); }
            }
            System.err.println("Error al registrar usuario: " + e.getMessage());
            return false;
        } finally {
            if (con != null) {
                try { con.setAutoCommit(true); con.close(); } catch (SQLException e) { System.err.println(e.getMessage()); }
            }
        }
    }
}