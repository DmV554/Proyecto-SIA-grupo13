import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

    public boolean esNino() {
        return false;
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

    public ConsultaMedica eliminarConsulta(Connection connection, int index) throws ConsultaNoEncontradaException, SQLException {
        if (index < 0 || index > consultas.size()) {
            System.out.println("XDDD");
            throw new ConsultaNoEncontradaException();
        }
        ConsultaMedica consultaAux = consultas.get(index);
        consultas.remove(index);
        System.out.println("DOU");
        int id = consultaAux.getId(connection);
        System.out.println("id: " + id);

        String sql = "DELETE FROM consultas WHERE idConsulta = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }

        return consultaAux;
    }

    public void inicializarConsultas(ArrayList<ConsultaMedica> lista) {
        for (ConsultaMedica consulta : consultas) {
            lista.add(consulta);
        }
    }

    public void actualizarConsulta(Connection connection, ConsultaMedica consulta, String nuevoMedico, String nuevaFecha, String nuevaHora, String nuevoMotivo) throws ConsultaNoEncontradaException, SQLException {
        int id = consulta.getId(connection);

        String sql = "UPDATE consultas SET medico = ?, fecha = ?, hora = ?, motivo = ? WHERE idConsulta = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, nuevoMedico);
            preparedStatement.setString(2, nuevaFecha);
            preparedStatement.setString(3, nuevaHora);
            preparedStatement.setString(4, nuevoMotivo);
            preparedStatement.setInt(5, id);
    
            int filasActualizadas = preparedStatement.executeUpdate();
            if (filasActualizadas == 0) {
                // La consulta no se actualizó, podría ser porque el ID no existe en la base de datos
                throw new SQLException("La consulta médica con ID " + id + " no se encontró en la base de datos.");
            }
        }
    }
}
    