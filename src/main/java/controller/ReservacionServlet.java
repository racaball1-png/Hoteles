package controller;

import dao.ReservacionDAO;
import dto.ApiResponse;
import modelo.Reservacion;
import com.google.gson.*;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet("/api/reservaciones/*")
public class ReservacionServlet extends HttpServlet {

    private ReservacionDAO reservacionDAO = new ReservacionDAO();
    private Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, type, ctx) ->
                    LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME))
            .create();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        List<Reservacion> reservaciones = reservacionDAO.listarReservaciones();
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write(gson.toJson(new ApiResponse(true, "OK", reservaciones)));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String path = req.getPathInfo();

        if ("/cancelar".equals(path)) {
            JsonObject body = gson.fromJson(req.getReader(), JsonObject.class);
            int idReserva = body.get("idReservacion").getAsInt();

            boolean ok = reservacionDAO.cancelarReservacion(idReserva);
            if (ok) {
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write(gson.toJson(new ApiResponse(true, "Reservación cancelada", null)));
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write(gson.toJson(new ApiResponse(false, "No se pudo cancelar la reservación", null)));
            }
        } else {
            JsonObject body = gson.fromJson(req.getReader(), JsonObject.class);

            int idCliente = body.get("idCliente").getAsInt();
            int idTrabajador = body.has("idTrabajador") ? body.get("idTrabajador").getAsInt() : 0;
            int idHabitacion = body.get("idHabitacion").getAsInt();
            LocalDateTime fEntrada = LocalDateTime.parse(body.get("fechaEntrada").getAsString());
            LocalDateTime fSalida = LocalDateTime.parse(body.get("fechaSalida").getAsString());
            double costoTotal = body.get("costoTotal").getAsDouble();

            boolean ok = reservacionDAO.insertarReservacion(idCliente, idTrabajador, idHabitacion, fEntrada, fSalida, costoTotal);

            if (ok) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
                resp.getWriter().write(gson.toJson(new ApiResponse(true, "Reservación creada con éxito", null)));
            } else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                resp.getWriter().write(gson.toJson(new ApiResponse(false, "Error al crear la reservación", null)));
            }
        }
    }
}