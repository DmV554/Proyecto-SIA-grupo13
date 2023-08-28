public class Paciente {
    private String nombre;
    private int edad;
    private String rut;
    private String enfermedades;
    ConsultaMedica[] consultas;

    public Paciente(String var1, int var2, String var3, String var4) {
        this.nombre = var1;
        this.edad = var2;
        this.rut = var3;
        this.enfermedades = var4;
        this.consultas = new ConsultaMedica[30];
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

    public void setEnfermedades(String var1) {
        this.enfermedades = var1;
    }

    public String getEnfermedades() {
        return this.enfermedades;
    }
}
