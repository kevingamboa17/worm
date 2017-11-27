package persistence;

import domain.FieldWormType;
import domain.WormConfig;
import persistence.contracts.DBExecuteQuery;

import java.sql.*;

/**
 * The BDValidator class is responsible for performing
 * validations that allow or not perform other tasks.
 */
public class DBValidator implements persistence.contracts.DBValidator{

    /** The object that execute a determinate query */
    private persistence.contracts.DBExecuteQuery dbExecuteQuery;

    /**
     * Initializes a newly created {@code DBValidator} object with the DBExecuteQuery required
     * @param dbExecuteQuery a instance of DBExecuteQuery that execute the queries
     */
    public  DBValidator(DBExecuteQuery dbExecuteQuery){
        this.dbExecuteQuery = dbExecuteQuery;
    }

    /**
     * Method that validate and return a boolean if a determined table exist in a database
     * @param DBName name of database where research the table
     * @param tableName table name that need be find
     * @return a boolean if the table specified exist in the database
     */
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

    /**
     * Method that validate and return a boolean if a determined row exist in a table
     * @param tableName table name where research a row
     * @param idFieldName column name where is the id in the table
     * @param id number id of the row that need to find
     * @return a boolean if the row specified exist in the table
     */
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

    /**
     * Method that validate if the names and types of the table in database are equals to the name
     * and types of the object
     * @param DBName name of the database where are the table
     * @param tableName name of the table to compare
     * @param attributesNames attributes of the object
     * @return a boolean if the names and types are equals
     */
    private boolean validateTableAttributes(String DBName, String tableName, FieldWormType[] attributesNames) {
        ResultSet resultSet = dbExecuteQuery.executeSelectQuery(new QueryBuilder().getTableFieldsNamesAndTypes(DBName, tableName)).getResultSet();

        int i=0;
        try {
            while(resultSet.next()){
                boolean isNameMatch = resultSet.getString(1).compareTo(attributesNames[i].getFieldName()) == 0;
                boolean isTypeMatch = resultSet.getString(2).compareTo(attributesNames[i].getOriginalType().getTypeName()) == 0;
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

    /**
     * Method that validate if the database is valid
     * @param dbName name of the database that need to validate
     * @param tableName table name that need validate
     * @param attributes attributes of the object
     * @return a boolean if the database is valid
     */
    @Override
    public boolean isDBValid(String dbName, String tableName, FieldWormType[] attributes) {
        return  validateTableExist(dbName, tableName ) &&
                validateTableAttributes(
                        tableName,
                        dbName,
                        attributes
                );
    }

    /**
     * Method that if the database exist in the server
     * @param DBName name of the database that need to be validated
     * @return a boolean if the database exist in the server
     */
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
