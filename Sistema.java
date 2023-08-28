import java.util.Map;
import java.util.HashMap;

public class Sistema {
    Map<String, Paciente> mapaPacientes = new HashMap<String, Paciente>();

    public Sistema() {
    }

    public void agregarPaciente(Paciente paciente) {
        if(mapaPacientes.containsKey(paciente.getRut())) {
            System.out.println("El paciente ya existe, volviendo al menu principal");
        } else {
            mapaPacientes.put(paciente.getRut(), paciente);
            System.out.println("Paciente agregado");
        }
        System.out.println("");
    }

    public boolean existePaciente(String rutBuscado) {
        Paciente primerPaciente;
        if (mapaPacientes.containsKey(rutBuscado)) {
            System.out.println("El paciente ya existe, volviendo al menú principal");
            System.out.println("");
            return false;
        }
        return true;
    }


    public void eliminarPaciente() {
    }

    public void mostrarPacientes() {
        if (mapaPacientes.isEmpty()) {
            System.out.println("No hay pacientes registrados");
            System.out.println("");
            return;
        }

        Paciente primerPaciente;

        int indice = 0;
        for (Paciente paciente : mapaPacientes.values()) {
            System.out.println("Nombre: " + paciente.getNombre());
            System.out.println("Edad: " + paciente.getEdad());
            System.out.println("Rut: " + paciente.getRut());
            System.out.println("");
            indice++;
        }
    }

    public void agregarCita() {
    }

    public void eliminarCita() {
    }

    public void listarConsultasPorPaciente() {
    }
}