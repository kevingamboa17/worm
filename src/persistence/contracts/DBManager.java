package persistence.contracts;

import domain.FieldWormType;

public interface DBManager {
    void createDB(String DBName);
    void save(String tableName, FieldWormType[] values);
    void update(String tableName, FieldWormType[] values);
    void insertEntity(String tableName, FieldWormType[] values);
    void delete(String tableName,int id);
    FieldWormType[] getObject(Class type, String tableName, int id);
    FieldWormType[][] getAll(Class type, String tableName);
}
