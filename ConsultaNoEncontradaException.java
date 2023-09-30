
public class ConsultaNoEncontradaException extends Exception {
    public ConsultaNoEncontradaException() {
        super("La consulta no se encontró para el paciente especificado");
    }
}
