package persistence;

import persistence.contracts.PoolConnections;

import java.sql.Connection;

public class DBConnection implements persistence.contracts.DBConnection{
    private final PoolConnections poolConnections;

    public DBConnection(PoolConnections poolConnections) {

        if (poolConnections != null) {
            this.poolConnections = poolConnections;
        } else {
            this.poolConnections = new DefaultConnection();
        }

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
