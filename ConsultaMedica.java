import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConsultaMedica {
    private String medico;
    private String hora;
    private String fecha;
    private String motivoVisita;
    private String descripcion;
    private String rutAsociado;
    private int id;

    public ConsultaMedica(String medico, String hora, String fecha, String motivoVisita, String descripcion, String rutAsociado) {
        this.medico = medico;
        this.hora = hora;
        this.fecha = fecha;
        this.motivoVisita = motivoVisita;
        this.descripcion = descripcion;
        this.rutAsociado = rutAsociado;

    }

    public void setDescripcion(String var1) {
        this.descripcion = var1;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setHora(String var1) {
        this.hora = var1;
    }

    public String getHora() {
        return this.hora;
    }

    public void setFecha(String var1) {
        this.fecha = var1;
    }

    public String getFecha() {
        return this.fecha;
    }

    public void setMedico(String var1) {
        this.medico = var1;
    }

    public String getMedico() {
        return this.medico;
    }

    public void setMotivoVisita(String var1) {
        this.motivoVisita = var1;
    }

    public String getMotivoVisita() {
        return this.motivoVisita;
    }


    public String getRutAsociado() {
        return rutAsociado;
    }

    public int getId(Connection connection) throws SQLException {
        String sql = "SELECT idConsulta FROM consultas WHERE RUT = ? AND hora = ? AND fecha = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, rutAsociado);
            preparedStatement.setString(2, hora);
            preparedStatement.setString(3, fecha);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("idConsulta");
                } else {
                    // La consulta no encontró ningún resultado, debes manejar este caso adecuadamente
                    throw new SQLException("No se encontró.");
                }
            }
        }
    }

}