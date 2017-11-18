package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BDExecuteQuery implements persistence.contracts.BDExecuteQuery {
    @Override
    public ResultSet rowExist(String query) {
        DBConnection dbConnection= new DBConnection();
        Connection connection = dbConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            dbConnection.closeConnection(connection);

            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ResultSet tableAttributes(String query) {
        DBConnection dbConnection= new DBConnection();
        Connection connection = dbConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            dbConnection.closeConnection(connection);

            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ResultSet tableExist(String query) {
        DBConnection dbConnection= new DBConnection();
        Connection connection = dbConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            dbConnection.closeConnection(connection);

            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void insertEntity(String query) {
        DBConnection dbConnection= new DBConnection();
        Connection connection = dbConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.executeQuery();
            dbConnection.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateEntity(String query) {
        DBConnection dbConnection= new DBConnection();
        Connection connection = dbConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.executeQuery();
            dbConnection.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteEntity(String query) {
        DBConnection dbConnection= new DBConnection();
        Connection connection = dbConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.executeQuery();
            dbConnection.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void findEntity(String query) {
        DBConnection dbConnection= new DBConnection();
        Connection connection = dbConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.executeQuery();
            dbConnection.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void allEntities(String query) {
        DBConnection dbConnection= new DBConnection();
        Connection connection = dbConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.executeQuery();
            dbConnection.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createDB(String query) {
        DBConnection dbConnection= new DBConnection();
        Connection connection = dbConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.executeQuery();
            dbConnection.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createTable(String query) {
        DBConnection dbConnection= new DBConnection();
        Connection connection = dbConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.executeQuery();
            dbConnection.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
