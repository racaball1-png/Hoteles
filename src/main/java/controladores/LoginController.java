/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;
import dao.UsuarioDAO;
import modelo.Persona;
import modelo.UsuarioSesion;
import modelo.UsuarioSesion.Rol;
import vista.FrmLogin;
import vista.FrmMenuCliente;
import vista.FrmMenuPrincipal;
import javax.swing.JOptionPane;
/**
 *
 * @author acabi
 */
public class LoginController {

    private FrmLogin vista;
    private UsuarioDAO dao;

    public LoginController(FrmLogin vista) {
        this.vista = vista;
        this.dao = new UsuarioDAO();
    }

    public LoginController() {
        this.dao = new UsuarioDAO();
    }

    public void autenticar(String correo, String password) {
        if (correo.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor complete todos los campos.", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        UsuarioSesion sesion = dao.login(correo, password);

        if (sesion != null) {
            JOptionPane.showMessageDialog(null, "¡Bienvenido/a " + sesion.getNombre() + "! (" + sesion.getRol() + ")");
            
            if (vista != null) {
                vista.dispose();
            }

            if (sesion.getRol() == Rol.TRABAJADOR) {
                FrmMenuPrincipal menu = new FrmMenuPrincipal(sesion.getCorreo());
                menu.setVisible(true);
            } else {
                new FrmMenuCliente(sesion).setVisible(true);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Credenciales incorrectas o usuario no registrado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean registrarNuevoUsuario(String nombre, String apellido, String correo, String telefono, String dpi, String password, Rol rol, String nit) {
        if (dao.existeCorreo(correo)) {
            JOptionPane.showMessageDialog(null, "El correo electrónico ya está registrado.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        Persona p = new Persona();
        p.setNombre(nombre);
        p.setApellido(apellido);
        p.setCorreoElectronico(correo);
        p.setNumeroTelefonico(telefono);
        p.setDpi(dpi);

        boolean exito = dao.registrarUsuario(p, password, rol, nit);
        if (exito) {
            JOptionPane.showMessageDialog(null, "Cuenta registrada exitosamente como " + rol + ". Ya puedes iniciar sesión.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Error al guardar el usuario.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return exito;
    }
}