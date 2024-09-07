package Fines;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnect {
    private Connection con;
    private String url = "jdbc:mysql://localhost:3306/dbit_fines";
    private String user = "root"; // Adjust as needed
    private String password = ""; // Adjust as needed

    public void connect() {
        try {
            con = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        if (con == null || !isConnectionValid()) {
            connect();
        }
        return con;
    }

    private boolean isConnectionValid() {
        try {
            return con != null && con.isValid(2); // Timeout in seconds
        } catch (SQLException e) {
            return false;
        }
    }

    public void close() {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
