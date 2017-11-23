package persistence;

import business.TypeMatcher;
import domain.FieldWormType;
import domain.WormConfig;
import persistence.contracts.DBConnection;
import persistence.contracts.DBExecuteQuery;
import persistence.contracts.DBValidator;

import java.sql.ResultSet;

public class DBManager implements persistence.contracts.DBManager {
    private final DBValidator dbValidator;
    private final DBExecuteQuery dbExecuteQuery;
    private final DBConnection dbConnection;
    private final QueryBuilder queryBuilder;
    private final String dbName;
    private final TypeMatcher typeMatcher;


    public DBManager() {
        WormConfig wormConfig = WormConfig.newInstance();
        this.dbName = wormConfig.getDbName();
        dbConnection = new persistence.DBConnection(
                wormConfig.getPoolConnections()
        );
        dbExecuteQuery = new persistence.DBExecuteQuery(dbConnection);
        dbValidator = new persistence.DBValidator(dbExecuteQuery);
        queryBuilder = new QueryBuilder();
        typeMatcher = new TypeMatcher();
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
    public FieldWormType[] getObject(Class type, String tableName, int id) {
        return typeMatcher.convertToArrayFieldWormType(type, null);
    }

    @Override
    public FieldWormType[][] getAll(Class type, String tableName) {
        return typeMatcher.convertToMatrixFieldWormType(type, null);
    }
}
