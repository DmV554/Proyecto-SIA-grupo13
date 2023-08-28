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
            System.out.println("El paciente ha sido eliminado correctamente!");
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

    public void agregarCita(ConsultaMedica consultaMedica, String rut) {

        Paciente auxiliar = mapaPacientes.get(rut);

        auxiliar.consultas.add(consultaMedica);

    }

    public void eliminarCita() {
    }

    public void listarConsultasPorPaciente() {
        int indice = 0;

        for(Paciente auxiliar : mapaPacientes.values()) {
            while(indice != auxiliar.consultas.size()) {
                System.out.println("Nombre del paciente: "+ auxiliar.getNombre());
                System.out.println("Doctor de la cita: "+ auxiliar.consultas.get(indice).getMedico());
                System.out.println("Fecha de la cita: "+ auxiliar.consultas.get(indice).getFecha());
                System.out.println("Hora de la cita: "+ auxiliar.consultas.get(indice).getHora());
                System.out.println("Motivo de la cita: "+ auxiliar.consultas.get(indice).getMotivoVisita());
                System.out.println("Desripción de la cita: "+ auxiliar.consultas.get(indice).getDescripcion());
                System.out.println("");
                indice++;
            }
            indice = 0;

        }

    }
}