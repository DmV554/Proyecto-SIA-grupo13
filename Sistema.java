import java.util.*;

public class Sistema {
    private HashMap<String, Paciente> mapaPacientes;

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

    public ArrayList<Paciente> crearListaPacientes() {
        return new ArrayList<Paciente>(mapaPacientes.values());
    }

}




