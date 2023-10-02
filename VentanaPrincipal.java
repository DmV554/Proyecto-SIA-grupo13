import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    private DB db = new DB();

    public VentanaPrincipal() {

        db.conectarseADB();
        Connection connection = db.getConnection();
        if(connection != null) {
            System.out.println("Conexión exitosa");
        } else {
            System.out.println("Error al conectar");
            System.exit(0);
        }

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM pacientes");
            while (resultSet.next()) {
                String rut = resultSet.getString("rut");
                String nombre = resultSet.getString("nombre");
                int edad = resultSet.getInt("edad");
                Paciente paciente = new Paciente(nombre, edad, rut);
        

                try (Statement statementConsultas = connection.createStatement()) {
                    ResultSet resultSetConsultas = statementConsultas.executeQuery("SELECT * FROM consultas WHERE rut = '" + rut + "'");
                    while (resultSetConsultas.next()) {
                        String idConsulta = resultSetConsultas.getString("idConsulta");
                        String medico = resultSetConsultas.getString("medico");
                        String fecha = resultSetConsultas.getString("fecha");
                        String hora = resultSetConsultas.getString("hora");
                        String motivo = resultSetConsultas.getString("motivoVisita");
                        String descripcion = resultSetConsultas.getString("descripcion");
                        ConsultaMedica consulta = new ConsultaMedica(medico, fecha, hora, motivo, descripcion, rut);
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
        frame.add(createButtonPanel("Editar o eliminar consulta", e -> abrirDialogoBuscarPacienteParaEditarConsulta()));
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


        JTextField txtNombre = createTextField(15);
        JTextField txtEdad = createTextField(15);
        JTextField txtRut = createTextField(15);


        addToPanel(panel, "Nombre:", txtNombre, gbc, 0);
        addToPanel(panel, "Edad:", txtEdad, gbc, 1);
        addToPanel(panel, "RUT:", txtRut, gbc, 2);

        JButton btnGuardar = createButton("Guardar", BUTTON_FONT);


        final Connection connection = db.getConnection();

        btnGuardar.addActionListener(e -> {
            try {
                String rut = txtRut.getText();
                String nombre = txtNombre.getText();
                String strEdad = txtEdad.getText();

                if (nombre.trim().isEmpty()) {
                    throw new CamposVaciosException("El campo Nombre está vacío.");
                }
                if (strEdad.trim().isEmpty()) {
                    throw new CamposVaciosException("El campo Edad está vacío.");
                }
                if (rut.trim().isEmpty()) {
                    throw new CamposVaciosException("El campo RUT está vacío.");
                }

                if (sistema.existePaciente(rut)) {
                    JOptionPane.showMessageDialog(null, "Ya existe un paciente con ese RUT");
                    return;
                }

                int edad = Integer.parseInt(strEdad);

                Paciente nuevoPaciente;
                if (edad < 15) {
                    nuevoPaciente = new PacienteNino(nombre, edad, rut);
                } else if(nombre.contains("*")) {
                    nuevoPaciente = new PacienteEmergencia(nombre,edad, rut);


                    JDialog emergenciaDialog = createDialog("Consulta de Emergencia", 400, 400);
                    JPanel emergenciaPanel = new JPanel(new GridBagLayout());
                    GridBagConstraints gbcEmergencia = new GridBagConstraints();
                    gbcEmergencia.insets = new Insets(10, 10, 10, 10);


                    JTextField txtHora = createTextField(15);
                    JTextField txtFecha = createTextField(15);
                    JTextField txtDescripcion = createTextField(15);


                    addToPanel(emergenciaPanel, "Hora:", txtHora, gbcEmergencia, 0);
                    addToPanel(emergenciaPanel, "Fecha:", txtFecha, gbcEmergencia, 1);
                    addToPanel(emergenciaPanel, "Descripción:", txtDescripcion, gbcEmergencia, 2);

                    JButton btnGuardarEmergencia = createButton("Guardar", BUTTON_FONT);
                    btnGuardarEmergencia.addActionListener(a -> {
                        String hora = txtHora.getText();
                        String fecha = txtFecha.getText();
                        String descripcion = txtDescripcion.getText();

                        ConsultaMedica consultaEmergencia = new ConsultaMedica("Emergencia", hora, fecha, "Emergencia: " ,descripcion, rut);

                        nuevoPaciente.agregarConsulta(consultaEmergencia);

                        try {
                            sistema.agregarConsulta(connection, consultaEmergencia, nuevoPaciente);

                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }

                        emergenciaDialog.dispose();
                    });

                } else nuevoPaciente = new Paciente(nombre, edad, rut);


                try {
                    sistema.agregarPaciente(connection, nuevoPaciente);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                dialog.dispose();

            } catch (CamposVaciosException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Por favor, ingrese una edad válida", "Error", JOptionPane.ERROR_MESSAGE);
            }
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
            try {
                final Connection connection = db.getConnection();
                Paciente paciente = sistema.buscarPaciente(rut);
                sistema.eliminarPaciente(connection,rut);
                dialog.dispose();
            } catch (PacienteNoEncontradoException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
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


        JTextField txtRut = createTextField(15);
        JTextField txtMedico = createTextField(15);
        JTextField txtFecha = createTextField(15);
        JTextField txtHora = createTextField(15);
        JTextField txtMotivo = createTextField(15);
        JTextField txtDescripcion = createTextField(15);


        addToPanel(panel, "RUT del paciente:", txtRut, gbc, 0);
        addToPanel(panel, "Médico:", txtMedico, gbc, 1);
        addToPanel(panel, "Fecha:", txtFecha, gbc, 2);
        addToPanel(panel, "Hora:", txtHora, gbc, 3);
        addToPanel(panel, "Motivo de visita:", txtMotivo, gbc, 4);
        addToPanel(panel, "Descripción:", txtDescripcion, gbc, 5);

        JButton btnGuardar = createButton("Guardar", BUTTON_FONT);
        btnGuardar.addActionListener(e -> {
            String rut = txtRut.getText();
            ConsultaMedica nuevaConsulta = new ConsultaMedica(txtMedico.getText(), txtFecha.getText(), txtHora.getText(), txtMotivo.getText(), txtDescripcion.getText(), rut);


            final Connection connection = db.getConnection();

            try {
                Paciente paciente = sistema.buscarPaciente(rut);

                if (paciente.getEdad() < 15) {
                    int confirmacion = JOptionPane.showConfirmDialog(null, "¿El paciente niño tiene autorización del tutor?", "Confirmación", JOptionPane.YES_NO_OPTION);
                    if (confirmacion != JOptionPane.YES_OPTION) {
                        JOptionPane.showMessageDialog(null, "No se pudo guardar la consulta debido a que no tenía autorización del tutor", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                paciente.agregarConsulta(nuevaConsulta);
                sistema.agregarConsulta(connection, nuevaConsulta, paciente);
                dialog.dispose();

            } catch (PacienteNoEncontradoException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
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

        String[] columnNames = {"Médico", "Fecha", "Hora", "Motivo", "Descripción", "RUT asociado"};
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
            try {
                Paciente paciente = sistema.buscarPaciente(rut);
                lblRutValor.setText("RUT ingresado: " + rut);

                model.setRowCount(0);

                ArrayList<ConsultaMedica> consultasPaciente = new ArrayList<>();
                paciente.inicializarConsultas(consultasPaciente);

                for (ConsultaMedica consulta : consultasPaciente) {
                    Object[] rowData = {
                            consulta.getMedico(),
                            consulta.getHora(),
                            consulta.getFecha(),
                            consulta.getMotivoVisita(),
                            consulta.getDescripcion(),
                            consulta.getRutAsociado()
                    };
                    model.addRow(rowData);
                }
            } catch (PacienteNoEncontradoException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
            try {
                Paciente paciente = sistema.buscarPaciente(rut);
                abrirDialogoMostrarConsultasParaEditar(paciente);
                dialog.dispose();
            } catch (PacienteNoEncontradoException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.add(lblRut);
        dialog.add(txtRut);
        dialog.add(new JLabel());
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
                return false;
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
                    try {
                        final Connection connection = db.getConnection();
                        ConsultaMedica consultaAux = paciente.eliminarConsulta(connection, selectedRow);
                        sistema.eliminarConsulta(consultaAux);

                    } catch (ConsultaNoEncontradaException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    } 
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
        dialog.add(new JLabel());
        dialog.add(btnGuardar);

        dialog.setVisible(true);
    }

    public void actualizarTablaEditarConsultas(DefaultTableModel model, Paciente paciente) {

        model.setRowCount(0);
        ArrayList<ConsultaMedica> consultas = new ArrayList<>();
        paciente.inicializarConsultas(consultas);


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
        panelBusqueda.add(new JLabel());
        panelBusqueda.add(btnBuscar);

        dialog.add(panelBusqueda, BorderLayout.NORTH);


        String[] columnNames = {"Nombre", "Edad", "RUT"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
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

            }
            actualizarTablaConCriterio(nombre, edadMinima, table);
        });

        dialog.setVisible(true);
    }

    public void actualizarTablaConCriterio(String nombre, int edadMinima, JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        ArrayList<Paciente> pacientes = sistema.obtenerPacientesPorCriterio(nombre, edadMinima);
        for (Paciente paciente : pacientes) {
            Object[] rowData = {paciente.getNombre(), paciente.getEdad(), paciente.getRut()};
            model.addRow(rowData);
        }
    }

}
