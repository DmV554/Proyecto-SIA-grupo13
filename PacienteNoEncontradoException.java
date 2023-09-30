
public class PacienteNoEncontradoException extends Exception {
    public PacienteNoEncontradoException() {
        super("El paciente no se encontró en el sistema");
    }
}
