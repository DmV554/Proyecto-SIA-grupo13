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

    public void agregarCita(ConsultaMedica consultaMedica, String rut) {

        Paciente auxiliar = mapaPacientes.get(rut);

        auxiliar.consultas.add(consultaMedica);

    }

    public void eliminarCita(Paciente paciente, ConsultaMedica consulta) {
        paciente.consultas.remove(consulta);
    }

    public ConsultaMedica buscarConsulta(Paciente paciente, String nombreConsulta) {
        for (ConsultaMedica consulta : paciente.consultas) {
            if (consulta.getIdentificadorConsulta().equals(nombreConsulta)) {
                return consulta;
            }
        }
        return null;
    }

    

    public void listarConsultasPorPaciente(Paciente paciente) {
        for (ConsultaMedica consulta : paciente.consultas) {
            System.out.println("Nombre: " + consulta.getMedico());
            System.out.println("Hora: " + consulta.getHora());
            System.out.println("Fecha: " + consulta.getFecha());
            System.out.println("Motivo de visita: " + consulta.getMotivoVisita());
            System.out.println("Descripcion: " + consulta.getDescripcion());
            System.out.println("Identificador de consulta: " + consulta.getIdentificadorConsulta());
            System.out.println("");
        }

    }
}




