package persistence.contracts;

import java.sql.ResultSet;

public interface DBExecuteQuery {
    ResultSet rowExist(String query);
    ResultSet tableAttributes(String query);
    ResultSet tableExist(String query);
    void insertEntity(String query);
    void updateEntity(String query);
    void deleteEntity(String query);
    ResultSet findEntity(String query);
    ResultSet allEntities(String query);
    void createDB(String query);
    void createTable(String query);
}
