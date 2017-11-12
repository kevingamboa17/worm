package persistence;

import java.sql.Connection;
interface PoolConnections {
    public Connection getConnection();
    public void closeConnection(Connection connection);
}
