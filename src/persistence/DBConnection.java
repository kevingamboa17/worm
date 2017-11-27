package persistence;

import persistence.contracts.PoolConnections;

import java.sql.Connection;

/**
 * The {@code DBConnection} class has the responsibility of generate objects type
 * <code>Connection</code> through its pool of connections. Also it's able to close
 * opened connection.
 */
public class DBConnection implements persistence.contracts.DBConnection{

    /**The <code>PoolConnections</code> object that provides all configuration
     * data for the database*/
    private final PoolConnections poolConnections;

    /**
     * Initializes a newly created {@code DBConnection} object with a
     * <code>DefaultConnection</code>, it's going to be responsible of
     * manage the database connections.
     * @param dbName The database's name.
     * @param host  The host name or IP where the database was created.
     * @param port  The port number where the database is running.
     * @param user  The username to get access into database.
     * @param password The password to get access into database.
     */
    public DBConnection(String dbName, String host, String port, String user, String password) {
        this.poolConnections = new DefaultConnection(dbName, host, port, user, password);
        this.poolConnections.configurePool();
    }

    /**
     * Initializes a newly created {@code DBConnection} object with a
     * <code>PoolConnection</code> already defined.
     *
     * @param poolConnections <>PoolConnection</> object that it has all
     *                         necessary data to get access into database.
     */
    public DBConnection(PoolConnections poolConnections) {
        this.poolConnections = poolConnections;
        this.poolConnections.configurePool();
    }

    /**
     * Provides a <code>Connection</code> from its <code>PoolConnection</code>
     * to create statements and do queries into database.
     *
     * @return A new <code>Connection</code> created by the pool of connections.
     */
    @Override
    public Connection getConnection() {
        return poolConnections.getConnection();
    }



    /**
     * This method closes opened connections.
     * @param connection A <code>Connection</code> that is going to be closed.
     */
    @Override
    public void closeConnection(Connection connection) {
        poolConnections.closeConnection(connection);
    }
}
