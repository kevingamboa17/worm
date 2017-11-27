package persistence.contracts;

import java.sql.Connection;

public interface PoolConnections {
    Connection getConnection();
    void configurePool();
    void closeConnection(Connection connection);
}
