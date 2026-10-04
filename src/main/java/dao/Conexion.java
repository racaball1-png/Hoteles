/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author acabi
 */
public class Conexion {
    private static final String IP_AWS = "3.128.173.92"; 
    private static final String PORT = "3306";
    private static final String DATABASE = "hotel_bambu";
    
    private static final String URL = "jdbc:mysql://" + IP_AWS + ":" + PORT + "/" + DATABASE + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "hotel_user";
    private static final String PASSWORD = "NotieneContrasena";

    public static Connection getConexion() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}