import java.util.*;

public class Sistema {
    private HashMap<String, Paciente> mapaPacientes = new HashMap<String, Paciente>();


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

    public Paciente buscarPaciente(String rut) {
        return mapaPacientes.get(rut);
    }

}




