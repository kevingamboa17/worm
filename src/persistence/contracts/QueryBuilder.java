package persistence.contracts;

import domain.FieldWormType;

public interface QueryBuilder {

    interface Validator {
        String getTableFieldsNames(String tableName);
        String existTable(String tableName);
        String existRow(String tableName, int id);
    }

    interface CRUD {
        String createDB(String dataBaseName);
        String createTable(String tableName, String[] attributesNames);
        String insertEntity(String tableName, FieldWormType attributes);
        String updateEntity(String tableName, FieldWormType attributes);
        String deleteEntity(String tableName, int id);
        String findEntity(String tableName, int id);
        String allEntities(String tableName);
    }
}
