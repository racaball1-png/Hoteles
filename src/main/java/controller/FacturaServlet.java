package controller;

import dao.FacturaDAO;
import dto.ApiResponse;
import modelo.Factura;
import com.google.gson.Gson;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/api/facturas/generar")
public class FacturaServlet extends HttpServlet {

    private FacturaDAO facturaDAO = new FacturaDAO();
    private Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            Factura factura = gson.fromJson(req.getReader(), Factura.class);
            boolean ok = facturaDAO.generarFactura(factura);

            if (ok) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
                resp.getWriter().write(gson.toJson(new ApiResponse(true, "Factura emitida correctamente", null)));
            } else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                resp.getWriter().write(gson.toJson(new ApiResponse(false, "Error al registrar la factura", null)));
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(gson.toJson(new ApiResponse(false, "Estructura JSON inválida", null)));
        }
    }
}