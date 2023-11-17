package dao.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL_Runtime = "jdbc:oracle:thin:@im2ag-oracle.univ-grenoble-alpes.fr:1521:im2ag";
    private static final String USER_Runtime = "baydouna";
    private static final String PASSWORD_Runtime = "e449c762e6";

     public static Connection getConnection() throws SQLException {
            return DriverManager.getConnection(URL_Runtime, USER_Runtime, PASSWORD_Runtime);
    }
}
