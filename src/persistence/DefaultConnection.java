package persistence;

import persistence.contracts.PoolConnections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The {@code DefaultConnection} class represents a customized <code>Connection</code>,
 * there is defined the JDBC driver to establish a connection with a specific database.
 */
public class DefaultConnection implements PoolConnections {

    /** The name of driver to establish connections */
    private final String JDBC_DRIVER = "com.mysql.jdbc.Driver";

    /** The host name where database is going to be */
    private final String HOST;

    /** The port number where database is going to run */
    private final String PORT;

    /** The database name*/
    private final String DATABASE;

    /** The username of the database */
    private final String USER;

    /** The password of the database */
    private final String PASSW;

    /** The complete database url*/
    private final String DATABASE_URL;

    /**
     * Initializes a newly created {@code DefaultConnection} object that is
     * responsible of the connection with JDBC driver.
     * @param DATABASE The database's name.
     * @param HOST  The host name or IP where the database was created.
     * @param PORT The port number where the database is running.
     * @param USER The username to get access into database.
     * @param PASSW The password to get access into database.
     */
    public DefaultConnection(String DATABASE, String HOST, String PORT, String USER, String PASSW) {
        this.HOST = HOST;
        this.PORT = PORT;
        this.DATABASE = DATABASE;
        this.USER = USER;
        this.PASSW = PASSW;
        this.DATABASE_URL = "jdbc:mysql://" + this.HOST + ":" + this.PORT + "/" + this.DATABASE + "?useSSL=false";
    }

    /**
     * Gets a <code>Connection</code> object through JDBC driver.
     * @return New <code>Connection</code>.
     */
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


    /**
     * Closes an opened <code>Connection</code>.
     * @param connection
     */
    @Override
    public void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
