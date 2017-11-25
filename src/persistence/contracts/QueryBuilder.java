package persistence.contracts;

import domain.FieldWormType;

public interface QueryBuilder {

    interface Validator {
        String getTableFieldsNamesAndTypes(String DBName, String tableName);
        String existTable(String DBName, String tableName);
        String existRow(String tableName, String idFieldName, int id);
        String existDB(String dataBaseName);
    }

    interface CRUD {
        String createDB(String dataBaseName);
        String createTable(String tableName, FieldWormType[] attributesNames);
        String insertEntity(String tableName, FieldWormType[] attributes);
        String updateEntity(String tableName, FieldWormType[] attributes);
        String deleteEntity(String tableName, String idFieldName, int id);
        String findEntity(String tableName, String idFieldName, int id);
        String allEntities(String tableName);
    }
}
