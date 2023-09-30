import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.sql.Statement;
import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class VentanaPrincipal {
    private static final Dimension BUTTON_SIZE = new Dimension(250, 30);
    private static final Font LABEL_FONT = new Font("Arial", Font.PLAIN, 16);
    private static final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 16);
    private Sistema sistema = new Sistema();

    public VentanaPrincipal() {
        DB db = new DB();

        Connection connection = db.getConnection();
        if(connection != null) {
            System.out.println("Conexión exitosa");
        } else {
            System.out.println("Error al conectar");
        }

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM pacientes");
            while (resultSet.next()) {
                String rut = resultSet.getString("rut");
                String nombre = resultSet.getString("nombre");
                int edad = resultSet.getInt("edad");
                Paciente paciente = new Paciente(nombre, edad, rut);
        
                // Usar un nuevo Statement para la consulta interna
                try (Statement statementConsultas = connection.createStatement()) {
                    ResultSet resultSetConsultas = statementConsultas.executeQuery("SELECT * FROM consultas WHERE rut = '" + rut + "'");
                    while (resultSetConsultas.next()) {
                        String medico = resultSetConsultas.getString("medico");
                        String fecha = resultSetConsultas.getString("fecha");
                        String hora = resultSetConsultas.getString("hora");
                        String motivo = resultSetConsultas.getString("motivoVisita");
                        String descripcion = resultSetConsultas.getString("descripcion");
                        ConsultaMedica consulta = new ConsultaMedica(medico, fecha, hora, motivo, descripcion);
                        paciente.agregarConsulta(consulta);
                    }
                }
        
                sistema.agregarPaciente(paciente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        sistema.llenarListaConsultas();
        
    }

    public void crearVentana() {
        JFrame frame = createMainFrame();

        frame.add(createLabel("Sistema de salud", new Font("Arial", Font.BOLD, 24)));
        frame.add(createButtonPanel("Agregar paciente", e -> abrirDialogoAgregarPaciente()));
        frame.add(createButtonPanel("Mostrar pacientes", e -> abrirDialogoMostrarPacientes()));
        frame.add(createButtonPanel("Eliminar paciente", e -> abrirDialogoEliminarPaciente()));
        frame.add(createButtonPanel("Crear consulta a paciente", e -> abrirDialogoAgregaConsulta()));
        frame.add(createButtonPanel("Mostrar consulta médica", e -> abrirDialogoMostrarConsultas()));
        frame.add(createButtonPanel("Editar consulta", e -> abrirDialogoBuscarPacienteParaEditarConsulta()));
        frame.add(createButtonPanel("Búsqueda avanzada", e -> abrirDialogoBusquedaAvanzadaConTabla()));
        frame.add(createButtonPanel("Salir del sistema", e -> System.exit(0)));

        frame.setVisible(true);
    }

    private JFrame createMainFrame() {
        JFrame frame = new JFrame("Sistema de Manejo de Salud");
        frame.setSize(700, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(14, 1));
        return frame;
    }

    private JLabel createLabel(String text, Font font) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(font);
        return label;
    }

    private JPanel createButtonPanel(String buttonText, ActionListener listener) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton button = new JButton(buttonText);
        button.setPreferredSize(BUTTON_SIZE);
        button.addActionListener(listener);
        panel.add(button);
        return panel;
    }

    private JDialog createDialog(String title, int width, int height) {
        JDialog dialog = new JDialog();
        dialog.setTitle(title);
        dialog.setSize(width, height);
        dialog.setLayout(new GridBagLayout());
        return dialog;
    }

    private JTextField createTextField(int columns) {
        JTextField textField = new JTextField(columns);
        textField.setFont(new Font("Arial", Font.PLAIN, 16));
        return textField;
    }

    private void addToPanel(JPanel panel, String labelText, JComponent component, GridBagConstraints gbc, int row) {
        JLabel label = new JLabel(labelText);
        label.setFont(LABEL_FONT);
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(component, gbc);
    }

    private JButton createButton(String text, Font font) {
        JButton button = new JButton(text);
        button.setFont(font);
        return button;
    }


    public void abrirDialogoAgregarPaciente() {
        JDialog dialog = createDialog("Agregar Paciente", 400, 250);
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Crear componentes
        JTextField txtNombre = createTextField(15);
        JTextField txtEdad = createTextField(15);
        JTextField txtRut = createTextField(15);

        // Agregar componentes al panel
        addToPanel(panel, "Nombre:", txtNombre, gbc, 0);
        addToPanel(panel, "Edad:", txtEdad, gbc, 1);
        addToPanel(panel, "RUT:", txtRut, gbc, 2);

        JButton btnGuardar = createButton("Guardar", BUTTON_FONT);
        btnGuardar.addActionListener(e -> {
            String rut = txtRut.getText();

            if (sistema.existePaciente(rut)) {
                JOptionPane.showMessageDialog(null, "Ya existe un paciente con ese RUT");
                return;
            }

            String nombre = txtNombre.getText();
            int edad = Integer.parseInt(txtEdad.getText());

            Paciente nuevoPaciente = new Paciente(nombre, edad, rut);
            sistema.agregarPaciente(nuevoPaciente);

            // Escribir el nuevo paciente en el archivo CSV
            //CSV.escribirPaciente(nuevoPaciente);

            dialog.dispose();
        });

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(btnGuardar, gbc);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    public void abrirDialogoMostrarPacientes() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Mostrar Pacientes");
        dialog.setSize(500, 400);
        dialog.setLayout(new BorderLayout());

        String[] columnNames = {"Nombre", "Edad", "RUT"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        sistema.agregarPacientesTabla(model);

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dialog.dispose());

        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBoton.add(btnCerrar);

        dialog.add(scrollPane, BorderLayout.CENTER);
        dialog.add(panelBoton, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }


    public void abrirDialogoEliminarPaciente() {
        JDialog dialog = createDialog("Eliminar Paciente", 400, 150);
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JTextField txtRut = createTextField(15);
        addToPanel(panel, "RUT:", txtRut, gbc, 0);

        JButton btnEliminar = createButton("Eliminar", BUTTON_FONT);
        btnEliminar.addActionListener(e -> {
            String rut = txtRut.getText();
            if (!sistema.existePaciente(rut)) {
                JOptionPane.showMessageDialog(null, "No existe un paciente con ese RUT");
            } else {
                sistema.eliminarPaciente(rut);
                dialog.dispose();
            }
        });
        addToPanel(panel, "", btnEliminar, gbc, 1);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    public void abrirDialogoAgregaConsulta() {
        JDialog dialog = createDialog("Agregar Consulta Médica", 400, 400);
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Crear componentes
        JTextField txtRut = createTextField(15);
        JTextField txtMedico = createTextField(15);
        JTextField txtFecha = createTextField(15);
        JTextField txtHora = createTextField(15);
        JTextField txtMotivo = createTextField(15);
        JTextField txtDescripcion = createTextField(15);

        // Agregar componentes al panel
        addToPanel(panel, "RUT del paciente:", txtRut, gbc, 0);
        addToPanel(panel, "Médico:", txtMedico, gbc, 1);
        addToPanel(panel, "Fecha:", txtFecha, gbc, 2);
        addToPanel(panel, "Hora:", txtHora, gbc, 3);
        addToPanel(panel, "Motivo de visita:", txtMotivo, gbc, 4);
        addToPanel(panel, "Descripción:", txtDescripcion, gbc, 5);

        JButton btnGuardar = createButton("Guardar", BUTTON_FONT);
        btnGuardar.addActionListener(e -> {
            String rut = txtRut.getText();
            if (!sistema.existePaciente(rut)) {
                JOptionPane.showMessageDialog(null, "No existe un paciente con ese RUT");
                return;
            }
            ConsultaMedica nuevaConsulta = new ConsultaMedica(txtMedico.getText(), txtFecha.getText(), txtHora.getText(), txtMotivo.getText(), txtDescripcion.getText());
            sistema.buscarPaciente(rut).agregarConsulta(nuevaConsulta);
            sistema.agregarConsulta(nuevaConsulta);
            //CSV.escribirConsulta(rut, nuevaConsulta);  // Asegúrate de tener la clase CSVHelper con el método correspondiente
            dialog.dispose();
        });

        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(btnGuardar, gbc);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    public void abrirDialogoMostrarConsultas() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Mostrar Consultas Médicas");
        dialog.setSize(700, 400);
        dialog.setLayout(new BorderLayout());

        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblRut = new JLabel("RUT del paciente:");
        JLabel lblRutValor = new JLabel("");
        JTextField txtRut = new JTextField(15);
        JButton btnBuscar = new JButton("Buscar");
        panelSuperior.add(lblRut);
        panelSuperior.add(txtRut);
        panelSuperior.add(btnBuscar);
        panelSuperior.add(lblRutValor);
        dialog.add(panelSuperior, BorderLayout.NORTH);

        String[] columnNames = {"Médico", "Fecha", "Hora", "Motivo", "Descripción"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        dialog.add(scrollPane, BorderLayout.CENTER);

        JButton btnMostrarTodas = new JButton("Mostrar todas las consultas");
        btnMostrarTodas.addActionListener(e -> {
            model.setRowCount(0);
            sistema.agregarConsultaTabla(model);
            lblRutValor.setText("");
        });

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dialog.dispose());
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBoton.add(btnCerrar);
        panelBoton.add(btnMostrarTodas);
        dialog.add(panelBoton, BorderLayout.SOUTH);

        sistema.agregarConsultaTabla(model);

        btnBuscar.addActionListener(e -> {
            String rut = txtRut.getText();
            Paciente paciente = sistema.buscarPaciente(rut);
            if (paciente == null) {
                JOptionPane.showMessageDialog(null, "No existe un paciente con ese RUT");
                return;
            }

            lblRutValor.setText("RUT ingresado: " + rut);

            model.setRowCount(0);  // Limpiar la tabla

            ArrayList<ConsultaMedica> consultasPaciente = new ArrayList<>();
            paciente.inicializarConsultas(consultasPaciente);

            for (ConsultaMedica consulta : consultasPaciente) {
                Object[] rowData = {
                        consulta.getMedico(),
                        consulta.getHora(),
                        consulta.getFecha(),
                        consulta.getMotivoVisita(),
                        consulta.getDescripcion()
                };
                model.addRow(rowData);
            }
        });

        dialog.setVisible(true);
    }


    public void abrirDialogoBuscarPacienteParaEditarConsulta() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Buscar Paciente por RUT");
        dialog.setSize(300, 150);
        dialog.setLayout(new GridLayout(3, 2));

        JLabel lblRut = new JLabel("RUT del paciente:");
        JTextField txtRut = new JTextField();

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(e -> {
            String rut = txtRut.getText();
            Paciente paciente = sistema.buscarPaciente(rut);
            if (paciente == null) {
                JOptionPane.showMessageDialog(null, "No existe un paciente con ese RUT");
            } else {
                abrirDialogoMostrarConsultasParaEditar(paciente);
                dialog.dispose();
            }
        });

        dialog.add(lblRut);
        dialog.add(txtRut);
        dialog.add(new JLabel());  // Espacio vacío
        dialog.add(btnBuscar);
        dialog.setVisible(true);
    }


    public void abrirDialogoMostrarConsultasParaEditar(Paciente paciente) {
        JDialog dialog = new JDialog();
        dialog.setTitle("Consultas Médicas de " + paciente.getNombre());
        dialog.setSize(600, 400);
        dialog.setLayout(new BorderLayout());

        String[] columnNames = {"Médico", "Fecha", "Hora", "Motivo"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;  // Esto hace que ninguna celda sea editable
            }
        };

        ArrayList<ConsultaMedica> consultas = new ArrayList<>();
        paciente.inicializarConsultas(consultas);
        for (ConsultaMedica consulta : consultas) {
            Object[] rowData = {consulta.getMedico(), consulta.getFecha(), consulta.getHora(), consulta.getMotivoVisita()};
            model.addRow(rowData);
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        JButton btnEditar = new JButton("Editar Consulta Seleccionada");
        btnEditar.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                ConsultaMedica consultaSeleccionada = consultas.get(selectedRow);
                abrirDialogoEditarConsultaMedica(paciente, consultaSeleccionada, model);
            } else {
                JOptionPane.showMessageDialog(null, "Por favor, seleccione una consulta para editar.");
            }
        });

        JButton btnEliminar = new JButton("Eliminar Consulta Seleccionada");
        btnEliminar.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int confirm = JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea eliminar esta consulta?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    paciente.eliminarConsulta(selectedRow);
                    model.removeRow(selectedRow);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Por favor, seleccione una consulta para eliminar.");
            }
        });


        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout());
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        dialog.add(panelBotones, BorderLayout.SOUTH);

        dialog.add(scrollPane, BorderLayout.CENTER);
        dialog.setVisible(true);
    }


    public void abrirDialogoEditarConsultaMedica(Paciente paciente, ConsultaMedica consulta, DefaultTableModel model) {
        JDialog dialog = new JDialog();
        dialog.setTitle("Editar Consulta Médica");
        dialog.setSize(400, 400);
        dialog.setLayout(new GridLayout(7, 2));

        JTextField txtMedico = new JTextField(consulta.getMedico());
        JTextField txtHora = new JTextField(consulta.getHora());
        JTextField txtFecha = new JTextField(consulta.getFecha());
        JTextField txtMotivo = new JTextField(consulta.getMotivoVisita());
        JTextField txtDescripcion = new JTextField(consulta.getDescripcion());

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> {
            consulta.setMedico(txtMedico.getText());
            consulta.setHora(txtHora.getText());
            consulta.setFecha(txtFecha.getText());
            consulta.setMotivoVisita(txtMotivo.getText());
            consulta.setDescripcion(txtDescripcion.getText());
            actualizarTablaEditarConsultas(model, paciente);
            dialog.dispose();
        });

        dialog.add(new JLabel("Médico:"));
        dialog.add(txtMedico);
        dialog.add(new JLabel("Hora:"));
        dialog.add(txtHora);
        dialog.add(new JLabel("Fecha:"));
        dialog.add(txtFecha);
        dialog.add(new JLabel("Motivo:"));
        dialog.add(txtMotivo);
        dialog.add(new JLabel("Descripción:"));
        dialog.add(txtDescripcion);
        dialog.add(new JLabel());  // Espacio vacío
        dialog.add(btnGuardar);

        dialog.setVisible(true);
    }

    public void actualizarTablaEditarConsultas(DefaultTableModel model, Paciente paciente) {
        // Limpiar el modelo
        model.setRowCount(0);
        ArrayList<ConsultaMedica> consultas = new ArrayList<>();
        paciente.inicializarConsultas(consultas);

        // Agregar las filas actualizadas
        for (ConsultaMedica consulta : consultas) {
            Object[] rowData = {consulta.getMedico(), consulta.getFecha(), consulta.getHora(), consulta.getMotivoVisita()};
            model.addRow(rowData);
        }
    }

    public void abrirDialogoBusquedaAvanzadaConTabla() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Búsqueda Avanzada de Pacientes");
        dialog.setSize(600, 500);
        dialog.setLayout(new BorderLayout());

        // Panel superior para criterios de búsqueda
        JPanel panelBusqueda = new JPanel(new GridLayout(3, 2));

        JLabel lblNombre = new JLabel("Nombre del paciente:");
        JTextField txtNombre = new JTextField();

        JLabel lblEdad = new JLabel("Edad mínima:");
        JTextField txtEdad = new JTextField();

        JButton btnBuscar = new JButton("Buscar");

        panelBusqueda.add(lblNombre);
        panelBusqueda.add(txtNombre);
        panelBusqueda.add(lblEdad);
        panelBusqueda.add(txtEdad);
        panelBusqueda.add(new JLabel());  // Espacio vacío
        panelBusqueda.add(btnBuscar);

        dialog.add(panelBusqueda, BorderLayout.NORTH);

        // Tabla para mostrar resultados
        String[] columnNames = {"Nombre", "Edad", "RUT"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;  // Esto hace que ninguna celda sea editable
            }
        };

        final JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        dialog.add(scrollPane, BorderLayout.CENTER);

        btnBuscar.addActionListener(e -> {
            String nombre = txtNombre.getText();
            int edadMinima = -1;
            try {
                edadMinima = Integer.parseInt(txtEdad.getText());
            } catch (NumberFormatException ex) {
                // No se ingresó una edad válida
            }
            actualizarTablaConCriterio(nombre, edadMinima, table);
        });

        dialog.setVisible(true);
    }

    public void actualizarTablaConCriterio(String nombre, int edadMinima, JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);  // Limpia la tabla

        ArrayList<Paciente> pacientes = sistema.obtenerPacientesPorCriterio(nombre, edadMinima);
        for (Paciente paciente : pacientes) {
            Object[] rowData = {paciente.getNombre(), paciente.getEdad(), paciente.getRut()};
            model.addRow(rowData);
        }
    }

}
