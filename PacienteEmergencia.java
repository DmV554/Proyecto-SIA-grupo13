public class PacienteEmergencia extends Paciente {
    public PacienteEmergencia(String var1, int var2, String var3) {
        super(var1,var2,var3);
    }
    @Override
    public void agregarConsulta(ConsultaMedica consultaMedica) {
        if (verificarProtocoloEmergencia()) {
            super.agregarConsulta(consultaMedica);
        } else {
            System.out.println("Protocolo de emergencia no cumplido. No se puede agregar consulta.");
        }
    }

    private boolean verificarProtocoloEmergencia() {
        return true;
    }
}