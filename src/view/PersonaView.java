package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import model.Persona;

public class PersonaView extends JFrame {
    // --- Paleta de Colores Profesional ---
    private static final Color PRIMARY_COLOR = new Color(44, 62, 80);    // Azul Oscuro (Midnight Blue)
    private static final Color SECONDARY_COLOR = new Color(52, 152, 219); // Azul Claro (Peter River)
    private static final Color ACCENT_COLOR = new Color(22, 160, 133);    // Verde Azulado (Green Sea)
    private static final Color BACKGROUND_COLOR = new Color(236, 240, 241); // Gris Claro (Clouds)
    private static final Color SURFACE_COLOR = Color.WHITE;
    private static final Color TEXT_COLOR = new Color(44, 62, 80);
    
    // Colores de estado
    private static final Color SUCCESS_COLOR = new Color(39, 174, 96);
    private static final Color WARNING_COLOR = new Color(18, 67, 243);
    private static final Color DANGER_COLOR = new Color(192, 57, 43);

    private JTextField txtNombre = crearTextField();
    private JTextField txtEdad = crearTextField();
    private JTextField txtPeso = crearTextField();
    private JTextField txtEstatura = crearTextField();
    
    private JButton btnRegistrar = new StyledButton("REGISTRAR", ACCENT_COLOR);
    private JButton btnActualizar = new StyledButton("ACTUALIZAR", SECONDARY_COLOR);
    private JButton btnEliminar = new StyledButton("ELIMINAR", DANGER_COLOR);
    private JButton btnLimpiar = new StyledButton("LIMPIAR", new Color(127, 140, 141));
    
    private DefaultTableModel modeloTabla;
    private JTable tablaPersonas;

    public PersonaView() {
        setTitle("Sistema de Gestión IMC - Centro Médico VidaPlena");
        setSize(1000, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(BACKGROUND_COLOR);

        // --- Configuración Global de UI ---
        configurarUI();

        // --- Panel Principal ---
        JPanel panelPrincipal = new JPanel(new BorderLayout(20, 20));
        panelPrincipal.setBackground(BACKGROUND_COLOR);
        panelPrincipal.setBorder(new EmptyBorder(20, 20, 20, 20));
        add(panelPrincipal, BorderLayout.CENTER);

        // --- Panel Superior (Formulario y Botones) ---
        JPanel panelSuperior = new JPanel(new BorderLayout(0, 15));
        panelSuperior.setBackground(BACKGROUND_COLOR);
        panelSuperior.setOpaque(false);
        
        // Título
        JLabel lblTitulo = new JLabel("Gestión de Pacientes");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(PRIMARY_COLOR);
        lblTitulo.setBorder(new EmptyBorder(0, 0, 10, 0));
        panelSuperior.add(lblTitulo, BorderLayout.NORTH);

        // Formulario
        JPanel panelFormulario = new JPanel(new GridLayout(1, 4, 15, 0));
        panelFormulario.setBackground(SURFACE_COLOR);
        panelFormulario.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(189, 195, 199), 1, true),
            new EmptyBorder(15, 15, 15, 15)
        ));
        
        agregarCampo(panelFormulario, "Nombre Completo", txtNombre);
        agregarCampo(panelFormulario, "Edad", txtEdad);
        agregarCampo(panelFormulario, "Peso (kg)", txtPeso);
        agregarCampo(panelFormulario, "Estatura (m)", txtEstatura);
        
        panelSuperior.add(panelFormulario, BorderLayout.CENTER);

        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panelBotones.setBackground(BACKGROUND_COLOR);
        
        panelBotones.add(btnRegistrar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);
        
        panelSuperior.add(panelBotones, BorderLayout.SOUTH);
        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);

        // --- Tabla ---
        String[] columnas = {"Nombre", "Edad", "Peso", "Estatura", "IMC", "Interpretación"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        
        tablaPersonas = new JTable(modeloTabla);
        configurarTabla();

        JScrollPane scrollPane = new JScrollPane(tablaPersonas);
        scrollPane.setBorder(new LineBorder(new Color(189, 195, 199), 1));
        scrollPane.getViewport().setBackground(SURFACE_COLOR);
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);
    }

    private void configurarUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.put("Label.font", new Font("Segoe UI", Font.PLAIN, 12));
            UIManager.put("Label.foreground", new Color(127, 140, 141));
        } catch (Exception ignored) {}
    }

    private void configurarTabla() {
        tablaPersonas.setRowHeight(40);
        tablaPersonas.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tablaPersonas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaPersonas.setGridColor(new Color(236, 240, 241));
        tablaPersonas.setShowVerticalLines(false);
        tablaPersonas.setSelectionBackground(new Color(214, 234, 248));
        tablaPersonas.setSelectionForeground(TEXT_COLOR);

        // --- Encabezado Personalizado (Solución para visibilidad) ---
        JTableHeader header = tablaPersonas.getTableHeader();
        header.setPreferredSize(new Dimension(0, 45));
        
        // Creamos un renderer explícito para el header que fuerce el color de fondo
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel l = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                l.setBackground(PRIMARY_COLOR);
                l.setForeground(Color.WHITE);
                l.setFont(new Font("Segoe UI", Font.BOLD, 13));
                l.setHorizontalAlignment(JLabel.CENTER);
                l.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(60, 80, 100)));
                return l;
            }
        });

        // --- Renderer de Celdas ---
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, 
                    boolean isSelected, boolean hasFocus, int row, int column) {
                
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setHorizontalAlignment(JLabel.CENTER);
                
                if (!isSelected) {
                    // Colorear Interpretación
                    if (column == 5) {
                        String interpretacion = (String) value;
                        // Estilo tipo "Badge"
                        JLabel label = (JLabel) c;
                        label.setFont(label.getFont().deriveFont(Font.BOLD));
                        
                        switch (interpretacion) {
                            case "Bajo peso": setForeground(WARNING_COLOR); break;
                            case "Normal": setForeground(SUCCESS_COLOR); break;
                            case "Sobrepeso": setForeground(new Color(230, 126, 34)); break;
                            case "Obesidad": setForeground(DANGER_COLOR); break;
                            default: setForeground(TEXT_COLOR);
                        }
                    } else {
                        setForeground(TEXT_COLOR);
                    }
                    
                    // Alternar fondo
                    setBackground(row % 2 == 0 ? SURFACE_COLOR : new Color(248, 249, 249));
                }
                return c;
            }
        };

        for (int i = 0; i < tablaPersonas.getColumnCount(); i++) {
            tablaPersonas.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    // --- Clase para Botones Estilizados ---
    private static class StyledButton extends JButton {
        private Color normalColor;
        private Color hoverColor;
        private Color pressedColor;

        public StyledButton(String text, Color color) {
            super(text);
            this.normalColor = color;
            this.hoverColor = color.brighter();
            this.pressedColor = color.darker();
            
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setForeground(Color.WHITE);
            setFont(new Font("Segoe UI", Font.BOLD, 12));
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setBorder(new EmptyBorder(10, 25, 10, 25));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            if (getModel().isPressed()) {
                g2.setColor(pressedColor);
            } else if (getModel().isRollover()) {
                g2.setColor(hoverColor);
                // Sombra suave al hacer hover
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
            } else {
                g2.setColor(normalColor);
            }
            
            // Borde redondeado
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
            
            g2.dispose();
            super.paintComponent(g);
        }
    }

    private JTextField crearTextField() {
        JTextField tf = new JTextField();
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tf.setForeground(TEXT_COLOR);
        tf.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(189, 195, 199)),
            new EmptyBorder(5, 8, 5, 8)
        ));
        return tf;
    }

    private void agregarCampo(JPanel panel, String titulo, JTextField campo) {
        JPanel p = new JPanel(new BorderLayout(0, 5));
        p.setBackground(SURFACE_COLOR);
        
        JLabel lbl = new JLabel(titulo.toUpperCase());
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 10));
        lbl.setForeground(new Color(149, 165, 166));
        
        p.add(lbl, BorderLayout.NORTH);
        p.add(campo, BorderLayout.CENTER);
        panel.add(p);
    }

    // Getters y Setters
    public String getNombre() { return txtNombre.getText(); }
    public String getEdad() { return txtEdad.getText(); }
    public String getPeso() { return txtPeso.getText(); }
    public String getEstatura() { return txtEstatura.getText(); }

    public void setNombre(String nombre) { txtNombre.setText(nombre); }
    public void setEdad(String edad) { txtEdad.setText(edad); }
    public void setPeso(String peso) { txtPeso.setText(peso); }
    public void setEstatura(String estatura) { txtEstatura.setText(estatura); }

    public void limpiarCampos() {
        txtNombre.setText("");
        txtEdad.setText("");
        txtPeso.setText("");
        txtEstatura.setText("");
        tablaPersonas.clearSelection();
    }

    public int getFilaSeleccionada() { return tablaPersonas.getSelectedRow(); }

    public void mostrarPersonas(List<Persona> personas) {
        modeloTabla.setRowCount(0);
        for (Persona p : personas) {
            modeloTabla.addRow(new Object[]{
                p.getNombre(), p.getEdad(), p.getPeso(), p.getEstatura(),
                String.format("%.2f", p.calcularIMC()), p.interpretarIMC()
            });
        }
    }

    public void mostrarMensaje(String mensaje) { 
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE); 
    }
    
    public void mostrarError(String mensaje) { 
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE); 
    }

    public void addRegistrarListener(ActionListener l) { btnRegistrar.addActionListener(l); }
    public void addActualizarListener(ActionListener l) { btnActualizar.addActionListener(l); }
    public void addEliminarListener(ActionListener l) { btnEliminar.addActionListener(l); }
    public void addLimpiarListener(ActionListener l) { btnLimpiar.addActionListener(l); }
    public void addTablaSelectionListener(javax.swing.event.ListSelectionListener l) {
        tablaPersonas.getSelectionModel().addListSelectionListener(l);
    }
}
