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



    public void eliminarConsulta(int index) throws ConsultaNoEncontradaException {
        if (index < 0 || index >= consultas.size()) {
            throw new ConsultaNoEncontradaException();
        }
        consultas.remove(index);
    }

    public void inicializarConsultas(ArrayList<ConsultaMedica> lista) {
        for (ConsultaMedica consulta : consultas) {
            lista.add(consulta);
        }
    }
}