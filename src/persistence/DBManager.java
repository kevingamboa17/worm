package persistence;

import domain.FieldWormType;
import domain.WormConfig;
import persistence.contracts.DBConnection;
import persistence.contracts.DBExecuteQuery;
import persistence.contracts.DBValidator;

public class DBManager implements persistence.contracts.DBManager {
    private final DBValidator dbValidator;
    private final DBExecuteQuery dbExecuteQuery;
    private final DBConnection dbConnection;
    private final QueryBuilder queryBuilder;
    private final String dbName;


    public DBManager() {
        dbValidator = new persistence.DBValidator();
        dbExecuteQuery = new persistence.DBExecuteQuery();
        WormConfig wormConfig = WormConfig.newInstance();
        this.dbName = wormConfig.getDbName();
        dbConnection = new persistence.DBConnection(
                wormConfig.getPoolConnections()
        );
        queryBuilder = new QueryBuilder();
    }

    @Override
    public void save(String tableName, FieldWormType[] values) {
        boolean isDBValid = dbValidator.isDBValid(dbName, tableName, values);
        if (isDBValid && dbValidator.validateRowExist(tableName, values[0].getFieldName(), (int)values[0].getValue())) {
            update(tableName, values);
        } else if (isDBValid){
            create(tableName, values);
        }
    }

    @Override
    public void update(String tableName, FieldWormType[] values) {
        dbExecuteQuery.updateEntity(
                queryBuilder.updateEntity(tableName, values)
        );
    }

    @Override
    public void create(String tableName, FieldWormType[] values) {
        dbExecuteQuery.insertEntity(
                queryBuilder.insertEntity(tableName, values)
        );
    }


    @Override
    public void delete(String tableName, int id) {
        // TODO: Correct this hardcoded name
        String fieldIdName = "objectID";

        dbExecuteQuery.deleteEntity(
                queryBuilder.deleteEntity(tableName, fieldIdName, id)
        );
    }

    @Override
    public FieldWormType[] getObject(String tableName, int id) {
        // TODO: Check what is the data the DBquery return
        return new FieldWormType[0];
    }

    @Override
    public FieldWormType[][] getAll(String tableName) {
        // TODO: Check what is the data the DBquery return
        return new FieldWormType[0][];
    }
}
