package persistence.contracts;

import domain.FieldWormType;

public interface DBValidator {
    boolean validateTableExist(String DBName, String tableName);
    boolean validateRowExist(String tableName, String idFieldName, int id);
    boolean validateTableAttributes(String DBName, String tableName, FieldWormType[] attributesNames);
}
