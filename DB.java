import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
    private static final String URL = "jdbc:mysql://34.172.45.195:3306/proyectosia";
    private static final String USER = "dmv";
    private static final String PASSWORD = "123456";
    private Connection connection;

    public void conectarseADB() {
        connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}