package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;

    // Cambia estos valores según tu configuración:
    private String url = "jdbc:sqlserver://localhost:1433;databaseName=tarea4;encrypt=true;trustServerCertificate=true";
    private String username = "Login en SQLServer";
    private String password = "Password en SQLServer";

    private DatabaseConnection() throws SQLException {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            this.connection = DriverManager.getConnection(url, username, password);
            System.out.println("Conexión exitosa a SQL Server!");
        } catch (ClassNotFoundException ex) {
            System.out.println("Error: Driver no encontrado: " + ex.getMessage());
        }
    }

    public static DatabaseConnection getInstance() throws SQLException {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}