package controller;

import dao.UsuarioDAO;
import dto.ApiResponse;
import dto.LoginRequest;
import dto.RegistroUsuarioRequest;
import modelo.Persona;
import modelo.UsuarioSesion;
import modelo.UsuarioSesion.Rol;
import com.google.gson.Gson;

import java.io.IOException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/api/auth/*")
public class AuthServlet extends HttpServlet {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String path = req.getPathInfo();

        if ("/login".equals(path)) {
            LoginRequest login = gson.fromJson(req.getReader(), LoginRequest.class);

            if (login == null || login.getCorreo() == null || login.getPassword() == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write(gson.toJson(new ApiResponse(false, "Datos incompletos", null)));
                return;
            }

            UsuarioSesion sesion = usuarioDAO.login(login.getCorreo(), login.getPassword());

            if (sesion != null) {
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write(gson.toJson(new ApiResponse(true, "Login exitoso", sesion)));
            } else {
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                resp.getWriter().write(gson.toJson(new ApiResponse(false, "Credenciales incorrectas", null)));
            }

        } else if ("/registro".equals(path)) {
            RegistroUsuarioRequest reg = gson.fromJson(req.getReader(), RegistroUsuarioRequest.class);

            if (usuarioDAO.existeCorreo(reg.getCorreo())) {
                resp.setStatus(HttpServletResponse.SC_CONFLICT);
                resp.getWriter().write(gson.toJson(new ApiResponse(false, "El correo ya está registrado", null)));
                return;
            }

            Persona p = new Persona();
            p.setNombre(reg.getNombre());
            p.setApellido(reg.getApellido());
            p.setDpi(reg.getDpi());
            p.setNumeroTelefonico(reg.getTelefono());
            p.setCorreoElectronico(reg.getCorreo());

            Rol rolEnum = Rol.valueOf(reg.getRol().toUpperCase());
            boolean ok = usuarioDAO.registrarUsuario(p, reg.getPassword(), rolEnum, reg.getNit());

            if (ok) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
                resp.getWriter().write(gson.toJson(new ApiResponse(true, "Usuario registrado", null)));
            } else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                resp.getWriter().write(gson.toJson(new ApiResponse(false, "Error al registrar en BD", null)));
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}