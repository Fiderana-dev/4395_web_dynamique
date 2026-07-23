package model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Maconnexion {
    private static final String URL = "jdbc:postgresql://localhost:5432/ordidi";
    private static final String USER = "postgres";
    private static final String PASSWORD = "wawa";

    public static Connection getConnexion() throws SQLException {
        Connection connexion = null;
        try {
            Class.forName("org.postgresql.Driver");
            connexion = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver PostgreSQL introuvable", e);
        } catch (SQLException e) {
            throw new SQLException("Erreur de connexion à la base de données : " + e.getMessage(), e);
        }
        return connexion;
    }
}