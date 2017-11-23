package persistence.contracts;

import java.sql.ResultSet;

public interface DBExecuteQuery {
    ResultSet executeSelectQuery(String query);
    void executeModificationQuery(String query);
}
