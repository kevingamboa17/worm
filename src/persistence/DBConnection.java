package persistence;

import persistence.contracts.PoolConnections;

import java.sql.Connection;

public class DBConnection implements persistence.contracts.DBConnection{
    private final PoolConnections poolConnections;

    public DBConnection(String dbName, String host, String port, String user, String password) {
        this.poolConnections = new DefaultConnection(dbName, host, port, user, password);
        this.poolConnections.configurePool();
    }

    public DBConnection(PoolConnections poolConnections) {
        this.poolConnections = poolConnections;
        this.poolConnections.configurePool();
    }

    @Override
    public Connection getConnection() {
        return poolConnections.getConnection();
    }

    @Override
    public void closeConnection(Connection connection) {
        poolConnections.closeConnection(connection);
    }
}
