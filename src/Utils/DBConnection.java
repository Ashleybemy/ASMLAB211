package Utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/drinkstore_db";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Nạp driver MySQL
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        // Kết nối và trả về đối tượng Connection
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
