import java.sql.*;

public class TestConnection {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/";
        String username = "root";
        String password = "admin"; // Replace with your actual password
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);
            
            System.out.println("✅ MySQL Connection Successful!");
            System.out.println("✅ JDBC Driver Working!");
            System.out.println("Database: " + connection.getMetaData().getDatabaseProductName());
            System.out.println("Version: " + connection.getMetaData().getDatabaseProductVersion());
            
            // Test creating database
            connection.createStatement().execute("CREATE DATABASE IF NOT EXISTS quiz_app");
            System.out.println("✅ Database 'quiz_app' created/verified!");
            
            connection.close();
            System.out.println("✅ Ready to run Quiz Application!");
        } catch (Exception e) {
            System.out.println("❌ Connection Failed:");
            e.printStackTrace();
            System.out.println("\n💡 Check:");
            System.out.println("1. MySQL service is running");
            System.out.println("2. Password is correct");
            System.out.println("3. JAR file is in lib/ folder");
        }
    }
} 