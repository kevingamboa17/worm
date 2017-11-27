package persistence;

import persistence.contracts.DBQueryCompleted;
import persistence.contracts.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The BDExecuteQuery class is responsible for executing queries
 * on the server and if necessary, returns the result of the query
 */
public class DBExecuteQuery implements persistence.contracts.DBExecuteQuery {


    /** The object that provide a connection */
    private DBConnection dbConnection;

    /**
     * Initializes a newly created {@code DBExecuteQuery} object with the DBConnection required
     * @param dbConnection a instance of DBConnection that provide a connection
     */
    public DBExecuteQuery(persistence.contracts.DBConnection dbConnection){
        this.dbConnection = dbConnection;
    }

    /**
     * Method that executes a determinate query and close the connection used
     * @param query the query that need be executed
     * @return a interface that contains the result of the query and the close connection
     */
    @Override
    public DBQueryCompleted executeSelectQuery(String query) {
        Connection connection = dbConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();


            return new DBQueryCompleted() {
                @Override
                public ResultSet getResultSet() {
                    return resultSet;
                }

                @Override
                public void closeConnection() {
                    dbConnection.closeConnection(connection);
                }
            };
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Method that executes a determinate query and close the connection used
     * @param query the query that need be executed
     */
    @Override
    public void executeModificationQuery(String query) {
        Connection connection = dbConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.executeUpdate();

            dbConnection.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
