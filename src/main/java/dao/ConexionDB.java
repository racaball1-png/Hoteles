/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    
    // Configuración de la base de datos (Modifica estos valores con tus datos reales)
    private static final String URL = "jdbc:mysql://localhost:3306/tu_base_de_datos";
    private static final String USUARIO = "root";
    private static final String CONTRASENA = "admin123";

    // Constructor privado para evitar que se instancie la clase
    private ConexionDB() {
    }

    public static Connection obtenerConexion() throws SQLException {
        try {
            // Cargar el driver de MySQL (Requerido en algunas versiones)
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USUARIO, CONTRASENA);
        } catch (ClassNotFoundException e) {
            System.err.println("Error: No se encontró el driver de la base de datos.");
            throw new SQLException(e);
        }
    }
}