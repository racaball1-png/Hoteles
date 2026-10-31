package controller;

import dao.HabitacionDAO;
import dto.ApiResponse;
import modelo.Habitacion;
import com.google.gson.Gson;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/habitaciones")
public class HabitacionServlet extends HttpServlet {

    private HabitacionDAO habitacionDAO = new HabitacionDAO();
    private Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String soloDisponibles = req.getParameter("disponibles");
        List<Habitacion> lista;

        if ("true".equalsIgnoreCase(soloDisponibles)) {
            lista = habitacionDAO.obtenerHabitacionesDisponibles();
        } else {
            lista = habitacionDAO.listarHabitaciones();
        }

        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write(gson.toJson(new ApiResponse(true, "OK", lista)));
    }
}