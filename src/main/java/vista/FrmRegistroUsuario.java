package vista;

import dao.ClienteDAO;
import dao.TrabajadorDAO;
import modelo.Persona;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author acabi
 */
public class FrmRegistroUsuario extends JFrame {

    private static final Color COLOR_PRIMARIO = new Color(39, 64, 41);
    private static final Color COLOR_FONDO = new Color(247, 246, 242);
    private static final Color COLOR_TEXTO = new Color(30, 47, 32);

    private JTextField txtNombre, txtApellido, txtCorreo, txtDpi, txtNit;
    private JComboBox<String> cbxRol;
    private JPasswordField txtPassword, txtConfirmPassword;
    private JButton btnRegistrar, btnVolver;

    // Componentes para Múltiples Teléfonos (Examen UM
    private JTextField txtNumeroTel;
    private JComboBox<String> cmbTipoTel;
    private JButton btnAgregarTel;
    private DefaultTableModel modelTelefonos;
    private JTable tblTelefonos;

    private boolean esModoAdmin; // true: Administrador | false: Registro desde Login

    // Constructor por defecto
    public FrmRegistroUsuario() {
        this(true);
    }

    // Constructor parametrizado
    public FrmRegistroUsuario(boolean esModoAdmin) {
        super(esModoAdmin ? "BAMBÚ Hotel - Registro de Usuarios y Asignación de Roles" : "BAMBÚ Hotel - Registro de Cliente");
        this.esModoAdmin = esModoAdmin;
        configurarVentana();
        construirInterfaz();
    }

    private void configurarVentana() {
        setSize(560, 780);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(COLOR_FONDO);
        setLayout(new BorderLayout());
    }

    private void construirInterfaz() {
        // --- ENCABEZADO ---
        JPanel pnlHeader = new JPanel();
        pnlHeader.setBackground(COLOR_PRIMARIO);
        pnlHeader.setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel lblTitulo = new JLabel(esModoAdmin ? "Crear Cuenta de Usuario" : "Crear Cuenta de Cliente");
        lblTitulo.setFont(new Font("Georgia", Font.BOLD, 20));
        lblTitulo.setForeground(Color.WHITE);
        pnlHeader.add(lblTitulo);

        add(pnlHeader, BorderLayout.NORTH);

        // --- FORMULARIO CENTRAL ---
        JPanel pnlFormulario = new JPanel(new GridBagLayout());
        pnlFormulario.setBackground(COLOR_FONDO);
        pnlFormulario.setBorder(new EmptyBorder(15, 25, 15, 25));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.weightx = 1.0;

        txtNombre = new JTextField();
        txtApellido = new JTextField();
        txtCorreo = new JTextField();
        txtDpi = new JTextField();
        txtNit = new JTextField("C/F");

        cbxRol = new JComboBox<>(new String[]{"Cliente", "Trabajador"});
        cbxRol.setFont(new Font("SansSerif", Font.BOLD, 12));
        cbxRol.setBackground(Color.WHITE);

        if (!esModoAdmin) {
            cbxRol.setSelectedItem("Cliente");
            cbxRol.setEnabled(false);
        }

        txtPassword = new JPasswordField();
        txtConfirmPassword = new JPasswordField();

        // --- COMPONENTES DE TELÉFONO MULTIPLE ---
        txtNumeroTel = new JTextField(10);
        cmbTipoTel = new JComboBox<>(new String[]{"Personal", "Trabajo", "Casa", "Otro"});
        btnAgregarTel = new JButton("+ Agregar");
        btnAgregarTel.setBackground(new Color(41, 128, 185));
        btnAgregarTel.setForeground(Color.WHITE);

        modelTelefonos = new DefaultTableModel(new String[]{"Número", "Tipo"}, 0);
        tblTelefonos = new JTable(modelTelefonos);
        JScrollPane scrollTablaTel = new JScrollPane(tblTelefonos);
        scrollTablaTel.setPreferredSize(new Dimension(300, 90));

        // Evento para agregar teléfono a la tabla visual
        btnAgregarTel.addActionListener(e -> {
            String num = txtNumeroTel.getText().trim();
            String tipo = (String) cmbTipoTel.getSelectedItem();
            if (!num.isEmpty()) {
                modelTelefonos.addRow(new Object[]{num, tipo});
                txtNumeroTel.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Ingrese un número de teléfono válido.", "Atención", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Panel horizontal para ingresar teléfono + tipo + botón
        JPanel pnlInputTel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        pnlInputTel.setBackground(COLOR_FONDO);
        pnlInputTel.add(txtNumeroTel);
        pnlInputTel.add(cmbTipoTel);
        pnlInputTel.add(btnAgregarTel);

        int row = 0;
        agregarCampo(pnlFormulario, "Rol asignado *:", cbxRol, gbc, row++);
        agregarCampo(pnlFormulario, "Nombre *:", txtNombre, gbc, row++);
        agregarCampo(pnlFormulario, "Apellido *:", txtApellido, gbc, row++);
        agregarCampo(pnlFormulario, "Correo Electrónico *:", txtCorreo, gbc, row++);
        agregarCampo(pnlFormulario, "DPI / Identificación *:", txtDpi, gbc, row++);
        agregarCampo(pnlFormulario, "NIT:", txtNit, gbc, row++);
        
        // Sección Teléfonos
        agregarCampo(pnlFormulario, "Agregar Teléfono:", pnlInputTel, gbc, row++);
        agregarCampo(pnlFormulario, "Lista de Teléfonos:", scrollTablaTel, gbc, row++);

        agregarCampo(pnlFormulario, "Contraseña *:", txtPassword, gbc, row++);
        agregarCampo(pnlFormulario, "Confirmar Contraseña *:", txtConfirmPassword, gbc, row++);

        JScrollPane scrollPane = new JScrollPane(pnlFormulario);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);

        // --- BOTONES INFERIORES ---
        JPanel pnlBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        pnlBotones.setBackground(COLOR_FONDO);

        btnVolver = new JButton("Volver / Cancelar");
        btnVolver.setFont(new Font("SansSerif", Font.PLAIN, 12));
        btnVolver.addActionListener(e -> dispose());

        btnRegistrar = new JButton("Registrar Usuario");
        btnRegistrar.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnRegistrar.setBackground(COLOR_PRIMARIO);
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setFocusPainted(false);
        btnRegistrar.addActionListener(e -> registrarUsuario());

        pnlBotones.add(btnVolver);
        pnlBotones.add(btnRegistrar);

        add(pnlBotones, BorderLayout.SOUTH);
    }

    private void agregarCampo(JPanel panel, String etiqueta, JComponent componente, GridBagConstraints gbc, int fila) {
        gbc.gridy = fila;

        gbc.gridx = 0;
        gbc.weightx = 0.35;
        JLabel lbl = new JLabel(etiqueta);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 12));
        lbl.setForeground(COLOR_TEXTO);
        panel.add(lbl, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.65;
        componente.setFont(new Font("SansSerif", Font.PLAIN, 12));
        panel.add(componente, gbc);
    }

    private void registrarUsuario() {
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String correo = txtCorreo.getText().trim();
        String dpi = txtDpi.getText().trim();
        String pass = new String(txtPassword.getPassword());
        String confirmPass = new String(txtConfirmPassword.getPassword());
        String rolSeleccionado = (String) cbxRol.getSelectedItem();

        if (nombre.isEmpty() || apellido.isEmpty() || correo.isEmpty() || dpi.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor complete todos los campos obligatorios (*).", "Campos Requeridos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!pass.equals(confirmPass)) {
            JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 1. Extraer la lista de teléfonos agregados en la tabla
        List<Persona.Telefono> listaTelefonos = new ArrayList<>();
        for (int i = 0; i < modelTelefonos.getRowCount(); i++) {
            String num = (String) modelTelefonos.getValueAt(i, 0);
            String tipo = (String) modelTelefonos.getValueAt(i, 1);
            listaTelefonos.add(new Persona.Telefono(num, tipo));
        }

        boolean exito = false;

        try {
            if ("Cliente".equalsIgnoreCase(rolSeleccionado)) {
                ClienteDAO clienteDAO = new ClienteDAO();
                exito = clienteDAO.insertarClienteConTelefonos(nombre, apellido, correo, dpi, pass, listaTelefonos);
            } else if ("Trabajador".equalsIgnoreCase(rolSeleccionado)) {
                TrabajadorDAO trabajadorDAO = new TrabajadorDAO();
                String primerTel = listaTelefonos.isEmpty() ? "" : listaTelefonos.get(0).getNumero();
                exito = trabajadorDAO.insertarTrabajador(nombre, apellido, correo, primerTel, dpi, pass);
            }

            if (exito) {
                JOptionPane.showMessageDialog(this, "¡Usuario registrado exitosamente como " + rolSeleccionado + "!", "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo guardar en la base de datos. Verifique que el correo o DPI no estén duplicados.", "Error al Guardar", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al procesar la solicitud: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}