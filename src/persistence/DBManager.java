package persistence;

import domain.FieldWormType;

public class DBManager implements persistence.contracts.DBManager {
    @Override
    public void save(String tableName, FieldWormType[] values) {

    }

    @Override
    public void update() {

    }

    @Override
    public void create() {

    }

    @Override
    public void delete(String tableName, int id) {

    }

    @Override
    public FieldWormType[] getObject(String tableName, int id) {
        return new FieldWormType[0];
    }

    @Override
    public FieldWormType[][] getAll(String tableName) {
        return new FieldWormType[0][];
    }
}
