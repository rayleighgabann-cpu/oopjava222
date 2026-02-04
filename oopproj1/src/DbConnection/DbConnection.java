package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/bank_system";
    private static final String USER = "postgres";
    private static final String PASS = "your_password";
    private static DbConnection instance;
    private Connection conn;

    private DbConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            this.conn = DriverManager.getConnection(URL, USER, PASS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized Connection get() {
        if (instance == null) {
            instance = new DbConnection();
        }
        return instance.conn;
    }
}