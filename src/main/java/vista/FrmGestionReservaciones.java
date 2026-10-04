/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;
import controladores.ReservacionController;
import dao.ReservacionDAO;
import modelo.Reservacion;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author acabi
 */
public class FrmGestionReservaciones extends JFrame {

    private static final Color COLOR_PRIMARIO = new Color(39, 64, 41);
    private static final Color COLOR_FONDO = new Color(247, 246, 242);
    private static final Color COLOR_TEXTO = new Color(30, 47, 32);
    private static final Color COLOR_ROJO = new Color(153, 27, 27);
    private static final Color COLOR_VERDE_FACTURA = new Color(39, 128, 60);

    private JTable tablaReservas;
    private DefaultTableModel modeloTabla;
    private ReservacionDAO reservacionDAO;
    private ReservacionController reservacionController;
    private JButton btnRefrescar, btnEliminar, btnFacturar;

    public FrmGestionReservaciones() {
        super("BAMBÚ Hotel & Nature Suites - Gestión de Reservaciones");
        this.reservacionDAO = new ReservacionDAO();
        this.reservacionController = new ReservacionController();

        configurarVentana();
        construirInterfaz();
        cargarDatos();
    }

    private void configurarVentana() {
        setSize(950, 520);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(COLOR_FONDO);
        setLayout(new BorderLayout());
    }

    private void construirInterfaz() {
        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(COLOR_PRIMARIO);
        pnlHeader.setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel lblTitulo = new JLabel("Listado General de Reservaciones");
        lblTitulo.setFont(new Font("Georgia", Font.BOLD, 18));
        lblTitulo.setForeground(Color.WHITE);

        JLabel lblSub = new JLabel("Consulta, verificación, facturación y cancelación de reservas activas");
        lblSub.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblSub.setForeground(new Color(196, 222, 198));

        JPanel pnlTexto = new JPanel(new GridLayout(2, 1));
        pnlTexto.setOpaque(false);
        pnlTexto.add(lblTitulo);
        pnlTexto.add(lblSub);

        pnlHeader.add(pnlTexto, BorderLayout.WEST);

        JPanel pnlBotonesHeader = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        pnlBotonesHeader.setOpaque(false);

        btnRefrescar = new JButton("Actualizar Tabla");
        btnRefrescar.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnRefrescar.setBackground(new Color(230, 238, 229));
        btnRefrescar.setForeground(COLOR_PRIMARIO);
        btnRefrescar.setFocusPainted(false);
        btnRefrescar.addActionListener(e -> cargarDatos());

       btnFacturar = new JButton("Facturar");
       btnFacturar.setUI(new javax.swing.plaf.basic.BasicButtonUI()); // <-- Habilita el color de fondo en Windows
       btnFacturar.setFont(new Font("SansSerif", Font.BOLD, 12));
       btnFacturar.setBackground(COLOR_VERDE_FACTURA);
       btnFacturar.setForeground(Color.WHITE);
       btnFacturar.setFocusPainted(false);
       btnFacturar.addActionListener(e -> abrirFacturacion());

       btnEliminar = new JButton("Cancelar Reserva");
       btnEliminar.setUI(new javax.swing.plaf.basic.BasicButtonUI()); // <-- Habilita el color de fondo en Windows
       btnEliminar.setFont(new Font("SansSerif", Font.BOLD, 12));
       btnEliminar.setBackground(COLOR_ROJO);
       btnEliminar.setForeground(Color.WHITE);
       btnEliminar.setFocusPainted(false);
       btnEliminar.addActionListener(e -> eliminarReservaSeleccionada());

        pnlBotonesHeader.add(btnRefrescar);
        pnlBotonesHeader.add(btnFacturar);
        pnlBotonesHeader.add(btnEliminar);

        pnlHeader.add(pnlBotonesHeader, BorderLayout.EAST);
        add(pnlHeader, BorderLayout.NORTH);

        String[] columnas = {"ID Reserva", "ID Cliente", "ID Habitación", "Entrada", "Salida", "Monto Total", "Estado ID"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaReservas = new JTable(modeloTabla);
        tablaReservas.setFont(new Font("SansSerif", Font.PLAIN, 12));
        tablaReservas.setRowHeight(28);
        tablaReservas.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        tablaReservas.getTableHeader().setBackground(new Color(220, 215, 203));
        tablaReservas.getTableHeader().setForeground(COLOR_TEXTO);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tablaReservas.getColumnCount(); i++) {
            tablaReservas.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(tablaReservas);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        scrollPane.getViewport().setBackground(COLOR_FONDO);

        add(scrollPane, BorderLayout.CENTER);
    }

    private void cargarDatos() {
        modeloTabla.setRowCount(0);
        List<Reservacion> lista = reservacionDAO.listarReservaciones();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        for (Reservacion r : lista) {
            String fEntrada = (r.getFechaEntrada() != null) ? r.getFechaEntrada().format(fmt) : "N/A";
            String fSalida = (r.getFechaSalida() != null) ? r.getFechaSalida().format(fmt) : "N/A";

            modeloTabla.addRow(new Object[]{
                r.getIdReservacion(),
                r.getIdCliente(),
                r.getIdRoom(),
                fEntrada,
                fSalida,
                "Q " + String.format("%.2f", r.getMontoTotal()),
                r.getIdEstadoReserva() == 1 ? "Confirmada" : "Cancelada"
            });
        }
    }

    private void abrirFacturacion() {
    int filaSeleccionada = tablaReservas.getSelectedRow();
    
    if (filaSeleccionada >= 0) {
        // Validar si la reservación está cancelada
        String estado = tablaReservas.getValueAt(filaSeleccionada, 6).toString();
        if ("Cancelada".equalsIgnoreCase(estado)) {
            JOptionPane.showMessageDialog(
                this, 
                "No se puede facturar una reservación que ha sido cancelada.", 
                "Acción no permitida", 
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int idReservacion = Integer.parseInt(tablaReservas.getValueAt(filaSeleccionada, 0).toString());
        int idCliente = Integer.parseInt(tablaReservas.getValueAt(filaSeleccionada, 1).toString());
        String nombreCliente = "Cliente ID #" + idCliente;

        // Limpiar el formato de moneda ("Q 950.00" -> 950.00)
        String montoStr = tablaReservas.getValueAt(filaSeleccionada, 5).toString();
        montoStr = montoStr.replace("Q", "").replace(" ", "").replace(",", "").trim();
        double montoTotal = Double.parseDouble(montoStr);

        // Abrir la ventana de factura solo si está Confirmada
        FrmFactura frm = new FrmFactura(idReservacion, idCliente, nombreCliente, montoTotal);
        frm.setVisible(true);
    } else {
        JOptionPane.showMessageDialog(this, "Seleccione una reservación de la tabla para facturar.", "Aviso", JOptionPane.WARNING_MESSAGE);
    }
}

    private void eliminarReservaSeleccionada() {
        int fila = tablaReservas.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Por favor seleccione una reservación de la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idReserva = (int) modeloTabla.getValueAt(fila, 0);
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "¿Está seguro de que desea cancelar/eliminar la reservación ID #" + idReserva + "?",
            "Confirmar Cancelación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            boolean exito = reservacionController.cancelarReservacion(idReserva);
            if (exito) {
                JOptionPane.showMessageDialog(this, "Reservación cancelada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarDatos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al cancelar la reservación.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}