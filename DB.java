import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class DB {
    private static final String URL = "jdbc:mysql://localhost:3306/proyectosia";
    private static final String USER = "root";
    private static final String PASSWORD = "anelaceelfead1712";

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
