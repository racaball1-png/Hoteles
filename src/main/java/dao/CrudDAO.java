/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.List;

public interface CrudDAO<T> {
    List<T> listar();
    T obtenerPorId(int id);
    Boolean crear(T objeto);
    Boolean actualizar(T objeto);
    Boolean eliminar(int id);
}