package com.java.quizApplication;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/quiz_app";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "admin";  // Changed to match your working password
    
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public static void initDatabase() {
        try {
            System.out.println("🔄 Initializing database...");
            createDatabase();
            createTables();
            insertDefaultQuestions();
            System.out.println("✅ Database initialization complete!");
        } catch (SQLException e) {
            System.err.println("❌ Database initialization failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void createDatabase() throws SQLException {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", DB_USER, DB_PASS)) {
            conn.createStatement().execute("CREATE DATABASE IF NOT EXISTS quiz_app");
        }
    }
    
    private static void createTables() throws SQLException {
        try (Connection conn = getConnection()) {
            conn.createStatement().execute("""
                CREATE TABLE IF NOT EXISTS users (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(100) NOT NULL,
                    email VARCHAR(150) UNIQUE,
                    best_score INT DEFAULT 0
                )
            """);
            
            conn.createStatement().execute("""
                CREATE TABLE IF NOT EXISTS questions (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    question TEXT NOT NULL,
                    option1 VARCHAR(255) NOT NULL,
                    option2 VARCHAR(255) NOT NULL,
                    option3 VARCHAR(255) NOT NULL,
                    option4 VARCHAR(255) NOT NULL,
                    correct_answer VARCHAR(255) NOT NULL
                )
            """);
            
            conn.createStatement().execute("""
                CREATE TABLE IF NOT EXISTS quiz_attempts (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    user_id INT,
                    score INT NOT NULL,
                    attempt_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    FOREIGN KEY (user_id) REFERENCES users(id)
                )
            """);
        }
    }
    
    public static int saveUser(String name, String email) {
        try (Connection conn = getConnection()) {
            System.out.println("🔄 Saving user: " + name + " (" + email + ")");
            
            // Check if user exists
            PreparedStatement check = conn.prepareStatement("SELECT id FROM users WHERE email = ?");
            check.setString(1, email);
            ResultSet rs = check.executeQuery();
            if (rs.next()) {
                int existingId = rs.getInt("id");
                System.out.println("👤 User already exists with ID: " + existingId);
                return existingId;
            }
            
            // Create new user
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO users (name, email) VALUES (?, ?)", 
                Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.executeUpdate();
            
            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                int newId = keys.getInt(1);
                System.out.println("✅ New user created with ID: " + newId);
                return newId;
            }
            return -1;
        } catch (SQLException e) {
            System.err.println("❌ Error saving user: " + e.getMessage());
            e.printStackTrace();
            return -1;
        }
    }
    
    public static String[][] getRandomQuestions() {
        List<String[]> questions = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                 "SELECT question, option1, option2, option3, option4 FROM questions ORDER BY RAND() LIMIT 10")) {
            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                questions.add(new String[]{
                    rs.getString(1), rs.getString(2), rs.getString(3), 
                    rs.getString(4), rs.getString(5)
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions.toArray(new String[0][]);
    }
    
    public static String[] getRandomAnswers() {
        List<String> answers = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                 "SELECT correct_answer FROM questions ORDER BY RAND() LIMIT 10")) {
            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                answers.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return answers.toArray(new String[0]);
    }
    
    public static void saveQuizAttempt(int userId, int score) {
        try (Connection conn = getConnection()) {
            // Save attempt
            PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO quiz_attempts (user_id, score) VALUES (?, ?)");
            stmt.setInt(1, userId);
            stmt.setInt(2, score);
            stmt.executeUpdate();
            
            // Update best score
            PreparedStatement update = conn.prepareStatement(
                "UPDATE users SET best_score = GREATEST(best_score, ?) WHERE id = ?");
            update.setInt(1, score);
            update.setInt(2, userId);
            update.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static int getUserBestScore(int userId) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT best_score FROM users WHERE id = ?")) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    public static List<QuizAttempt> getUserHistory(int userId) {
        List<QuizAttempt> attempts = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                 "SELECT score, attempt_date FROM quiz_attempts WHERE user_id = ? ORDER BY attempt_date DESC LIMIT 5")) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                attempts.add(new QuizAttempt(rs.getInt(1), rs.getString(2)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return attempts;
    }
    
    private static void insertDefaultQuestions() throws SQLException {
        try (Connection conn = getConnection()) {
            PreparedStatement check = conn.prepareStatement("SELECT COUNT(*) FROM questions");
            ResultSet rs = check.executeQuery();
            rs.next();
            if (rs.getInt(1) > 0) return;
            
            String[] questions = {
                "INSERT INTO questions VALUES (null, 'Which is used to find and fix bugs in Java?', 'JVM', 'JDB', 'JDK', 'JRE', 'JDB')",
                "INSERT INTO questions VALUES (null, 'What is the return type of hashCode() method?', 'int', 'Object', 'long', 'void', 'int')",
                "INSERT INTO questions VALUES (null, 'Which package contains Random class?', 'java.util', 'java.lang', 'java.awt', 'java.io', 'java.util')",
                "INSERT INTO questions VALUES (null, 'Interface with no fields/methods is?', 'Runnable', 'Abstract', 'Marker', 'CharSequence', 'Marker')",
                "INSERT INTO questions VALUES (null, 'String memory location with new operator?', 'Stack', 'String memory', 'Random space', 'Heap', 'Heap')",
                "INSERT INTO questions VALUES (null, 'Which is a marker interface?', 'Runnable', 'Remote', 'Readable', 'Result', 'Remote')",
                "INSERT INTO questions VALUES (null, 'Keyword for package access?', 'import', 'package', 'extends', 'export', 'import')",
                "INSERT INTO questions VALUES (null, 'JAR stands for?', 'Java Archive Runner', 'Java Archive', 'Java App Resource', 'Java App Runner', 'Java Archive')",
                "INSERT INTO questions VALUES (null, 'Which is mutable in Java?', 'StringBuilder', 'Short', 'Byte', 'String', 'StringBuilder')",
                "INSERT INTO questions VALUES (null, 'Java portability comes from?', 'Bytecode executed by JVM', 'Applets', 'Exception handling', 'Dynamic binding', 'Bytecode executed by JVM')"
            };
            
            for (String sql : questions) {
                conn.createStatement().execute(sql);
            }
        }
    }
    
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }
    
    // Debug method to view stored data
    public static void showStoredData() {
        System.out.println("\n📊 STORED DATA OVERVIEW:");
        System.out.println("==================================================");
        
        try (Connection conn = getConnection()) {
            // Show users
            System.out.println("\n👥 USERS:");
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.printf("ID: %d | Name: %s | Email: %s | Best Score: %d%n", 
                    rs.getInt("id"), rs.getString("name"), 
                    rs.getString("email"), rs.getInt("best_score"));
            }
            
            // Show recent quiz attempts
            System.out.println("\n📝 RECENT QUIZ ATTEMPTS:");
            stmt = conn.prepareStatement(
                "SELECT u.name, qa.score, qa.attempt_date FROM quiz_attempts qa " +
                "JOIN users u ON qa.user_id = u.id ORDER BY qa.attempt_date DESC LIMIT 10"
            );
            rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.printf("User: %s | Score: %d | Date: %s%n",
                    rs.getString("name"), rs.getInt("score"), rs.getString("attempt_date"));
            }
            
            // Show question count
            stmt = conn.prepareStatement("SELECT COUNT(*) as total FROM questions");
            rs = stmt.executeQuery();
            if (rs.next()) {
                System.out.println("\n❓ QUESTIONS: " + rs.getInt("total") + " total questions available");
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error viewing data: " + e.getMessage());
        }
        
        System.out.println("==================================================");
    }
    
    public static class QuizAttempt {
        private int score;
        private String date;
        
        public QuizAttempt(int score, String date) {
            this.score = score;
            this.date = date;
        }
        
        public int getScore() { return score; }
        public String getDate() { return date; }
    }
} 