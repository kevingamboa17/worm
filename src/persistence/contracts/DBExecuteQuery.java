package persistence.contracts;

public interface DBExecuteQuery {
    DBQueryCompleted executeSelectQuery(String query);
    void executeModificationQuery(String query);
}
