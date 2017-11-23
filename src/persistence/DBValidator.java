package persistence;

import domain.FieldWormType;
import persistence.contracts.DBExecuteQuery;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DBValidator implements persistence.contracts.DBValidator{

    private persistence.contracts.DBExecuteQuery dbExecuteQuery;

    public  DBValidator(DBExecuteQuery dbExecuteQuery){
        this.dbExecuteQuery = dbExecuteQuery;
    }

    private boolean validateTableExist(String DBName, String tableName) {
        ResultSet resultSet = dbExecuteQuery.executeSelectQuery(new QueryBuilder().existTable(DBName, tableName));

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
        ResultSet resultSet = dbExecuteQuery.executeSelectQuery(new QueryBuilder().existRow(tableName,idFieldName,id));

        try {
            if(resultSet.next()){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean validateTableAttributes(String DBName, String tableName, FieldWormType[] attributesNames) {
        ResultSet resultSet = dbExecuteQuery.executeSelectQuery(new QueryBuilder().getTableFieldsNamesAndTypes(DBName, tableName));

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

    @Override
    public boolean isDBValid(String dbName, String tableName, FieldWormType[] attributes) {
        return  validateTableExist(dbName, tableName ) &&
                validateTableAttributes(
                        tableName,
                        dbName,
                        attributes
                );
    }
}
