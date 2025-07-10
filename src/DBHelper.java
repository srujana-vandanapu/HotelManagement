import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBHelper {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/hotelmanagement";
        String user = "root";
        String password = "Srujana12121";

        return DriverManager.getConnection(url, user, password);
    }
}
