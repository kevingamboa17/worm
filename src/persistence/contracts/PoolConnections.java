package persistence.contracts;

import java.sql.Connection;
interface PoolConnections {
    public Connection getConnection();
    public void closeConnection(Connection connection);
}
