import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

    }
}

class Sistema {
    Pacientes[] pacientes = new Pacientes[100];

    public void Sistema() {

    }

    public void agregarPaciente() {

    }

    public void eliminarPaciente() {

    }

    public void mostrarPacientes() {

    }

    public void agregarCita() {

    }

    public void eliminarCita() {

    }

    public void listarConsultasPorPaciente() {

    }
}

class Pacientes {
    private String nombre;
    private int edad;
    private String rut;
    private String enfermedades;
    ConsultaMedica[] consultas = new ConsultaMedica[30];

    public void Pacientes(String nombre, int edad, String rut, String enfermedades) {
        this.nombre = nombre;
        this.edad = edad;
        this.rut = rut;
        this.enfermedades = enfermedades;
    }

}



class ConsultaMedica {
    private String descripcion;
    private String hora;
    private String fecha;
    private String medico;
    private String motivoVisita;

    public void ConsultaMedica() {

    }
}