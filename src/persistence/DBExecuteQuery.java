package persistence;

import persistence.contracts.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBExecuteQuery implements persistence.contracts.DBExecuteQuery {

    private DBConnection dbConnection;

    public DBExecuteQuery(persistence.contracts.DBConnection dbConnection){
        this.dbConnection = dbConnection;
    }

    @Override
    public ResultSet executeSelectQuery(String query) {
        Connection connection = dbConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();


            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

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
