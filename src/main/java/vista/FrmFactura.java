/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;
import dao.FacturaDAO;
import modelo.Factura;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.text.DecimalFormat;
import java.time.LocalDateTime;

/**
 *
 * @author acabi
 */
public class FrmFactura extends JFrame {

    private int idReservacion;
    private int idCliente;
    private double montoBase;

    private JTextField txtNit;
    private JTextField txtNombreFactura;
    private JComboBox<String> cbFormaPago;
    private JTextArea txtDescripcion;
    private JLabel lblSubtotal;
    private JLabel lblImpuesto;
    private JLabel lblTotal;
    private JButton btnEmitirFactura;
    private JButton btnCancelar;

    private DecimalFormat df = new DecimalFormat("Q#,##0.00");

    public FrmFactura(int idReservacion, int idCliente, String nombreCliente, double montoBase) {
        this.idReservacion = idReservacion;
        this.idCliente = idCliente;
        this.montoBase = montoBase;

        initComponents(nombreCliente);
        calcularValores();
    }

    private void initComponents(String nombreCliente) {
        setTitle("Módulo de Facturación - Hotel Bambú");
        setSize(520, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // --- Panel Encabezado ---
        JPanel panelHeader = new JPanel();
        panelHeader.setBackground(new Color(41, 128, 185));
        JLabel lblTitulo = new JLabel("EMISIÓN DE FACTURA");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setForeground(Color.WHITE);
        panelHeader.add(lblTitulo);
        add(panelHeader, BorderLayout.NORTH);

        // --- Panel Central ---
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
        panelCentral.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        // 1. Opciones de Datos y Pago
        JPanel panelPago = new JPanel(new GridLayout(3, 2, 8, 8));
        panelPago.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "1. Opciones de Pago y Receptor", TitledBorder.LEFT, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 12)));

        panelPago.add(new JLabel("NIT / C/F:"));
        txtNit = new JTextField("C/F");
        panelPago.add(txtNit);

        panelPago.add(new JLabel("A nombre de:"));
        txtNombreFactura = new JTextField(nombreCliente != null ? nombreCliente : "Consumidor Final");
        panelPago.add(txtNombreFactura);

        panelPago.add(new JLabel("Forma de Pago:"));
        cbFormaPago = new JComboBox<>(new String[]{"1 - Efectivo", "2 - Tarjeta de Crédito / Débito", "3 - Transferencia Bancaria"});
        panelPago.add(cbFormaPago);

        panelCentral.add(panelPago);
        panelCentral.add(Box.createVerticalStrut(10));

        // 2. Descripción de Factura
        JPanel panelDetalle = new JPanel(new BorderLayout());
        panelDetalle.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "2. Descripción de la Factura", TitledBorder.LEFT, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 12)));

        txtDescripcion = new JTextArea(6, 30);
        txtDescripcion.setEditable(false);
        txtDescripcion.setFont(new Font("Monospaced", Font.PLAIN, 12));
        panelDetalle.add(new JScrollPane(txtDescripcion), BorderLayout.CENTER);

        panelCentral.add(panelDetalle);
        panelCentral.add(Box.createVerticalStrut(10));

        // 3. Totales
        JPanel panelTotales = new JPanel(new GridLayout(3, 2, 5, 5));
        panelTotales.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "3. Liquidación Total", TitledBorder.LEFT, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 12)));

        lblSubtotal = new JLabel("Q0.00", SwingConstants.RIGHT);
        lblImpuesto = new JLabel("Q0.00", SwingConstants.RIGHT);
        lblTotal = new JLabel("Q0.00", SwingConstants.RIGHT);
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTotal.setForeground(new Color(39, 174, 96));

        panelTotales.add(new JLabel("Subtotal Hospedaje:"));
        panelTotales.add(lblSubtotal);
        panelTotales.add(new JLabel("Impuestos (IVA 12%):"));
        panelTotales.add(lblImpuesto);
        panelTotales.add(new JLabel("TOTAL A PAGAR:"));
        panelTotales.add(lblTotal);

        panelCentral.add(panelTotales);

        add(panelCentral, BorderLayout.CENTER);

        // --- Panel de Botones ---
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        
        btnEmitirFactura = new JButton("Facturar e Imprimir");
        btnEmitirFactura.setUI(new javax.swing.plaf.basic.BasicButtonUI()); // <-- Forzar color en Windows
        btnEmitirFactura.setBackground(new Color(39, 174, 96));
        btnEmitirFactura.setForeground(Color.WHITE);
        btnEmitirFactura.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnEmitirFactura.setFocusPainted(false);

        btnCancelar = new JButton("Cancelar");

        panelBotones.add(btnCancelar);
        panelBotones.add(btnEmitirFactura);

        add(panelBotones, BorderLayout.SOUTH);

        // --- Eventos ---
        btnCancelar.addActionListener(e -> dispose());
        btnEmitirFactura.addActionListener(e -> procesarFactura());
    }

    private void calcularValores() {
        double subtotal = montoBase / 1.12;
        double impuesto = montoBase - subtotal;

        lblSubtotal.setText(df.format(subtotal));
        lblImpuesto.setText(df.format(impuesto));
        lblTotal.setText(df.format(montoBase));

        StringBuilder sb = new StringBuilder();
        sb.append("=============================================\n");
        sb.append("           HOTEL BAMBÚ - GUATEMALA          \n");
        sb.append("=============================================\n");
        sb.append("Reserva #: ").append(idReservacion).append("\n");
        sb.append("Concepto : Servicio de Hospedaje y Estancia\n");
        sb.append("Monto Neto: ").append(df.format(subtotal)).append("\n");
        sb.append("IVA (12%) : ").append(df.format(impuesto)).append("\n");
        sb.append("---------------------------------------------\n");
        sb.append("Monto Total: ").append(df.format(montoBase)).append("\n");

        txtDescripcion.setText(sb.toString());
    }

    private void procesarFactura() {
        String nit = txtNit.getText().trim();
        String nombre = txtNombreFactura.getText().trim();

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese el nombre para la factura.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idFormaPago = cbFormaPago.getSelectedIndex() + 1;
        double subtotal = montoBase / 1.12;
        double impuesto = montoBase - subtotal;

        Factura f = new Factura();
        f.setIdReservacion(idReservacion);
        f.setIdCliente(idCliente);
        f.setIdFormaPago(idFormaPago);
        f.setNitCliente(nit.isEmpty() ? "C/F" : nit);
        f.setNombreFactura(nombre);
        f.setSubtotal(subtotal);
        f.setImpuesto(impuesto);
        f.setMontoTotal(montoBase);
        f.setFechaEmision(LocalDateTime.now());

        FacturaDAO dao = new FacturaDAO();
        if (dao.generarFactura(f)) {
            JOptionPane.showMessageDialog(this, 
                "¡Factura generada exitosamente!\n\n" +
                "Cliente: " + nombre + "\n" +
                "NIT: " + (nit.isEmpty() ? "C/F" : nit) + "\n" +
                "Total Pagado: " + df.format(montoBase), 
                "Facturación Completada", 
                JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo guardar la factura en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}