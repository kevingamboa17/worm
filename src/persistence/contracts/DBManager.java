package persistence.contracts;

import domain.FieldWormType;

public interface DBManager {
    void save(String tableName, FieldWormType[] values);
    void update(String tableName, FieldWormType[] values);
    void create(String tableName, FieldWormType[] values);
    void delete(String tableName,int id);
    FieldWormType[] getObject(String tableName, int id);
    FieldWormType[][] getAll(String tableName);
}
