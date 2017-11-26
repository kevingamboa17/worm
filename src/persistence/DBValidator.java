package persistence;

import domain.FieldWormType;
import domain.WormConfig;
import persistence.contracts.DBExecuteQuery;

import java.sql.*;

public class DBValidator implements persistence.contracts.DBValidator{

    private persistence.contracts.DBExecuteQuery dbExecuteQuery;

    public  DBValidator(DBExecuteQuery dbExecuteQuery){
        this.dbExecuteQuery = dbExecuteQuery;
    }

    @Override
    public boolean validateTableExist(String DBName, String tableName) {
        ResultSet resultSet = dbExecuteQuery.executeSelectQuery(new QueryBuilder().existTable(DBName, tableName)).getResultSet();
        try {
            if(resultSet.next()){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean validateRowExist(String tableName, String idFieldName, int id) {
        ResultSet resultSet = dbExecuteQuery.executeSelectQuery(new QueryBuilder().existRow(tableName,idFieldName,id)).getResultSet();

        try {
            if(resultSet == null)
                return false;

            if(resultSet.next()){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean validateTableAttributes(String DBName, String tableName, FieldWormType[] attributesNames) {
        ResultSet resultSet = dbExecuteQuery.executeSelectQuery(new QueryBuilder().getTableFieldsNamesAndTypes(DBName, tableName)).getResultSet();

        int i=0;
        try {
            while(resultSet.next()){
                boolean isNameMatch = resultSet.getString(1).compareTo(attributesNames[i].getFieldName()) == 0;
                boolean isTypeMatch = resultSet.getString(2).toLowerCase().compareTo(attributesNames[i].getDatabaseType().toLowerCase()) == 0;
                if(!isNameMatch || !isTypeMatch){
                    return false;
                }
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean isDBValid(String dbName, String tableName, FieldWormType[] attributes) {
        return  validateTableExist(dbName, tableName ) &&
                validateTableAttributes(
                        tableName,
                        dbName,
                        attributes
                );
    }

    @Override
    public boolean existDB(String DBName) {
        String host = WormConfig.newInstance().getHost();
        String port = WormConfig.newInstance().getPort();
        String user = WormConfig.newInstance().getUser();
        String password = WormConfig.newInstance().getPassword();

        String url = "jdbc:mysql://" + host + ":" + port + "?useSSL=false";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);

            Statement s = conn.createStatement();
            ResultSet resultSet = s.executeQuery(new QueryBuilder().existDB(DBName));

            if(resultSet != null)
                return resultSet.first();


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
