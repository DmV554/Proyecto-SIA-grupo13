import java.util.ArrayList;

public class Sistema {
    ArrayList<Paciente> listaPacientes = new ArrayList<>();

    public Sistema() {
    }

    public void agregarPaciente(Paciente paciente) {
        if(listaPacientes.isEmpty()) {
            listaPacientes.add(paciente);
            System.out.println("Paciente agregado");
            System.out.println("");
            return;
        }

        listaPacientes.add(paciente);
        System.out.println("Paciente agregado");
    }

    public boolean existePaciente(String rutBuscado) {
        Paciente primerPaciente;

        int indice = 0;
        while(listaPacientes.size() > indice) {
            primerPaciente = listaPacientes.get(indice);
            if(primerPaciente.getRut().equals(rutBuscado)) {
                System.out.println("El paciente ya existe, volviendo al menu principal");
                System.out.println("");
                return false;
            }

            indice++; 
        }
        return true;
    }

    public void eliminarPaciente() {
    }

    public void mostrarPacientes() {
        if(listaPacientes.isEmpty()) {
            System.out.println("No hay pacientes registrados");
            System.out.println("");
            return;
        }

        Paciente primerPaciente;

        int indice = 0;
        while(listaPacientes.size() > indice) {
            primerPaciente = listaPacientes.get(indice);
            System.out.println("Nombre: " + primerPaciente.getNombre());
            System.out.println("Edad: " + primerPaciente.getEdad());
            System.out.println("Rut: " + primerPaciente.getRut());
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