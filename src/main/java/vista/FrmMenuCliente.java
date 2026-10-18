/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;
import modelo.Cliente;
import modelo.UsuarioSesion;
import javax.swing.*;
import java.awt.*;
/**
 *
 * @author acabi
 */
public class FrmMenuCliente extends JFrame {

    private Cliente clienteLogueado;
    private UsuarioSesion usuarioSesion;

    // Constructors para soportar cualquier flujo de entrada
    public FrmMenuCliente() {
        initComponents();
        setLocationRelativeTo(null);
    }

    public FrmMenuCliente(UsuarioSesion usuarioSesion) {
        this.usuarioSesion = usuarioSesion;
        initComponents();
        setLocationRelativeTo(null);
    }

    public FrmMenuCliente(Cliente cliente) {
        this.clienteLogueado = cliente;
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("BAMBÚ Hotel & Nature Suites - Portal de Clientes");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(820, 520);
        setLayout(new BorderLayout());

        // Header Superior
        JPanel panelHeader = new JPanel(new BorderLayout());
        panelHeader.setBackground(new Color(34, 62, 42)); // Verde Bambú
        panelHeader.setPreferredSize(new Dimension(820, 80));
        panelHeader.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));

        String nombreMostrar = "Cliente";
        if (clienteLogueado != null) {
            nombreMostrar = clienteLogueado.getNombre() + " " + clienteLogueado.getApellido();
        } else if (usuarioSesion != null) {
            nombreMostrar = usuarioSesion.getCorreo();
        }

        JLabel lblBienvenida = new JLabel("Bienvenido/a, " + nombreMostrar);
        lblBienvenida.setFont(new Font("Georgia", Font.BOLD, 20));
        lblBienvenida.setForeground(Color.WHITE);

        // Crear el botón de Cerrar Sesión
        JButton btnCerrarSesion = new JButton("Cerrar Sesión");
        btnCerrarSesion.setBackground(new Color(180, 50, 50));
        btnCerrarSesion.setForeground(Color.WHITE);
        btnCerrarSesion.setFocusPainted(false);
        btnCerrarSesion.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Asignar el evento
        btnCerrarSesion.addActionListener(e -> cerrarSesion());

        panelHeader.add(lblBienvenida, BorderLayout.WEST);
        panelHeader.add(btnCerrarSesion, BorderLayout.EAST);
        add(panelHeader, BorderLayout.NORTH);

        // Grid Central con los Botones
        JPanel panelGrid = new JPanel(new GridLayout(1, 2, 25, 25));
        panelGrid.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        panelGrid.setBackground(new Color(245, 245, 240));

        JButton btnHabitaciones = crearBotonOpcion("Habitaciones y Precios", "Explorar habitaciones disponibles", "🏨", new Color(31, 110, 165));
        JButton btnMisReservas = crearBotonOpcion("Mis Reservaciones", "Consultar y realizar reservaciones", "📅", new Color(30, 130, 76));

        // Eventos funcionales
        btnHabitaciones.addActionListener(e -> {
            try {
                new FrmVerEstadoHabitaciones().setVisible(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al abrir Habitaciones: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnMisReservas.addActionListener(e -> {
            try {
                new FrmNuevaReservacion().setVisible(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al abrir Reservaciones: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panelGrid.add(btnHabitaciones);
        panelGrid.add(btnMisReservas);

        add(panelGrid, BorderLayout.CENTER);
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

    private JButton crearBotonOpcion(String titulo, String subtitulo, String icono, Color colorBorde) {
        String htmlText = "<html><center>"
                + "<font size='6' color='#1E2F20'>" + icono + "</font><br><br>"
                + "<font size='5' color='#1E2F20'><b>" + titulo + "</b></font><br><br>"
                + "<font size='3' color='#555555'>" + subtitulo + "</font>"
                + "</center></html>";

        JButton btn = new JButton(htmlText);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(colorBorde, 3),
                BorderFactory.createEmptyBorder(20, 15, 20, 15)
        ));

        return btn;
    }
}