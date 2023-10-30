package dao.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL_Runtime = "jdbc:oracle:thin:@im2ag-oracle.univ-grenoble-alpes.fr:1521:im2ag";
    private static final String USER_Runtime = "baydouna";
    private static final String PASSWORD_Runtime = "e449c762e6";
    
    static {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);
        }
    }

     public static Connection getConnection(boolean mode) throws SQLException {
        if (mode)
            return DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        else
            return DriverManager.getConnection(URL_Runtime, USER_Runtime, PASSWORD_Runtime);
    }
}