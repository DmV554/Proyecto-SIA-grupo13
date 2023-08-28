import java.util.*;

public class Sistema {
    HashMap<String, Paciente> mapaPacientes = new HashMap<String, Paciente>();

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
            System.out.println("El paciente a sido eliminado correctamente!");
        }
    }

    public void mostrarPacientes() {
        if (mapaPacientes.isEmpty()) {
            System.out.println("No hay pacientes registrados");
            System.out.println("");

            return;
        }


        for (Paciente paciente : mapaPacientes.values()) {
            System.out.println("Nombre: " + paciente.getNombre());
            System.out.println("Edad: " + paciente.getEdad());
            System.out.println("Rut: " + paciente.getRut());
            System.out.println("");

        }
    }

    public void agregarCita() {
    }

    public void eliminarCita() {
    }

    public void listarConsultasPorPaciente() {
    }
}