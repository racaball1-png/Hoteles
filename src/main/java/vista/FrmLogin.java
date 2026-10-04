/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import controladores.LoginController;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.regex.Pattern;

/**
 * @author acabi
 */
public class FrmLogin extends JFrame {

    private static final Color COLOR_BG_CANVAS = new Color(0xF6, 0xF4, 0xEE);
    private static final Color COLOR_CARD_BG = new Color(0xFA, 0xF9, 0xF5);
    private static final Color COLOR_PRIMARY = new Color(0x1B, 0x35, 0x24);
    private static final Color COLOR_PRIMARY_HOVER = new Color(0x12, 0x24, 0x18);
    private static final Color COLOR_AMBER = new Color(0xB4, 0x53, 0x09);
    private static final Color COLOR_TEXT_DARK = new Color(0x29, 0x25, 0x24);
    private static final Color COLOR_TEXT_MUTED = new Color(0x78, 0x71, 0x6C);
    private static final Color COLOR_BORDER = new Color(0xE7, 0xE5, 0xE4);
    private static final Color COLOR_SUCCESS = new Color(0x15, 0x80, 0x3D);
    private static final Color COLOR_ERROR = new Color(0xDC, 0x26, 0x26);

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

    private JTextField txtEmail;
    private JLabel lblEmailError;
    private JPasswordField txtPassword;
    private JButton btnTogglePassword;
    private boolean isPasswordVisible = false;

    private JCheckBox chkRemember;
    private JButton btnLogin;
    private JButton btnCrearCuenta;

    private LoginController controller;

    public FrmLogin() {
        controller = new LoginController(this);
        initComponents();
        setupRealTimeValidation();
    }

    private void initComponents() {
        setTitle("BAMBÚ Hotel & Nature Suites - Sistema Hotelero");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(480, 560); // Tamaño ajustado sin el bloque sobrante
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(COLOR_BG_CANVAS);
        setLayout(new GridBagLayout());

        JPanel cardPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(COLOR_CARD_BG);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 28, 28);
                g2.setColor(COLOR_BORDER);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 28, 28);
                g2.setColor(COLOR_PRIMARY);
                g2.fillRoundRect(0, 0, getWidth(), 6, 6, 6);
                g2.dispose();
            }
        };
        cardPanel.setOpaque(false);
        cardPanel.setPreferredSize(new Dimension(410, 480));
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBorder(new EmptyBorder(25, 30, 25, 30));

        // LOGO Y TÍTULOS
        JLabel lblLogo = new JLabel("B");
        lblLogo.setFont(new Font("Serif", Font.BOLD, 22));
        lblLogo.setForeground(new Color(0xFE, 0xF3, 0xC7));
        lblLogo.setOpaque(true);
        lblLogo.setBackground(COLOR_PRIMARY);
        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
        lblLogo.setPreferredSize(new Dimension(46, 46));
        lblLogo.setMaximumSize(new Dimension(46, 46));
        lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblBrandSub = new JLabel("HOTEL & NATURE SUITES");
        lblBrandSub.setFont(new Font("SansSerif", Font.BOLD, 10));
        lblBrandSub.setForeground(COLOR_AMBER);
        lblBrandSub.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblBrandTitle = new JLabel("BAMBÚ");
        lblBrandTitle.setFont(new Font("Serif", Font.BOLD, 30));
        lblBrandTitle.setForeground(COLOR_PRIMARY);
        lblBrandTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblPortal = new JLabel("Portal de Acceso");
        lblPortal.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblPortal.setForeground(COLOR_TEXT_MUTED);
        lblPortal.setAlignmentX(Component.CENTER_ALIGNMENT);

        cardPanel.add(lblLogo);
        cardPanel.add(Box.createVerticalStrut(8));
        cardPanel.add(lblBrandSub);
        cardPanel.add(lblBrandTitle);
        cardPanel.add(lblPortal);
        cardPanel.add(Box.createVerticalStrut(20));

        // CORREO
        JLabel lblEmail = new JLabel("Correo electrónico");
        lblEmail.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblEmail.setForeground(COLOR_TEXT_DARK);
        lblEmail.setAlignmentX(Component.LEFT_ALIGNMENT);
        cardPanel.add(lblEmail);
        cardPanel.add(Box.createVerticalStrut(4));

        txtEmail = new JTextField();
        estilitarCampo(txtEmail);
        cardPanel.add(txtEmail);

        lblEmailError = new JLabel(" ");
        lblEmailError.setFont(new Font("SansSerif", Font.PLAIN, 11));
        lblEmailError.setForeground(COLOR_ERROR);
        lblEmailError.setAlignmentX(Component.LEFT_ALIGNMENT);
        cardPanel.add(lblEmailError);
        cardPanel.add(Box.createVerticalStrut(10));

        // CONTRASEÑA
        JLabel lblPassword = new JLabel("Contraseña");
        lblPassword.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblPassword.setForeground(COLOR_TEXT_DARK);
        lblPassword.setAlignmentX(Component.LEFT_ALIGNMENT);
        cardPanel.add(lblPassword);
        cardPanel.add(Box.createVerticalStrut(4));

        JPanel pnlPasswordInput = new JPanel(new BorderLayout(5, 0));
        pnlPasswordInput.setOpaque(false);
        pnlPasswordInput.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        txtPassword = new JPasswordField();
        estilitarCampo(txtPassword);
        pnlPasswordInput.add(txtPassword, BorderLayout.CENTER);

        btnTogglePassword = new JButton("👁");
        btnTogglePassword.setPreferredSize(new Dimension(42, 38));
        btnTogglePassword.setFocusPainted(false);
        btnTogglePassword.setBackground(Color.WHITE);
        btnTogglePassword.setBorder(BorderFactory.createLineBorder(COLOR_BORDER));
        btnTogglePassword.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnTogglePassword.addActionListener(e -> togglePasswordVisibility());
        pnlPasswordInput.add(btnTogglePassword, BorderLayout.EAST);
        pnlPasswordInput.setAlignmentX(Component.LEFT_ALIGNMENT);
        cardPanel.add(pnlPasswordInput);

        cardPanel.add(Box.createVerticalStrut(10));

        // RECORDAR MI ACCESO
        chkRemember = new JCheckBox("Recordar mi acceso en este equipo");
        chkRemember.setFont(new Font("SansSerif", Font.PLAIN, 12));
        chkRemember.setForeground(COLOR_TEXT_DARK);
        chkRemember.setOpaque(false);
        chkRemember.setFocusPainted(false);
        chkRemember.setAlignmentX(Component.LEFT_ALIGNMENT);
        cardPanel.add(chkRemember);

        cardPanel.add(Box.createVerticalStrut(20));

        // BOTÓN LOGIN
        btnLogin = new JButton("Ingresar a BAMBÚ") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (isEnabled()) {
                    g2.setColor(getModel().isRollover() ? COLOR_PRIMARY_HOVER : COLOR_PRIMARY);
                } else {
                    g2.setColor(new Color(0xD6, 0xD3, 0xCB));
                }
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btnLogin.setFont(new Font("SansSerif", Font.BOLD, 13));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setContentAreaFilled(false);
        btnLogin.setBorderPainted(false);
        btnLogin.setFocusPainted(false);
        btnLogin.setPreferredSize(new Dimension(350, 44));
        btnLogin.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogin.setEnabled(false);
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogin.addActionListener(this::ejecutarLogin);
        cardPanel.add(btnLogin);

        cardPanel.add(Box.createVerticalStrut(12));

        // BOTÓN REGISTRO (Redirecciona a FrmRegistroUsuario)
        btnCrearCuenta = new JButton("¿No tienes cuenta? Regístrate aquí");
        btnCrearCuenta.setFont(new Font("SansSerif", Font.PLAIN, 12));
        btnCrearCuenta.setForeground(COLOR_PRIMARY);
        btnCrearCuenta.setBorderPainted(false);
        btnCrearCuenta.setContentAreaFilled(false);
        btnCrearCuenta.setFocusPainted(false);
        btnCrearCuenta.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCrearCuenta.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCrearCuenta.addActionListener(e -> {
            this.dispose();
            FrmRegistroUsuario frm = new FrmRegistroUsuario(false); // Rol bloqueado en "Cliente"
            frm.setVisible(true);
        });
        cardPanel.add(btnCrearCuenta);

        add(cardPanel);
    }

    private void estilitarCampo(JTextField campo) {
        campo.setFont(new Font("SansSerif", Font.PLAIN, 13));
        campo.setPreferredSize(new Dimension(350, 38));
        campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        campo.setBackground(Color.WHITE);
        campo.setForeground(COLOR_TEXT_DARK);
        campo.setCaretColor(COLOR_PRIMARY);
        campo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_BORDER, 1),
            new EmptyBorder(5, 10, 5, 10)
        ));
        campo.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            txtPassword.setEchoChar('•');
            btnTogglePassword.setText("👁");
            isPasswordVisible = false;
        } else {
            txtPassword.setEchoChar((char) 0);
            btnTogglePassword.setText("🙈");
            isPasswordVisible = true;
        }
    }

    private void setupRealTimeValidation() {
        DocumentListener listener = new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { validarFormularioEnTiempoReal(); }
            @Override public void removeUpdate(DocumentEvent e) { validarFormularioEnTiempoReal(); }
            @Override public void changedUpdate(DocumentEvent e) { validarFormularioEnTiempoReal(); }
        };
        txtEmail.getDocument().addDocumentListener(listener);
        txtPassword.getDocument().addDocumentListener(listener);
    }

    private void validarFormularioEnTiempoReal() {
        String email = txtEmail.getText().trim();
        String pass = new String(txtPassword.getPassword());

        boolean isEmailValid = false;
        if (email.isEmpty()) {
            lblEmailError.setText(" ");
        } else if (!EMAIL_PATTERN.matcher(email).matches()) {
            lblEmailError.setForeground(COLOR_ERROR);
            lblEmailError.setText("Formato inválido (ej. usuario@hotel.com)");
        } else {
            lblEmailError.setForeground(COLOR_SUCCESS);
            lblEmailError.setText("✓ Correo válido");
            isEmailValid = true;
        }

        // Habilita el botón si el correo es válido y la contraseña no está vacía
        boolean isPassNotEmpty = !pass.isEmpty();
        btnLogin.setEnabled(isEmailValid && isPassNotEmpty);
    }

    private void ejecutarLogin(ActionEvent e) {
        String email = txtEmail.getText().trim();
        String password = new String(txtPassword.getPassword());
        controller.autenticar(email, password);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> new FrmLogin().setVisible(true));
    }
}