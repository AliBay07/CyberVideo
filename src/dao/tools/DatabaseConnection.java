package dao.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL_Runtime = "jdbc:oracle:thin:@im2ag-oracle.univ-grenoble-alpes.fr:1521:im2ag";
    private static final String USER_Runtime = "baydouna";
    private static final String PASSWORD_Runtime = "e449c762e6";

    private static DatabaseConnection instance;
    private static Connection connection;

    private DatabaseConnection() {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            DatabaseConnection.connection = DriverManager.getConnection(URL_Runtime, USER_Runtime, PASSWORD_Runtime);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);
        }
    }

    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public static Connection getConnection() {
        return connection;
    }
}