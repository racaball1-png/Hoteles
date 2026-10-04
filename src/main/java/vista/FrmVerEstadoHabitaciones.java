/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;
import dao.HabitacionDAO;
import modelo.Habitacion;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;;

/**
 *
 * @author acabi
 */
public class FrmVerEstadoHabitaciones extends JFrame {

    private static final Color COLOR_PRIMARIO = new Color(39, 64, 41);
    private static final Color COLOR_FONDO = new Color(247, 246, 242);
    private static final Color COLOR_TEXTO = new Color(30, 47, 32);

    private JTable tablaHabitaciones;
    private DefaultTableModel modeloTabla;
    private HabitacionDAO habitacionDAO;
    private JButton btnRefrescar;

    public FrmVerEstadoHabitaciones() {
        super("BAMBÚ Hotel & Nature Suites - Estado de Habitaciones");
        this.habitacionDAO = new HabitacionDAO();

        configurarVentana();
        construirInterfaz();
        cargarDatos();
    }

    private void configurarVentana() {
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(COLOR_FONDO);
        setLayout(new BorderLayout());
    }

    private void construirInterfaz() {
        // Panel Encabezado
        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(COLOR_PRIMARIO);
        pnlHeader.setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel lblTitulo = new JLabel("Estado Actual de Habitaciones");
        lblTitulo.setFont(new Font("Georgia", Font.BOLD, 18));
        lblTitulo.setForeground(Color.WHITE);

        JLabel lblSub = new JLabel("Consulta de disponibilidad en tiempo real");
        lblSub.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblSub.setForeground(new Color(196, 222, 198));

        JPanel pnlTexto = new JPanel(new GridLayout(2, 1));
        pnlTexto.setOpaque(false);
        pnlTexto.add(lblTitulo);
        pnlTexto.add(lblSub);

        pnlHeader.add(pnlTexto, BorderLayout.WEST);

        btnRefrescar = new JButton("Actualizar");
        btnRefrescar.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnRefrescar.setBackground(new Color(230, 238, 229));
        btnRefrescar.setForeground(COLOR_PRIMARIO);
        btnRefrescar.setFocusPainted(false);
        btnRefrescar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRefrescar.addActionListener(e -> cargarDatos());
        pnlHeader.add(btnRefrescar, BorderLayout.EAST);

        add(pnlHeader, BorderLayout.NORTH);

        // Configuración de Tabla
        String[] columnas = {"ID Room", "No. Habitación", "Nivel / Piso", "Capacidad", "Precio/Noche (GTQ)", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tabla no editable directamente
            }
        };

        tablaHabitaciones = new JTable(modeloTabla);
        tablaHabitaciones.setFont(new Font("SansSerif", Font.PLAIN, 12));
        tablaHabitaciones.setRowHeight(28);
        tablaHabitaciones.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        tablaHabitaciones.getTableHeader().setBackground(new Color(220, 215, 203));
        tablaHabitaciones.getTableHeader().setForeground(COLOR_TEXTO);

        // Centrar datos en las celdas
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tablaHabitaciones.getColumnCount(); i++) {
            tablaHabitaciones.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(tablaHabitaciones);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        scrollPane.getViewport().setBackground(COLOR_FONDO);

        add(scrollPane, BorderLayout.CENTER);
    }

    private void cargarDatos() {
        modeloTabla.setRowCount(0); // Limpiar filas anteriores
        List<Habitacion> lista = habitacionDAO.listarHabitaciones();

        for (Habitacion h : lista) {
            String estadoTexto;
            switch (h.getIdEstadoHabitacion()) {
                case 1: estadoTexto = "Disponible"; break;
                case 2: estadoTexto = "Ocupada"; break;
                case 3: estadoTexto = "En Mantenimiento"; break;
                default: estadoTexto = "Estado " + h.getIdEstadoHabitacion(); break;
            }

            modeloTabla.addRow(new Object[]{
                h.getIdRoom(),
                h.getNumeroHabitacion(),
                "Piso " + h.getNivelPiso(),
                h.getCapacidadPersona() + " personas",
                "Q " + String.format("%.2f", h.getPrecioNoche()),
                estadoTexto
            });
        }
    }
}
