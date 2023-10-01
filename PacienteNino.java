public class PacienteNino extends Paciente {
    public PacienteNino(String var1, int var2, String var3) {
        super(var1, var2, var3);
    }

    @Override
    public void agregarConsulta(ConsultaMedica consultaMedica) {

        String descripcionActual = consultaMedica.getDescripcion();
        String descripcionConNota = descripcionActual + " (Autorizado por tutor)";

        consultaMedica.setDescripcion(descripcionConNota);

        super.agregarConsulta(consultaMedica);
    }
}
