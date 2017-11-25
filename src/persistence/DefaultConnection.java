package persistence;

import persistence.contracts.PoolConnections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DefaultConnection implements PoolConnections {
    private final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private final String HOST;
    private final String PORT;
    private final String DATABASE;
    private final String USER;
    private final String PASSW;

    private final String DATABASE_URL;

    public DefaultConnection(String DATABASE, String HOST, String PORT, String USER, String PASSW) {
        this.HOST = HOST;
        this.PORT = PORT;
        this.DATABASE = DATABASE;
        this.USER = USER;
        this.PASSW = PASSW;
        this.DATABASE_URL = "jdbc:mysql://" + this.HOST + ":" + this.PORT + "/" + this.DATABASE + "?useSSL=false";
    }

    @Override
    public Connection getConnection() {
        try {
            Class.forName(JDBC_DRIVER);
            return DriverManager.getConnection(DATABASE_URL, USER, PASSW);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void configurePool() {
        // Not needed by default
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
