import entities.Student;

import java.sql.*;
import java.util.UUID;

public class SQLiteExample {

    public static Connection getConnection() {
        String url = "jdbc:sqlite:identifier.sqlite";

        try  {
            Connection connection = DriverManager.getConnection(url);
            System.out.println("Соединение с базой данных установлено.");
            return connection;

        } catch (SQLException e) {
            System.err.println("Ошибка при работе с базой данных: " + e.getMessage());
            return null;
        }
    }}



