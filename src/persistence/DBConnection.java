package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection implements persistence.contracts.DBConnection{
    private final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private final String HOST = "localhost";
    private final String PORT = "5432";
    private final String DATABASE = "wormDB";
    private final String USER = "username";
    private final String PASSW = "password";

    private final String DATABASE_URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE;

    @Override
    public Connection getConnection() {
        try {
            Class.forName(JDBC_DRIVER);
            return DriverManager.getConnection(DATABASE_URL, USER, PASSW);
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
