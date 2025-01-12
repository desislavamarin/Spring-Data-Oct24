package p02;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    private static Connection connection;
    public DBConnection(String dbName, String user, String password) throws SQLException {
        Properties properties = new Properties();
        properties.setProperty("user", user);
        properties.setProperty("password", password);
        try {
            connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/" + dbName, properties);
        } catch (SQLException e) {
            System.out.println("There was problem initializing the database connection");
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        return connection;
    }

}
