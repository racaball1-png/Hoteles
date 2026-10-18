/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
/**
 *
 * @author acabi
 */
public class FrmMenuPrincipal extends JFrame {

    private static final Color COLOR_PRIMARIO = new Color(39, 64, 41);
    private static final Color COLOR_FONDO = new Color(247, 246, 242);

    public FrmMenuPrincipal(String correoUsuario) {
        super("BAMBÚ Hotel & Nature Suites - Panel Principal");
        configurarVentana();
        construirInterfaz(correoUsuario);
    }

    private void configurarVentana() {
        setSize(980, 620);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(COLOR_FONDO);
        setLayout(new BorderLayout());
    }

    private void construirInterfaz(String correoUsuario) {
        // --- Encabezado ---
        JPanel pnlHeader = new JPanel(new GridLayout(2, 1));
        pnlHeader.setBackground(COLOR_PRIMARIO);
        pnlHeader.setBorder(new EmptyBorder(15, 25, 15, 25));

        JLabel lblTitulo = new JLabel("HOTEL Y RESORT BAMBÚ");
        lblTitulo.setFont(new Font("Georgia", Font.BOLD, 22));
        lblTitulo.setForeground(Color.WHITE);

        JLabel lblSesion = new JLabel("Sistema de Gestión y Control Interno | Sesión: " + correoUsuario);
        lblSesion.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblSesion.setForeground(new Color(196, 222, 198));

        pnlHeader.add(lblTitulo);
        pnlHeader.add(lblSesion);
        add(pnlHeader, BorderLayout.NORTH);

        // --- Panel de Tarjetas (2 Filas x 3 Columnas) ---
        JPanel pnlGrid = new JPanel(new GridLayout(2, 3, 20, 20));
        pnlGrid.setBackground(COLOR_FONDO);
        pnlGrid.setBorder(new EmptyBorder(25, 25, 25, 25));

        JButton btnNuevaReserva = crearBotonMenu("Nueva Reservación", "+", new Color(41, 128, 185));
        JButton btnEstadoHabitaciones = crearBotonMenu("Estado Habitaciones", "🛏", new Color(39, 174, 96));
        JButton btnGestionarReservas = crearBotonMenu("Gestionar Reservas", "🧳", new Color(142, 68, 173));
        JButton btnFacturacion = crearBotonMenu("Facturación", "🧾", new Color(211, 84, 0));
        JButton btnRegistroRoles = crearBotonMenu("Registro y Roles", "👤", new Color(192, 57, 43));
        JButton btnCerrarSesion = crearBotonMenu("Cerrar Sesión", "🚪", new Color(127, 140, 141));

        // Acciones de los botones
        btnNuevaReserva.addActionListener(e -> new FrmNuevaReservacion().setVisible(true));
        btnEstadoHabitaciones.addActionListener(e -> new FrmVerEstadoHabitaciones().setVisible(true));
        btnGestionarReservas.addActionListener(e -> new FrmGestionReservaciones().setVisible(true));
        btnFacturacion.addActionListener(e -> new FrmGestionReservaciones().setVisible(true));

        btnRegistroRoles.addActionListener(e -> {
            FrmRegistroUsuario frm = new FrmRegistroUsuario(true);
            frm.setVisible(true);
        });

        // Evento para Cerrar Sesión
        btnCerrarSesion.addActionListener(e -> cerrarSesion());

        // Agregar las 6 tarjetas al panel
        pnlGrid.add(btnNuevaReserva);
        pnlGrid.add(btnEstadoHabitaciones);
        pnlGrid.add(btnGestionarReservas);
        pnlGrid.add(btnFacturacion);
        pnlGrid.add(btnRegistroRoles);
        pnlGrid.add(btnCerrarSesion); // Agregado al grid

        add(pnlGrid, BorderLayout.CENTER);
    }

    private void cerrarSesion() {
        int confirmacion = JOptionPane.showConfirmDialog(
            this, 
            "¿Está seguro de que desea cerrar sesión?", 
            "Confirmar Salida", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.QUESTION_MESSAGE
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                FrmLogin login = new FrmLogin();
                login.setVisible(true);
                login.setLocationRelativeTo(null);
                this.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, 
                    "Error al abrir la pantalla de inicio de sesión:\n" + ex.getMessage(), 
                    "Error al cerrar sesión", 
                    JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }

    private JButton crearBotonMenu(String texto, String icono, Color colorBorde) {
        JButton btn = new JButton();
        btn.setLayout(new BorderLayout());
        btn.setBackground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(colorBorde, 2),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel lblIcono = new JLabel(icono, SwingConstants.CENTER);
        lblIcono.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 36));

        JLabel lblTexto = new JLabel(texto, SwingConstants.CENTER);
        lblTexto.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblTexto.setForeground(new Color(40, 40, 40));

        btn.add(lblIcono, BorderLayout.CENTER);
        btn.add(lblTexto, BorderLayout.SOUTH);

        return btn;
    }
}