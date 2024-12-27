package com.example.crudapp;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseHelper {
    private static final String URL = "jdbc:sqlite:crudapp.db"; // Путь к базе данных
    private static final String DRIVER = "org.sqlite.JDBC";
    private static Connection connection;

    // Метод для получения соединения с базой данных
    public static Connection getConnection() throws SQLException {
        if (connection == null) {
            try {
                Class.forName(DRIVER); // Загружаем драйвер SQLite
                connection = DriverManager.getConnection(URL);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    // Метод для закрытия соединения с базой данных
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

