package persistence.contracts;

import java.sql.Connection;

interface DBConnection {
    public Connection getConnection();
    public void closeConnection(Connection connection);
}
