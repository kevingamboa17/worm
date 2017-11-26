package persistence.contracts;

import domain.FieldWormType;

public interface DBValidator {
    boolean validateRowExist(String tableName, String idFieldName, int id);
    boolean isDBValid(String DBName, String tableName, FieldWormType[] attributes);
    boolean existDB(String DBName);
    boolean validateTableExist(String DBName, String tableName);
}
