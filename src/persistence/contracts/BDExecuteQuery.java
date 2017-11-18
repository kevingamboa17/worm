package persistence.contracts;

import java.sql.ResultSet;

public interface BDExecuteQuery {
    ResultSet rowExist(String query);
    ResultSet tableAttributes(String query);
    ResultSet tableExist(String query);
    void insertEntity(String query);
    void updateEntity(String query);
    void deleteEntity(String query);
    void findEntity(String query);
    void allEntities(String query);
    void createDB(String query);
    void createTable(String query);
}
