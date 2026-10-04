/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;
import dao.ClienteDAO;
import dao.TrabajadorDAO;
import dao.UsuarioDAO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
/**
 *
 * @author acabi
 */
public class FrmRegistroUsuario extends JFrame {

    private static final Color COLOR_PRIMARIO = new Color(39, 64, 41);
    private static final Color COLOR_FONDO = new Color(247, 246, 242);
    private static final Color COLOR_TEXTO = new Color(30, 47, 32);

    private JTextField txtNombre, txtApellido, txtCorreo, txtTelefono, txtDpi, txtNit;
    private JComboBox<String> cbxRol;
    private JPasswordField txtPassword, txtConfirmPassword;
    private JButton btnRegistrar, btnVolver;

    private boolean esModoAdmin; // true: viene de Registro y Roles | false: viene de Login (autoregistro)

    // Constructor por defecto (Habilita selección de roles)
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
        setSize(520, 680);
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
        txtTelefono = new JTextField();
        txtDpi = new JTextField();
        txtNit = new JTextField("C/F");

        cbxRol = new JComboBox<>(new String[]{"Cliente", "Trabajador"});
        cbxRol.setFont(new Font("SansSerif", Font.BOLD, 12));
        cbxRol.setBackground(Color.WHITE);

        // Si viene del Login, se deshabilita la selección y queda fijo como Cliente
        if (!esModoAdmin) {
            cbxRol.setSelectedItem("Cliente");
            cbxRol.setEnabled(false);
        }

        txtPassword = new JPasswordField();
        txtConfirmPassword = new JPasswordField();

        int row = 0;
        agregarCampo(pnlFormulario, "Rol asignado *:", cbxRol, gbc, row++);
        agregarCampo(pnlFormulario, "Nombre *:", txtNombre, gbc, row++);
        agregarCampo(pnlFormulario, "Apellido *:", txtApellido, gbc, row++);
        agregarCampo(pnlFormulario, "Correo Electrónico *:", txtCorreo, gbc, row++);
        agregarCampo(pnlFormulario, "Teléfono:", txtTelefono, gbc, row++);
        agregarCampo(pnlFormulario, "DPI / Identificación *:", txtDpi, gbc, row++);
        agregarCampo(pnlFormulario, "NIT:", txtNit, gbc, row++);
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
        btnRegistrar.setUI(new javax.swing.plaf.basic.BasicButtonUI());
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
        String telefono = txtTelefono.getText().trim();
        String dpi = txtDpi.getText().trim();
        String nit = txtNit.getText().trim();
        String rolSeleccionado = (String) cbxRol.getSelectedItem();
        String pass = new String(txtPassword.getPassword());
        String confirmPass = new String(txtConfirmPassword.getPassword());

        if (nombre.isEmpty() || apellido.isEmpty() || correo.isEmpty() || dpi.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor complete todos los campos obligatorios (*).", "Campos Requeridos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!pass.equals(confirmPass)) {
            JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean exito = false;

        try {
            if ("Cliente".equalsIgnoreCase(rolSeleccionado)) {
                ClienteDAO clienteDAO = new ClienteDAO();
                // Llama al método de inserción de cliente en tu BD
                exito = clienteDAO.insertarCliente(nombre, apellido, correo, telefono, dpi, nit, pass);
            } else if ("Trabajador".equalsIgnoreCase(rolSeleccionado)) {
                TrabajadorDAO trabajadorDAO = new TrabajadorDAO();
                // Llama al método de inserción de trabajador en tu BD
                exito = trabajadorDAO.insertarTrabajador(nombre, apellido, correo, telefono, dpi, pass);
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