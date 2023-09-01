import java.util.*;

public class Sistema {
    private HashMap<String, Paciente> mapaPacientes = new HashMap<String, Paciente>();


    public void agregarPacientesDefecto() {
        Paciente nuevo1 = new Paciente("Maximiliano Brante",20,"210302910");
        Paciente nuevo2 = new Paciente("Orlando Caullan",23,"197073920");
        Paciente nuevo3 = new Paciente("Benjamin Tamburini",25,"187676320");
        Paciente nuevo4 = new Paciente("Benjamin González",28,"180022032");
        Paciente nuevo5 = new Paciente("Javier Donoso",20,"213032930");
        Paciente nuevo6 = new Paciente("Eduardo Guerra",18,"219093290");

        nuevo1.agregarConsulta(new ConsultaMedica("Dr. Dominguez", "09:30", "23-10-2023",
                "Dolor de cabeza", "Malestar general", "X7"));

        nuevo2.agregarConsulta(new ConsultaMedica("Dr. García", "10:00", "22-09-2023",
                "Dolor de cabeza", "Malestar general", "X2"));

        nuevo3.agregarConsulta(new ConsultaMedica("Dra. Pérez", "15:30", "23-09-2023",
                "Dolor de garganta", "Fiebre", "X3"));

        nuevo4.agregarConsulta(new ConsultaMedica("Dr. Rodríguez", "14:15", "24-09-2023",
                "Dolor de espalda", "Fatiga", "X4"));

        nuevo5.agregarConsulta(new ConsultaMedica("Dra. Martínez", "11:45", "25-09-2023",
                "Dolor de estómago", "Náuseas", "X5"));

        nuevo6.agregarConsulta(new ConsultaMedica("Dr. López", "17:20", "26-09-2023",
                "Presión arterial alta", "Mareos", "X6"));


        mapaPacientes.put(nuevo1.getRut(),nuevo1);
        mapaPacientes.put(nuevo2.getRut(),nuevo2);
        mapaPacientes.put(nuevo3.getRut(),nuevo3);
        mapaPacientes.put(nuevo4.getRut(),nuevo4);
        mapaPacientes.put(nuevo5.getRut(),nuevo5);
        mapaPacientes.put(nuevo6.getRut(),nuevo6);

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

    public Paciente buscarPaciente(String rut) {
        return mapaPacientes.get(rut);
    }

}




