import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class VentanaPrincipal {
    Sistema sistema = new Sistema();

    public void crearVentana() {
        JFrame frame = new JFrame("Sistema de Manejo de Salud");
        frame.setSize(400, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(13, 1));

        JButton btnAgregarPaciente = new JButton("Agregar paciente");
        btnAgregarPaciente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirDialogoAgregarPaciente();
            }
        });
        frame.add(btnAgregarPaciente);

        JButton btnMostrarPacientes = new JButton("Mostrar pacientes");
        btnMostrarPacientes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirDialogoMostrarPacientes();
            }
        });
        frame.add(btnMostrarPacientes);

        JButton btnEliminar = new JButton("Eliminar paciente");
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirDialogoEliminarPaciente();
            }
        });
        frame.add(btnEliminar);

        JButton btnSalir = new JButton("Salir del sistema");
        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        frame.add(btnSalir);
        
        frame.setVisible(true);
    }

    public void abrirDialogoAgregarPaciente() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Agregar Paciente");
        dialog.setSize(300, 200);
        dialog.setLayout(new GridLayout(4, 2));

        JLabel lblNombre = new JLabel("Nombre:");
        JTextField txtNombre = new JTextField();
        JLabel lblEdad = new JLabel("Edad:");
        JTextField txtEdad = new JTextField();
        JLabel lblRut = new JLabel("RUT:");
        JTextField txtRut = new JTextField();

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String rut = txtRut.getText();

                if(sistema.existePaciente(rut)) {
                    JOptionPane.showMessageDialog(null, "Ya existe un paciente con ese RUT");
                    return;
                }

                String nombre = txtNombre.getText();
                int edad = Integer.parseInt(txtEdad.getText());
                
                Paciente nuevoPaciente = new Paciente(nombre, edad, rut);
                sistema.agregarPaciente(nuevoPaciente);
                dialog.dispose();
            }
        });

        dialog.add(lblNombre);
        dialog.add(txtNombre);
        dialog.add(lblEdad);
        dialog.add(txtEdad);
        dialog.add(lblRut);
        dialog.add(txtRut);
        dialog.add(new JLabel());  // Espacio vacío
        dialog.add(btnGuardar);
        dialog.setVisible(true);
    }

    public void abrirDialogoMostrarPacientes() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Mostrar Pacientes");
        dialog.setSize(400, 400);
        dialog.setLayout(new BorderLayout());

        ArrayList<Paciente> pacientes = sistema.crearListaPacientes();
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Paciente paciente : pacientes) {
            String detallePaciente = "Nombre: " + paciente.getNombre() +
                    ", Edad: " + paciente.getEdad() +
                    ", RUT: " + paciente.getRut();
            listModel.addElement(detallePaciente);
        }

        JList<String> listaPacientes = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(listaPacientes);
        dialog.add(scrollPane, BorderLayout.CENTER);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        dialog.add(btnCerrar, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    public void abrirDialogoEliminarPaciente() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Eliminar Paciente");
        dialog.setSize(300, 200);
        dialog.setLayout(new GridLayout(2, 2));

        JLabel lblRut = new JLabel("RUT:");
        JTextField txtRut = new JTextField();

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String rut = txtRut.getText();

                if(!sistema.existePaciente(rut)) {
                    JOptionPane.showMessageDialog(null, "No existe un paciente con ese RUT");
                    return;
                }

                sistema.eliminarPaciente(rut);
                dialog.dispose();
            }
        });

        dialog.add(lblRut);
        dialog.add(txtRut);
        dialog.add(new JLabel());  // Espacio vacío
        dialog.add(btnEliminar);
        dialog.setVisible(true);
    }

}