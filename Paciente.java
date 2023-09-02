import java.util.*;
public class Paciente {
    private String nombre;
    private int edad;
    private String rut;

    private ArrayList<ConsultaMedica> consultas;

    public Paciente(String var1, int var2, String var3) {
        this.nombre = var1;
        this.edad = var2;
        this.rut = var3;
        this.consultas = new ArrayList<>();
    }

    public void setNombre(String var1) {
        this.nombre = var1;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setEdad(int var1) {
        this.edad = var1;
    }

    public int getEdad() {
        return this.edad;
    }

    public void setRut(String var1) {
        this.rut = var1;
    }

    public String getRut() {
        return this.rut;
    }

    public void agregarConsulta(ConsultaMedica consultaMedica) {
        consultas.add(consultaMedica);
    }

    public boolean noHayConsultas() {
        return consultas.isEmpty();
    }

    public ConsultaMedica buscarConsulta(String nombreConsulta) {
        for (ConsultaMedica consulta : consultas) {
            if (consulta.getIdentificadorConsulta().equals(nombreConsulta)) {
                return consulta;
            }
        }
        return null;
    }

     public ConsultaMedica buscarConsulta(String nombreConsulta, String motivoBuscado) {
        for (ConsultaMedica consulta : consultas) {
            if (consulta.getIdentificadorConsulta().equals(nombreConsulta)) {
                return consulta;
            }
        }
        return null;
    }

    public void eliminarConsulta(ConsultaMedica consulta) {
        consultas.remove(consulta);
    }

    public void listarConsultasPorPaciente() {
        for (ConsultaMedica consulta : consultas) {
            System.out.println("Nombre: " + consulta.getMedico());
            System.out.println("Hora: " + consulta.getHora());
            System.out.println("Fecha: " + consulta.getFecha());
            System.out.println("Motivo de visita: " + consulta.getMotivoVisita());
            System.out.println("Descripcion: " + consulta.getDescripcion());
            System.out.println("Identificador de consulta: " + consulta.getIdentificadorConsulta());
            System.out.println("");
        }
    }

    public void listarConsultasPorPaciente(String motivoBuscado) {
        boolean hayConsultas = false;
        for (ConsultaMedica consulta : consultas) {
            if(consulta.getMotivoVisita().contains(motivoBuscado)) {
                System.out.println("Nombre: " + consulta.getMedico());
                System.out.println("Hora: " + consulta.getHora());
                System.out.println("Fecha: " + consulta.getFecha());
                System.out.println("Motivo de visita: " + consulta.getMotivoVisita());
                System.out.println("Descripcion: " + consulta.getDescripcion());
                System.out.println("Identificador de consulta: " + consulta.getIdentificadorConsulta());
                System.out.println("");
                hayConsultas = true;
            }
        }

        if(!hayConsultas) {
            System.out.println("No hay consultas con ese motivo en este paciente!");
            System.out.println("");
        }
    }

}