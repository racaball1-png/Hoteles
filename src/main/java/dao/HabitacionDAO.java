/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dao.ConexionDB;
import modelo.Habitacion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HabitacionDAO {

    public boolean guardar(Habitacion h) {
        String sql = "INSERT INTO habitaciones (numero, tipo, precio, estado) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, h.getNumero());
            stmt.setString(2, h.getTipo());
            stmt.setDouble(3, h.getPrecio());
            stmt.setString(4, h.getEstado());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al guardar habitación: " + e.getMessage());
            return false;
        }
    }

    public List<Habitacion> listar() {
        List<Habitacion> lista = new ArrayList<>();
        String sql = "SELECT id, numero, tipo, precio, estado FROM habitaciones";

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Habitacion h = new Habitacion();
                h.setId(rs.getInt("id"));
                h.setNumero(rs.getInt("numero"));
                h.setTipo(rs.getString("tipo"));
                h.setPrecio(rs.getDouble("precio"));
                h.setEstado(rs.getString("estado"));
                lista.add(h);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar habitaciones: " + e.getMessage());
        }
        return lista;
    }
}
