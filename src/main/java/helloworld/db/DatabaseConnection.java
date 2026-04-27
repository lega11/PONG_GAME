package helloworld.db;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Handles the database connection for the program.
 * Implemented as a Singleton so one connection is reused.
 */
public class DatabaseConnection {

    private static Connection connection;

    /**
     * Private constructor to prevent direct object creation.
     */
    private DatabaseConnection() {
    }

    /**
     * Returns the shared database connection.
     *
     * @return database connection or null if connection failed
     */
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");

                String url = "jdbc:mysql://127.0.0.1:3306/pong_db";
                String user = "root";
                String password = "MyNewPass";

                connection = DriverManager.getConnection(url, user, password);
                System.out.println("Database connected successfully.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return connection;
    }
}