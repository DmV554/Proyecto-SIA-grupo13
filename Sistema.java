import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import javax.swing.table.DefaultTableModel;

public class Sistema {
    private HashMap<String, Paciente> mapaPacientes;
    private ArrayList<ConsultaMedica> listaTodasConsultas;

    public Sistema() {
        mapaPacientes = new HashMap<String, Paciente>();
    }

    public void agregarPaciente(Paciente paciente) {
        mapaPacientes.put(paciente.getRut(), paciente);
    }

    public void agregarPaciente(Connection connection, Paciente paciente) throws SQLException {
        String sql = "INSERT INTO pacientes (rut, nombre, edad) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, paciente.getRut());
            preparedStatement.setString(2, paciente.getNombre());
            preparedStatement.setInt(3, paciente.getEdad());

            preparedStatement.executeUpdate();
        }
        mapaPacientes.put(paciente.getRut(), paciente);
    }


    public boolean existePaciente(String rutBuscado) throws CamposVaciosException {
        if (rutBuscado == null || rutBuscado.trim().isEmpty()) {
            throw new CamposVaciosException("El RUT proporcionado está vacío o es nulo.");
        }
        return mapaPacientes.containsKey(rutBuscado);
    }
    public void eliminarPaciente(String rutEliminar) {
        if(mapaPacientes.containsKey(rutEliminar)) {
            mapaPacientes.remove(rutEliminar);
        }
    }


    public Paciente buscarPaciente(String rut) throws PacienteNoEncontradoException {
        Paciente paciente = mapaPacientes.get(rut);
        if (paciente == null) {
            throw new PacienteNoEncontradoException();
        }
        return paciente;
    }


    public ArrayList<Paciente> obtenerPacientesPorCriterio(String nombre, int edadMinima) {
        ArrayList<Paciente> resultados = new ArrayList<>();
        for (Paciente paciente : mapaPacientes.values()) {
            if ((nombre.isEmpty() || paciente.getNombre().contains(nombre)) &&
                    (edadMinima == -1 || paciente.getEdad() >= edadMinima)) {
                resultados.add(paciente);
            }
        }
        return resultados;
    }

    public void llenarListaConsultas() {
        listaTodasConsultas = new ArrayList<>();
        for (Paciente paciente : mapaPacientes.values()) {
            paciente.inicializarConsultas(listaTodasConsultas);
        }
    }

    public void agregarConsulta(ConsultaMedica consulta) {
        listaTodasConsultas.add(consulta);
    }

    public void agregarConsultaTabla(DefaultTableModel model) {
        for (ConsultaMedica consulta : listaTodasConsultas) {
                model.addRow(new Object[]{consulta.getMedico(), consulta.getHora(), consulta.getFecha(), consulta.getMotivoVisita(), consulta.getDescripcion()});
        }
    }

    public void agregarPacientesTabla(DefaultTableModel model) {
        for (Paciente paciente : mapaPacientes.values()) {
            model.addRow(new Object[]{paciente.getNombre(), paciente.getEdad(), paciente.getRut()});
        }
    }

}




