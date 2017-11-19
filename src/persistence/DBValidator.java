package persistence;

import domain.FieldWormType;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DBValidator implements persistence.contracts.DBValidator{

    @Override
    public boolean validateTableExist(String DBName, String tableName) {
        DBExecuteQuery dbExecuteQuery = new DBExecuteQuery();
        ResultSet resultSet = dbExecuteQuery.tableExist(new QueryBuilder().existTable(DBName, tableName));

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
        DBExecuteQuery dbExecuteQuery = new DBExecuteQuery();
        ResultSet resultSet = dbExecuteQuery.rowExist(new QueryBuilder().existRow(tableName,idFieldName,id));

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
    public boolean validateTableAttributes(String DBName, String tableName, FieldWormType[] attributesNames) {
        DBExecuteQuery dbExecuteQuery = new DBExecuteQuery();
        ResultSet resultSet = dbExecuteQuery.tableAttributes(new QueryBuilder().getTableFieldsNamesAndTypes(DBName, tableName));

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
    
}
