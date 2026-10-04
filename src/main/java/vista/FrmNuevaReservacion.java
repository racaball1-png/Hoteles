/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;
import controladores.ReservacionController;
import dao.HabitacionDAO;
import modelo.Habitacion;
import modelo.Trabajador;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
/**
 *
 * @author acabi
 */
public class FrmNuevaReservacion extends JFrame {

    private static final Color COLOR_PRIMARIO = new Color(39, 64, 41);
    private static final Color COLOR_FONDO = new Color(247, 246, 242);
    private static final Color COLOR_PANEL = Color.WHITE;
    private static final Color COLOR_BORDE = new Color(220, 215, 203);
    private static final Color COLOR_ALERTA = new Color(153, 27, 27);
    private static final Color COLOR_EXITO = new Color(22, 101, 52);

    private static final Font FONT_TITULO = new Font("Georgia", Font.BOLD, 22);
    private static final Font FONT_SUBTITULO = new Font("SansSerif", Font.PLAIN, 12);
    private static final Font FONT_SECCION = new Font("Georgia", Font.BOLD, 14);
    private static final Font FONT_LABEL = new Font("SansSerif", Font.BOLD, 12);
    private static final Font FONT_INPUT = new Font("SansSerif", Font.PLAIN, 12);

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private final NumberFormat monedaFormat = NumberFormat.getCurrencyInstance(new Locale("es", "GT"));

    private JComboBox<HabitacionComboItem> cbxHabitaciones;
    private JSpinner spFechaEntrada, spFechaSalida, spAdultos, spNinos;
    private JLabel lblEstadoDisponibilidad, lblDetalleNoches, lblSubtotal, lblImpuestos, lblTotalPagar;

    private JTextField txtNombres, txtApellidos, txtCorreo, txtTelefono, txtNumDocumento;
    private JComboBox<String> cbxTipoDocumento;

    private JButton btnVerificar, btnConfirmarReserva, btnLimpiar;

    private Trabajador trabajadorActivo;
    private HabitacionDAO habitacionDAO;
    private ReservacionController reservacionController;

    public FrmNuevaReservacion() {
        this(null);
    }

    public FrmNuevaReservacion(Trabajador trabajador) {
        super("BAMBÚ Hoteles - Emisión de Reservaciones");
        this.trabajadorActivo = trabajador;
        this.habitacionDAO = new HabitacionDAO();
        this.reservacionController = new ReservacionController();

        configurarVentana();
        construirInterfaz();
        cargarHabitacionesDesdeBD();
        verificarDisponibilidadTiempoReal(false);
    }

    private void configurarVentana() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(980, 800);
        setLocationRelativeTo(null);
        getContentPane().setBackground(COLOR_FONDO);
    }

    private void construirInterfaz() {
        setLayout(new BorderLayout(0, 0));
        add(crearPanelEncabezado(), BorderLayout.NORTH);

        JPanel panelCuerpo = new JPanel(new GridBagLayout());
        panelCuerpo.setBackground(COLOR_FONDO);
        panelCuerpo.setBorder(new EmptyBorder(15, 20, 15, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(8, 8, 8, 8);

        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setLayout(new BoxLayout(panelIzquierdo, BoxLayout.Y_AXIS));
        panelIzquierdo.setOpaque(false);
        panelIzquierdo.add(crearPanelSeleccionHabitacion());
        panelIzquierdo.add(Box.createVerticalStrut(12));
        panelIzquierdo.add(crearPanelFechasYOcupacion());
        panelIzquierdo.add(Box.createVerticalStrut(12));
        panelIzquierdo.add(crearPanelDatosHuesped());

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.58; gbc.weighty = 1.0;
        panelCuerpo.add(panelIzquierdo, gbc);

        JPanel panelDerecho = new JPanel();
        panelDerecho.setLayout(new BoxLayout(panelDerecho, BoxLayout.Y_AXIS));
        panelDerecho.setOpaque(false);
        panelDerecho.add(crearPanelDisponibilidadLiquidacion());

        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 0.42; gbc.weighty = 1.0;
        panelCuerpo.add(panelDerecho, gbc);

        JScrollPane scrollPane = new JScrollPane(panelCuerpo);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);

        add(crearPanelBotonesAccion(), BorderLayout.SOUTH);
    }

    private JPanel crearPanelEncabezado() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(COLOR_PRIMARIO);
        header.setBorder(new EmptyBorder(16, 24, 16, 24));

        JPanel textoPanel = new JPanel(new GridLayout(2, 1, 0, 2));
        textoPanel.setOpaque(false);

        JLabel lblTitulo = new JLabel("HOTEL Y RESORT BAMBÚ");
        lblTitulo.setFont(FONT_TITULO);
        lblTitulo.setForeground(Color.WHITE);

        JLabel lblSub = new JLabel("Atendido por: " + (trabajadorActivo != null ? trabajadorActivo.getNombre() : "Recepción"));
        lblSub.setFont(FONT_SUBTITULO);
        lblSub.setForeground(new Color(196, 222, 198));

        textoPanel.add(lblTitulo);
        textoPanel.add(lblSub);
        header.add(textoPanel, BorderLayout.WEST);
        return header;
    }

    private JPanel crearPanelSeleccionHabitacion() {
        JPanel panel = crearContenedorTarjeta("1. Habitación Disponible");
        panel.setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.HORIZONTAL;
        g.insets = new Insets(6, 6, 6, 6);

        JLabel lblHabitacion = new JLabel("Seleccionar Habitación:");
        lblHabitacion.setFont(FONT_LABEL);
        g.gridx = 0; g.gridy = 0;
        panel.add(lblHabitacion, g);

        cbxHabitaciones = new JComboBox<>();
        cbxHabitaciones.setFont(FONT_INPUT);
        cbxHabitaciones.addActionListener(e -> verificarDisponibilidadTiempoReal(false));
        g.gridx = 1; g.gridy = 0; g.gridwidth = 2;
        panel.add(cbxHabitaciones, g);

        return panel;
    }

    private JPanel crearPanelFechasYOcupacion() {
        JPanel panel = crearContenedorTarjeta("2. Fechas de Estancia y Huéspedes");
        panel.setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.HORIZONTAL;
        g.insets = new Insets(4, 6, 4, 6);

        Date hoy = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(hoy);
        cal.add(Calendar.DAY_OF_MONTH, 2);
        Date manana = cal.getTime();

        spFechaEntrada = new JSpinner(new SpinnerDateModel(hoy, hoy, null, Calendar.DAY_OF_MONTH));
        spFechaEntrada.setEditor(new JSpinner.DateEditor(spFechaEntrada, "dd/MM/yyyy"));
        spFechaEntrada.setFont(FONT_INPUT);

        spFechaSalida = new JSpinner(new SpinnerDateModel(manana, hoy, null, Calendar.DAY_OF_MONTH));
        spFechaSalida.setEditor(new JSpinner.DateEditor(spFechaSalida, "dd/MM/yyyy"));
        spFechaSalida.setFont(FONT_INPUT);

        g.gridx = 0; g.gridy = 0; panel.add(new JLabel("Check-in:"), g);
        g.gridx = 1; g.gridy = 0; panel.add(spFechaEntrada, g);
        g.gridx = 2; g.gridy = 0; panel.add(new JLabel("Check-out:"), g);
        g.gridx = 3; g.gridy = 0; panel.add(spFechaSalida, g);

        g.gridx = 0; g.gridy = 1; panel.add(new JLabel("Adultos:"), g);
        spAdultos = new JSpinner(new SpinnerNumberModel(2, 1, 6, 1));
        g.gridx = 1; g.gridy = 1; panel.add(spAdultos, g);

        g.gridx = 2; g.gridy = 1; panel.add(new JLabel("Niños:"), g);
        spNinos = new JSpinner(new SpinnerNumberModel(0, 0, 4, 1));
        g.gridx = 3; g.gridy = 1; panel.add(spNinos, g);

        spFechaEntrada.addChangeListener(e -> verificarDisponibilidadTiempoReal(false));
        spFechaSalida.addChangeListener(e -> verificarDisponibilidadTiempoReal(false));

        return panel;
    }

    private JPanel crearPanelDatosHuesped() {
        JPanel panel = crearContenedorTarjeta("3. Datos del Huésped Titular");
        panel.setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.HORIZONTAL;
        g.insets = new Insets(4, 6, 4, 6);

        g.gridx = 0; g.gridy = 0; panel.add(new JLabel("Nombres *"), g);
        txtNombres = new JTextField(12);
        g.gridx = 1; g.gridy = 0; panel.add(txtNombres, g);

        g.gridx = 2; g.gridy = 0; panel.add(new JLabel("Apellidos *"), g);
        txtApellidos = new JTextField(12);
        g.gridx = 3; g.gridy = 0; panel.add(txtApellidos, g);

        g.gridx = 0; g.gridy = 1; panel.add(new JLabel("Correo *"), g);
        txtCorreo = new JTextField(12);
        g.gridx = 1; g.gridy = 1; panel.add(txtCorreo, g);

        g.gridx = 2; g.gridy = 1; panel.add(new JLabel("Teléfono *"), g);
        txtTelefono = new JTextField(12);
        g.gridx = 3; g.gridy = 1; panel.add(txtTelefono, g);

        g.gridx = 0; g.gridy = 2; panel.add(new JLabel("Documento *"), g);
        cbxTipoDocumento = new JComboBox<>(new String[]{"DPI (Guatemala)", "Pasaporte"});
        g.gridx = 1; g.gridy = 2; panel.add(cbxTipoDocumento, g);

        g.gridx = 2; g.gridy = 2; panel.add(new JLabel("No. DPI/Doc *"), g);
        txtNumDocumento = new JTextField(12);
        g.gridx = 3; g.gridy = 2; panel.add(txtNumDocumento, g);

        return panel;
    }

    private JPanel crearPanelDisponibilidadLiquidacion() {
        JPanel panel = crearContenedorTarjeta("Resumen y Liquidación");
        panel.setLayout(new BorderLayout(10, 10));

        JPanel panelEstado = new JPanel(new BorderLayout());
        panelEstado.setBackground(new Color(238, 246, 237));
        panelEstado.setBorder(new CompoundBorder(
                new LineBorder(new Color(185, 219, 188), 1, true),
                new EmptyBorder(10, 12, 10, 12)
        ));

        lblEstadoDisponibilidad = new JLabel("Consultando la base de datos...", JLabel.CENTER);
        lblEstadoDisponibilidad.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblEstadoDisponibilidad.setForeground(COLOR_EXITO);
        panelEstado.add(lblEstadoDisponibilidad, BorderLayout.CENTER);

        panel.add(panelEstado, BorderLayout.NORTH);

        JPanel panelDesglose = new JPanel(new GridBagLayout());
        panelDesglose.setOpaque(false);
        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.HORIZONTAL;
        g.insets = new Insets(6, 6, 6, 6);

        g.gridx = 0; g.gridy = 0; panelDesglose.add(new JLabel("Estancia:"), g);
        lblDetalleNoches = new JLabel("0 noches", JLabel.RIGHT);
        g.gridx = 1; g.gridy = 0; panelDesglose.add(lblDetalleNoches, g);

        g.gridx = 0; g.gridy = 1; panelDesglose.add(new JLabel("Subtotal Hospedaje:"), g);
        lblSubtotal = new JLabel("Q 0.00", JLabel.RIGHT);
        g.gridx = 1; g.gridy = 1; panelDesglose.add(lblSubtotal, g);

        g.gridx = 0; g.gridy = 2; panelDesglose.add(new JLabel("Impuestos (IVA 12% + INGUAT 10%):"), g);
        lblImpuestos = new JLabel("Q 0.00", JLabel.RIGHT);
        g.gridx = 1; g.gridy = 2; panelDesglose.add(lblImpuestos, g);

        g.gridx = 0; g.gridy = 3; panelDesglose.add(new JLabel("TOTAL ESTIMADO:"), g);
        lblTotalPagar = new JLabel("Q 0.00", JLabel.RIGHT);
        lblTotalPagar.setFont(new Font("Georgia", Font.BOLD, 18));
        lblTotalPagar.setForeground(COLOR_PRIMARIO);
        g.gridx = 1; g.gridy = 3; panelDesglose.add(lblTotalPagar, g);

        panel.add(panelDesglose, BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelBotonesAccion() {
        JPanel bar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 12));
        bar.setBackground(new Color(236, 233, 224));

        btnLimpiar = new JButton("Limpiar Campos");
        btnLimpiar.addActionListener(e -> limpiarFormulario());

        btnVerificar = new JButton("Verificar");
        btnVerificar.addActionListener(e -> verificarDisponibilidadTiempoReal(true));

        btnConfirmarReserva = new JButton("Confirmar Reserva");
        btnConfirmarReserva.setUI(new javax.swing.plaf.basic.BasicButtonUI()); // <-- Forzar renderizado en Windows
        btnConfirmarReserva.setFont(new Font("SansSerif", Font.BOLD, 13));
        btnConfirmarReserva.setBackground(COLOR_PRIMARIO);
        btnConfirmarReserva.setForeground(Color.WHITE);
        btnConfirmarReserva.setFocusPainted(false);
        btnConfirmarReserva.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(COLOR_PRIMARIO, 1),
                new EmptyBorder(8, 15, 8, 15)
        ));
        btnConfirmarReserva.addActionListener(e -> procesarReserva());

        bar.add(btnLimpiar);
        bar.add(btnVerificar);
        bar.add(btnConfirmarReserva);

        return bar;
    }

    private JPanel crearContenedorTarjeta(String titulo) {
        JPanel panel = new JPanel();
        panel.setBackground(COLOR_PANEL);
        TitledBorder border = BorderFactory.createTitledBorder(
                new LineBorder(COLOR_BORDE, 1, true),
                titulo, TitledBorder.LEFT, TitledBorder.TOP, FONT_SECCION, COLOR_PRIMARIO
        );
        panel.setBorder(new CompoundBorder(border, new EmptyBorder(8, 10, 10, 10)));
        return panel;
    }

    private void cargarHabitacionesDesdeBD() {
        cbxHabitaciones.removeAllItems();
        List<Habitacion> lista = habitacionDAO.obtenerHabitacionesDisponibles();
        for (Habitacion h : lista) {
            cbxHabitaciones.addItem(new HabitacionComboItem(h));
        }
    }

    private boolean verificarDisponibilidadTiempoReal(boolean mostrarMensajeManual) {
        Date fEntrada = (Date) spFechaEntrada.getValue();
        Date fSalida = (Date) spFechaSalida.getValue();

        if (!fSalida.after(fEntrada)) {
            lblEstadoDisponibilidad.setText("Fecha de salida debe ser posterior a entrada.");
            lblEstadoDisponibilidad.setForeground(COLOR_ALERTA);
            lblTotalPagar.setText("Q 0.00");
            btnConfirmarReserva.setEnabled(false);

            if (mostrarMensajeManual) {
                JOptionPane.showMessageDialog(this, "La fecha de Check-out debe ser posterior al Check-in.", "Verificación de Fechas", JOptionPane.WARNING_MESSAGE);
            }
            return false;
        }

        long diff = fSalida.getTime() - fEntrada.getTime();
        int noches = (int) (diff / (1000 * 60 * 60 * 24));
        if (noches <= 0) noches = 1;
        lblDetalleNoches.setText(noches + (noches == 1 ? " noche" : " noches"));

        HabitacionComboItem item = (HabitacionComboItem) cbxHabitaciones.getSelectedItem();
        if (item == null) {
            lblEstadoDisponibilidad.setText("No hay habitaciones disponibles.");
            lblEstadoDisponibilidad.setForeground(COLOR_ALERTA);
            btnConfirmarReserva.setEnabled(false);
            if (mostrarMensajeManual) {
                JOptionPane.showMessageDialog(this, "No hay habitación seleccionada.", "Verificación", JOptionPane.WARNING_MESSAGE);
            }
            return false;
        }

        double precioNoche = item.getHabitacion().getPrecioNoche();
        double subtotal = precioNoche * noches;
        double impuestos = subtotal * 0.22;
        double total = subtotal + impuestos;

        lblSubtotal.setText(monedaFormat.format(subtotal));
        lblImpuestos.setText(monedaFormat.format(impuestos));
        lblTotalPagar.setText(monedaFormat.format(total));

        lblEstadoDisponibilidad.setText("✓ Habitación Seleccionada (ID: " + item.getHabitacion().getIdHabitacion() + ")");
        lblEstadoDisponibilidad.setForeground(COLOR_EXITO);
        btnConfirmarReserva.setEnabled(true);

        if (mostrarMensajeManual) {
            JOptionPane.showMessageDialog(this, "✓ Habitación y cálculo de liquidación verificados correctamente.", "Verificación Exitosa", JOptionPane.INFORMATION_MESSAGE);
        }
        return true;
    }

    private void procesarReserva() {
        if (txtNombres.getText().trim().isEmpty() || txtApellidos.getText().trim().isEmpty() ||
            txtCorreo.getText().trim().isEmpty() || txtNumDocumento.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor complete los datos obligatorios del huésped.", "Campos requeridos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        HabitacionComboItem item = (HabitacionComboItem) cbxHabitaciones.getSelectedItem();
        if (item == null) return;

        Date fEntrada = (Date) spFechaEntrada.getValue();
        Date fSalida = (Date) spFechaSalida.getValue();

        LocalDateTime ldtEntrada = fEntrada.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime ldtSalida = fSalida.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        int idTrabajador = (trabajadorActivo != null) ? trabajadorActivo.getIdTrabajador() : 1;

        boolean exito = reservacionController.crearReservacionConDatosHuesped(
                txtNombres.getText().trim(),
                txtApellidos.getText().trim(),
                txtCorreo.getText().trim(),
                txtTelefono.getText().trim(),
                txtNumDocumento.getText().trim(),
                idTrabajador,
                item.getHabitacion().getIdHabitacion(),
                ldtEntrada,
                ldtSalida,
                item.getHabitacion().getPrecioNoche()
        );

        if (exito) {
            JOptionPane.showMessageDialog(this, "¡Reserva guardada exitosamente en la Base de Datos!", "Reserva Confirmada", JOptionPane.INFORMATION_MESSAGE);
            limpiarFormulario();
            cargarHabitacionesDesdeBD();
        } else {
            JOptionPane.showMessageDialog(this, "Ocurrió un error al registrar la reservación. Verifique la conexión o datos del cliente.", "Error MySQL", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarFormulario() {
        txtNombres.setText("");
        txtApellidos.setText("");
        txtCorreo.setText("");
        txtTelefono.setText("");
        txtNumDocumento.setText("");
        spAdultos.setValue(2);
        spNinos.setValue(0);
        verificarDisponibilidadTiempoReal(false);
    }

    static class HabitacionComboItem {
        private final Habitacion habitacion;

        public HabitacionComboItem(Habitacion habitacion) {
            this.habitacion = habitacion;
        }

        public Habitacion getHabitacion() { 
            return habitacion; 
        }

        @Override
        public String toString() {
            return "Hab. " + habitacion.getNumeroHabitacion() + " (" + habitacion.getTipo() + ") - Q" + habitacion.getPrecioNoche() + "/noche";
        }
    }
}