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
        System.out.println("Paciente agregado");
        System.out.println("");
    }


    public boolean existePaciente(String rutBuscado) {
        return mapaPacientes.containsKey(rutBuscado);
    }

    public void eliminarPaciente(String rutEliminar) {
        if(mapaPacientes.containsKey(rutEliminar)) {
            mapaPacientes.remove(rutEliminar);
            System.out.println("El paciente ha sido eliminado correctamente!");
        }
    }

    public void mostrarPacientes() {
        if (mapaPacientes.isEmpty()) {
            System.out.println("No hay pacientes registrados");
            System.out.println("");
            return;
        }

        System.out.println("======= PACIENTES DEL SISTEMA =======");  
        for (Paciente paciente : mapaPacientes.values()) {
            System.out.println("Nombre: " + paciente.getNombre());
            System.out.println("Edad: " + paciente.getEdad());
            System.out.println("Rut: " + paciente.getRut());
            System.out.println("");

        }
        System.out.println("=====================================");
        System.out.println("");
    }

    public void mostrarPacientes(int edadFiltro) {
        if (mapaPacientes.isEmpty()) {
            System.out.println("No hay pacientes registrados");
            System.out.println("");
            return;
        }
        int contador = 0;
        
        for (Paciente paciente : mapaPacientes.values()) {
            if(paciente.getEdad() > edadFiltro) {
                continue;
            }

            if(contador == 0) {
                System.out.println("======= PACIENTES DEL SISTEMA =======");
                contador++;  
            }

            System.out.println("Nombre: " + paciente.getNombre());
            System.out.println("Edad: " + paciente.getEdad());
            System.out.println("Rut: " + paciente.getRut());
            System.out.println("");
        }

        if(contador > 0) {
            System.out.println("=====================================");
            System.out.println("");
        } else {
            System.out.println("No hay pacientes registrados hasta esa edad");
            System.out.println("");
        }
        
    }

    public Paciente buscarPaciente(String rut) {
        return mapaPacientes.get(rut);
    }

    public void inicializarListaPacientes(ArrayList<Paciente> lista) {
        for (Paciente paciente : mapaPacientes.values()) {
            lista.add(paciente);
        }
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




