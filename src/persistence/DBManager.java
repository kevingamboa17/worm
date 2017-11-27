package persistence;

import business.TypeMatcher;
import domain.FieldWormType;
import domain.WormConfig;
import persistence.contracts.DBQueryCompleted;
import persistence.contracts.DBConnection;
import persistence.contracts.DBExecuteQuery;
import persistence.contracts.DBValidator;

import java.sql.*;

/**
 * The {@code DBManager} class is responsible of delegate requests to database,
 * - It validates all incoming requests to set and get data, through its <code>DBValidator</code>.
 * - Request connections to <code>DBConnection</code>
 * - Build and do queries through <code>QueryBuilder</code> and <code>DBExecuteQuery</code> classes.
 */
public class DBManager implements persistence.contracts.DBManager {

    /** The database validator */
    private final DBValidator dbValidator;

    /** The object that execute already built queries */
    private final DBExecuteQuery dbExecuteQuery;

    /** The object that provides connections with the database */
    private final DBConnection dbConnection;

    /** The object that build the basic CRUD queries */
    private final QueryBuilder queryBuilder;

    /** The database name */
    private final String dbName;

    /** The object that match types of data between database and Java objects */
    private final TypeMatcher typeMatcher;

    /**
     * Initializes a newly created {@code DBManager} object that is responsible
     * of delegates requests to get access into database, including database validations,
     * database connections, and queries creation.
     */
    public DBManager() {
        WormConfig wormConfig = WormConfig.newInstance();
        this.dbName = wormConfig.getDbName();

        dbConnection = wormConfig.getPoolConnections() == null?
                new persistence.DBConnection(
                        wormConfig.getDbName(),
                        wormConfig.getHost(),
                        wormConfig.getPort(),
                        wormConfig.getUser(),
                        wormConfig.getPassword()
                ) :
                new persistence.DBConnection(
                    wormConfig.getPoolConnections()
                );

        dbExecuteQuery = new persistence.DBExecuteQuery(dbConnection);
        dbValidator = new persistence.DBValidator(dbExecuteQuery);
        queryBuilder = new QueryBuilder();
        typeMatcher = new TypeMatcher();
    }

    /**
     * Creates a new database with a defined name.
     * @param DBName The database's name.
     */
    @Override
    public void createDB(String DBName) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://" +
                    WormConfig.newInstance().getHost() +
                    "/?user=" +
                    WormConfig.newInstance().getUser() +
                    "&password=" +
                    WormConfig.newInstance().getPassword());

            Statement s = conn.createStatement();
            s.execute(new QueryBuilder().createDB(DBName));

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves an entity into database, calling to <code>DBValidator</code> before,
     * to assure the data consistence. If the database not exist, then <code>DBManager</code>
     * has to create it. Also if the table object not exist, then DBManager has the responsibility
     * of create it.
     *
     * @param tableName The table's name of database.
     * @param values    A <code>FieldWormType</code> array that has all fields of the entity that
     *                  is going to be saved into database.
     */
    @Override
    public void save(String tableName, FieldWormType[] values) {
        boolean existDB = dbValidator.existDB(WormConfig.newInstance().getDbName());

       if(existDB){
           boolean tableExist = dbValidator.validateTableExist(WormConfig.newInstance().getDbName(), tableName);

           if(tableExist){
               boolean isDBValid = dbValidator.isDBValid(dbName, tableName, values);
               boolean rowExist = dbValidator.validateRowExist(tableName, values[0].getFieldName(), (int)values[0].getValue());

               if (isDBValid && rowExist) {
                   update(tableName, values);
               } else if (isDBValid){
                   insertEntity(tableName, values);
               }
               
           } else{
               createTable(tableName, values);
               insertEntity(tableName, values);
           }

        } else{
            createDB(WormConfig.newInstance().getDbName());
            createTable(tableName, values);
            insertEntity(tableName, values);
        }


    }

    /**
     * Updates an specific entity that is saved into database.
     * @param tableName The table's name where the entity exists.
     * @param values    A <code>FieldWormType</code> array that has all fields of the entity that
     *                  is going to be updated into database.
     */
    @Override
    public void update(String tableName, FieldWormType[] values) {
        dbExecuteQuery.executeModificationQuery(
                queryBuilder.updateEntity(tableName, values)
        );
    }

    /**
     * Insert a new entity into database.
     * @param tableName The table's name where the entity is going to be created.
     * @param values    A <code>FieldWormType</code> array that has all fields of the entity that
     *                  is going to be updated into database.
     */
    @Override
    public void insertEntity(String tableName, FieldWormType[] values) {
        dbExecuteQuery.executeModificationQuery(
                queryBuilder.insertEntity(tableName, values)
        );
    }

    /**
     * Creates a new table into database.
     * @param tableName The tables's name.
     * @param values    A <code>FieldWormType</code> array that represents the columns's names
     *                  of the table that is going to be created.
     */
    private void createTable(String tableName, FieldWormType[] values){
        try {
            Connection conn = dbConnection.getConnection();
            Statement s = conn.createStatement();
            s.execute(new QueryBuilder().createTable(tableName, values));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Deletes an specific entity using its ID like reference.
     * @param tableName The table's name where the entity exists.
     * @param id        The identifier of the entity that is going to be deleted.
     */
    @Override
    public void delete(String tableName, int id) {
        // TODO: Correct this hardcoded name
        String fieldIdName = "objectID";

        dbExecuteQuery.executeModificationQuery(
                queryBuilder.deleteEntity(tableName, fieldIdName, id)
        );
    }

    /**
     * Gets an <code>FieldWormType</code> array that represents the fields and its values
     * of specific entity referenced through its ID.
     * @param type      The class type of the entity.
     * @param tableName The table's name where the entity exists.
     * @param id        The identifier of the entity.
     * @return          A <code>FieldWormType</code> array.
     */
    @Override
    public FieldWormType[] getObject(Class type, String tableName, int id) {
        try {

            DBQueryCompleted dbQueryCompleted = dbExecuteQuery.executeSelectQuery(
                    queryBuilder.findEntity(
                            tableName,
                            "objectID",
                            id
                    )
            );

            FieldWormType[] fieldWormTypes = typeMatcher.convertToArrayFieldWormType(
                    type, dbQueryCompleted.getResultSet()
            );

            dbQueryCompleted.closeConnection();

            return fieldWormTypes;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Gets all entities of a specific type of <code>Class</code>.
     * @param type          Type of class that represents the entities that are going to
     *                      be returned.
     * @param tableName     The table's name where the entities exist.
     * @return              A matrix of <code>FieldWormType</code> objects that represent all fields
     *                      and its values of every entity returned.
     */
    @Override
    public FieldWormType[][] getAll(Class type, String tableName) {
        try {

            DBQueryCompleted dbQueryCompleted = dbExecuteQuery.executeSelectQuery(
                    queryBuilder.allEntities(tableName)
            );

            FieldWormType[][] fieldWormTypes = typeMatcher.convertToMatrixFieldWormType(
                    type,
                    dbQueryCompleted.getResultSet()
            );

            dbQueryCompleted.closeConnection();

            return fieldWormTypes;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
