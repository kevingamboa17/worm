package persistence;

import java.util.HashMap;

public interface QueryBuilder {

    interface Validator {
        String getTableFieldsNames(String tableName);
        String existTable(String tableName);
        String existRow(String tableName, int id);
    }

    interface CRUD {
        String createDB(String dataBaseName);
        String createTable(String tableName, String[] attributesNames);
        String insertEntity(String tableName, HashMap<String, String> attributes);
        String updateEntity(String tableName, HashMap<String, String> attributes);
        String deleteEntity(String tableName, int id);
        String findEntity(String tableName, int id);
        String allEntities(String tableName);
    }
}
