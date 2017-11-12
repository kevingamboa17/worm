package persistence;

public interface DBValidator {
    void validateTableExist(String tableName);
    void validateRowExist(String tableName, int id);
    void validateTableAttributes(String tableName, String[] attributesNames);
}
