import java.sql.Connection;
import java.sql.DriverManager;

public class TestSQLServer {
    public static void main(String[] args) {
        String url = "jdbc:sqlserver://localhost:1433;databaseName=tarea4;encrypt=true;trustServerCertificate=true";
        String user = "Login en SQLServer";
        String password = "Password en SQLServer";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("¡Conexión exitosa con SQL Server!");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}