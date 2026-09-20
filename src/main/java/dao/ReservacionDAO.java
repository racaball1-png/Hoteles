/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import modelo.Reservacion;
import java.util.List;

public interface ReservacionDAO extends CrudDAO<Reservacion> {
    List<Reservacion> buscarPorCliente(int idCliente);
}