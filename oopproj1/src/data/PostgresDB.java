package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresDB {
    public Connection getConnection() throws SQLException, ClassNotFoundException {
        String connectionUrl = "jdbc:postgresql://localhost:5432/postgres"; // Твое имя БД
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(connectionUrl, "postgres", "12345"); // Твой логин и пароль
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
