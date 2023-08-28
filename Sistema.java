import java.util.*;

public class Sistema {
    HashMap<String, Paciente> mapaPacientes = new HashMap<String, Paciente>();
    HashMap<String, ConsultaMedica> mapaCitas = new HashMap<String, ConsultaMedica>();

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

    public void agregarCita(ConsultaMedica consultaMedica) {

        String claveCita = generarClaveCita(consultaMedica.getMedico(), consultaMedica.getFecha(), consultaMedica.getHora());
        mapaCitas.put(claveCita, consultaMedica);

        System.out.println("Cita médica agregada:");
        System.out.println("Descripción: " + consultaMedica.getDescripcion());
        System.out.println("Hora: " + consultaMedica.getHora());
        System.out.println("Fecha: " + consultaMedica.getFecha());
        System.out.println("Médico: " + consultaMedica.getMedico());
        System.out.println("Motivo de Visita: " + consultaMedica.getMotivoVisita());
        System.out.println("");

    }
    private String generarClaveCita(String rutPaciente, String fecha, String hora) {
        return rutPaciente + " " + fecha + " " + hora;
    }
    public void eliminarCita() {
    }

    public void listarConsultasPorPaciente() {
    }
}