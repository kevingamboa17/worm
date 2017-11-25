package persistence.contracts;

import java.sql.ResultSet;

public interface DBQueryCompleted {

    public ResultSet getResultSet();
    public void closeConnection();

}
