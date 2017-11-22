package persistence;

import persistence.contracts.PoolConnections;

import java.sql.Connection;

public class DBConnection implements persistence.contracts.DBConnection{
    private final PoolConnections poolConnections;

    public DBConnection() {
        poolConnections = new DefaultConnection();
    }

    public DBConnection(PoolConnections poolConnections) {
        this.poolConnections = poolConnections;
        poolConnections.configurePool();
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
